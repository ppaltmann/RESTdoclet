/*
 * #%L restdoc-doclet %% Copyright (C) 2012 IG Group %% Licensed under the
 * Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License
 * at http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable
 * law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License. #L%
 */
package com.iggroup.oss.restdoclet.doclet.util;

import static com.iggroup.oss.restdoclet.doclet.util.UrlUtils.parseMultiUri;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.ParameterizedType;
import com.sun.javadoc.ProgramElementDoc;
import com.sun.javadoc.Tag;
import com.sun.javadoc.Type;

/**
 * This is an utility class for processing document types.
 */
public final class DocTypeUtils {

   private static final Logger LOG = Logger.getLogger(DocTypeUtils.class);

   private static final String DEPRECATED_TAG = "uriDeprecated";
   private static final String RETURN_TAG = "return";
   private static final String IS_PREFIX = "is";
   private static final String GETTER_PREFIX = "get";

   /**
    * Private constructor to "silence" PMD.
    */
   private DocTypeUtils() {
      super();
   }

   /**
    * Return a string array of deprecated URIs for this element, searching for
    * DEPRECATED_TAG in the method comment
    * 
    * @param element javadoc element
    * @return a string array of deprecated URIs for this element
    */
   public static String[] getDeprecatedURIs(final ProgramElementDoc element) {
      String[] uris = null;

      Tag[] tags = element.tags();

      for (final Tag tag : tags) {
         final String name = tag.name();
         if (StringUtils.contains(name, DEPRECATED_TAG)) {
            uris = parseMultiUri(tag.text());
            LOG.debug("deprecated uris" + tag.text());
            break;
         }
      }

      return uris;
   }

   /**
    * Generates a comment string from the given method, consisting of the
    * method return comment and if the return type is an iggroup type, a list
    * of attributes (separated by <br>
    * )
    * 
    * @param element method doc
    * @return documentation string for the method
    */
   public static String getReturnDoc(final MethodDoc element) {

      LOG.info("Get return type documentation for method: "
         + element.toString());
      String doc = "";
      Tag[] tags = element.tags();

      doc = getTypeDoc(element.returnType());
      if (doc.isEmpty()) { // no class doc found, revert to @return comment

         for (final Tag tag : tags) {
            final String name = tag.name();
            if (StringUtils.contains(name, RETURN_TAG)) {
               doc = tag.text();
               break;
            }
         }
      }
      return doc;

   }

   /**
    * Get the documentation of a parameterised type
    * 
    * @param type
    * @return
    */
   private static String getParameterisedTypeDoc(final Type type) {

      String typeInfo = "";

      if (type.asClassDoc() != null) {
         LOG.debug(type.qualifiedTypeName());
         typeInfo =
            getTypeDoc(type.asParameterizedType().typeArguments()[type
                                                                  .asParameterizedType().typeArguments().length - 1]);
      }

      return typeInfo;
   }

   /**
    * Return true if this is not a java type
    * 
    * @param type
    * @return
    */
   private static Boolean isRelevantType(final Type type) {

      assert type.asClassDoc() != null;

      return type != null && type.asClassDoc() != null
         && type.asClassDoc().qualifiedTypeName().indexOf("java") != 0
         || isParameterisedType(type);
   }

   /**
    * Return true if this this a parameterised type
    * 
    * @param type
    * @return
    */
   private static Boolean isParameterisedType(final Type type) {

      return type.asParameterizedType() != null
         && type.asParameterizedType().typeArguments().length > 0;
   }

   /**
    * Log the type information
    * 
    * @param type
    */
   private static void logType(final Type type) {

      if (LOG.isDebugEnabled() && type.asClassDoc() != null) {
         LOG.debug(type.asClassDoc().qualifiedTypeName());
         LOG.debug(" - " + type.asClassDoc().commentText());
         for (AnnotationDesc ad : type.asClassDoc().annotations()) {
            LOG.debug(" - " + ad.toString());
         }
      }

   }

   /**
    * Return the documentation for an enum type
    * 
    * @param type
    * @return
    */
   private static String getEnumDoc(final Type type) {

      String typeInfo = "";

      if (type.asClassDoc() != null) {
         FieldDoc[] enumConstants = type.asClassDoc().enumConstants();
         for (FieldDoc constant : enumConstants) {
            typeInfo += "<TR>";
            typeInfo += "<TD>" + constant.name() + "</TD>";
            typeInfo += "<TD>" + constant.commentText() + "</TD>";
            typeInfo += "</TR>";
         }
      }

      return typeInfo;

   }

   /**
    * Return the documentation for a public constant
    * 
    * @param type
    * @return
    */
   private static String getPublicConstantDoc(final Type type) {

      String typeInfo = "";

      FieldDoc[] fields = type.asClassDoc().fields(false);
      for (FieldDoc field : fields) {
         if (field.isPublic() && field.isFinal()
            && StringUtils.equals(field.name(), field.name().toUpperCase())) {

            typeInfo +=
               "<tr><td>" + field.type().simpleTypeName() + " " + field.name()
               + "</td><td>" + field.commentText() + "</td></tr>";

         }
      }

      return typeInfo;
   }

   /**
    * Return the documentation for a field
    * 
    * @param type
    * @param attributeName
    * @return
    */
   private static String getFieldDoc(final Type type,
                                     final String attributeName,
                                     final String methodComment) {

      LOG.debug("getFieldDoc " + type.simpleTypeName() + " - " + attributeName
         + " - " + methodComment);
      String fieldComment = "";

      if (type.asClassDoc() != null) {
         for (FieldDoc field : type.asClassDoc().fields(false)) {
            if (field.name().equalsIgnoreCase(attributeName)) {
               fieldComment = field.commentText();
               if (methodComment.length() > fieldComment.length()) {
                  fieldComment = methodComment;
               }
               // see if there are any validation
               // constraints
               AnnotationDesc[] annotations = field.annotations();
               for (AnnotationDesc annotation : annotations) {
                  if (annotation.toString().contains(
                     "@javax.validation.constraints.")) {
                     String constraint =
                        annotation.toString().replace(
                           "@javax.validation.constraints.", "");
                     fieldComment += "<br>[Rule: " + constraint + "]";
                  }
               }
               break;
            }
         }
      }

      return fieldComment;

   }

   /**
    * Return a list of getter names
    * 
    * @param type
    * @return
    */
   private static Collection<String> getGetterNames(final Type type) {

      FieldDoc[] fields = type.asClassDoc().fields(false);
      ArrayList<String> getterNames = new ArrayList<String>();
      for (FieldDoc field : fields) {
         if (!(field.isPublic() && field.isFinal() && StringUtils.equals(
            field.name(), field.name().toUpperCase()))) {
            getterNames.add(GETTER_PREFIX
               + field.name().substring(0, 1).toUpperCase()
               + field.name().substring(1));
            getterNames.add(IS_PREFIX
               + field.name().substring(0, 1).toUpperCase()
               + field.name().substring(1));
         }
      }

      return getterNames;

   }

   /**
    * Returns as a string a list of attributes plus comments for the given
    * iggroup complex type (or empty string if not iggroup), formatted in an
    * HTML table. This method will recurse if attributes are iggroup complex
    * types
    * 
    * @param type
    * @param processedTypes
    * @param leafType
    * @return
    */
   private static String getTypeDoc(final Type type,
                                    ArrayList<String> processedTypes,
                                    Boolean leafType) {

      LOG.debug("getTypeDoc " + type + " leafType=" + leafType);

      ClassDoc typeDoc = type.asClassDoc();
      String typeInfo = "";

      if (typeDoc != null) {

         // if this is a generic type then recurse with the first type argument
         if (isParameterisedType(type)) {

            typeInfo = getParameterisedTypeDoc(type);

         } else if (isRelevantType(type)) {

            logType(type);

            processedTypes.add(type.typeName());

            if (leafType && !typeDoc.commentText().isEmpty()) {
               typeInfo += typeDoc.commentText();
            }

            if (typeDoc.isEnum()) {

               typeInfo += getEnumDoc(type);

            } else { // class

               LOG.debug("class type");

               // first do base class
               if (typeDoc.superclass() != null) {

                  LOG.debug("base type = "
                     + typeDoc.superclass().qualifiedName());

                  String baseTypeDoc =
                     getTypeDoc(type.asClassDoc().superclassType(),
                        processedTypes, false);
                  if (!baseTypeDoc.isEmpty()) {
                     typeInfo += "<tr><td>" + baseTypeDoc + "</td></tr>";
                  }
               }

               typeInfo += getPublicConstantDoc(type);

               Collection<String> getterNames = getGetterNames(type);

               for (MethodDoc method : typeDoc.methods()) {

                  if (method.isPublic() && getterNames.contains(method.name())) {

                     String attributeInfo = "";
                     String attributeType =
                        method.returnType().simpleTypeName();

                     // check if is this a parameterised type
                     ParameterizedType pt =
                        method.returnType().asParameterizedType();
                     Type nestedType = null;
                     if (pt != null && pt.typeArguments().length > 0) {

                        attributeType += "[";
                        for (int i = 0; i < pt.typeArguments().length; i++) {
                           attributeType +=
                              pt.typeArguments()[i].simpleTypeName();
                           if (i < pt.typeArguments().length - 1) {
                              attributeType += ", ";
                           }
                        }
                        attributeType += "]";

                     }

                     // Check if this is an array
                     attributeType += method.returnType().dimension();

                     final String attributeName =
                        getAttributeNameFromMethod(method.name());

                     attributeInfo +=
                        "<td>" + attributeType + " " + attributeName + "</td>";

                     // If type or parameterised type then recurse
                     if (!processedTypes.contains(method.returnType()
                        .qualifiedTypeName())
                        && isRelevantType(method.returnType())) {
                        attributeInfo +=
                           "<td>"
                              + getTypeDoc(
                                 nestedType == null ? method.returnType()
                                    : nestedType, processedTypes, true)
                                    + "</td>";

                     } else {

                        attributeInfo +=
                           "<td>"
                              + getFieldDoc(type, attributeName,
                                 method.commentText()) + "</td>";

                     }

                     if (!attributeInfo.isEmpty()) {
                        typeInfo += "<tr>" + attributeInfo + "</tr>";
                     }

                  }
               }
            }

            // Wrap in a table tag if this is concrete type
            if (leafType && !typeInfo.isEmpty()) {
               typeInfo = "<table>" + typeInfo + "</table>";
            }
         }

      }

      return typeInfo;

   }

   /**
    * Returns as a string a list of attributes plus comments for the given
    * type, separated by <br>
    * This method will recurse if attributes are non java types
    * 
    * @param type type info
    * @return attribute data for the given type
    */
   public static String getTypeDoc(final Type type) {
      ArrayList<String> documentedTypes = new ArrayList<String>();
      String typeDoc = getTypeDoc(type, documentedTypes, true);
      if (typeDoc != null && !typeDoc.trim().isEmpty()) {
         LOG.info("Got documentation for type " + type + " : " + typeDoc);
      }
      return typeDoc;
   }

   /**
    * Return the simple type name of the passed in type, and include the simple
    * name of the template type if a generic
    * 
    * @param type type info
    * @return simple type name (including template type name in [] if
    *         applicable)
    */
   public static String getTypeName(final Type type) {

      String typeName = "";
      typeName = type.simpleTypeName();
      ParameterizedType pt = type.asParameterizedType();
      if (pt != null && pt.typeArguments() != null
         && pt.typeArguments().length > 0) {
         typeName += "[";
         for (int i = 0; i < pt.typeArguments().length; i++) {
            typeName += pt.typeArguments()[i].simpleTypeName();
            if (i < pt.typeArguments().length - 1) {
               typeName += ", ";
            }
         }
         typeName += "]";
      }
      return typeName;

   }

   /**
    * Derive an attribute name from a getter/setter
    * 
    * @param methodName
    * @return the methodName without the leading is/get/set
    */
   private static String getAttributeNameFromMethod(String methodName) {

      String attributeName;
      if (methodName.startsWith(IS_PREFIX)) {
         attributeName = methodName.substring(IS_PREFIX.length());
      } else {
         attributeName = methodName.substring(GETTER_PREFIX.length());
      }

      if (attributeName != null && !attributeName.isEmpty()) {
         attributeName =
            attributeName.substring(0, 1).toLowerCase()
            + attributeName.substring(1);
      }

      return attributeName;
   }

}
