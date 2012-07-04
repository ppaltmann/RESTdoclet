/*
 * #%L
 * restdoc-doclet
 * %%
 * Copyright (C) 2012 IG Group
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.iggroup.oss.restdoclet.doclet.util;

import org.apache.commons.lang.StringUtils;

import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.AnnotationDesc.ElementValuePair;
import com.sun.javadoc.AnnotationValue;
import com.sun.javadoc.Parameter;
import com.sun.javadoc.ProgramElementDoc;

/**
 * This is an utility class for processing annotations.
 */
public final class AnnotationUtils {

   /**
    * Private constructor to "silence" PMD.
    */
   private AnnotationUtils() {
      super();
   }

   /**
    * Gets the annotations an argument of a method is annotated with.
    * 
    * @param param the method's argument to look for annotations.
    * @return the annotations the argument is annotated with or an empty array
    *         if the argument is not annotated.
    */
   public static AnnotationDesc[] annotations(final Parameter param) {
      AnnotationDesc[] annotations = param.annotations();
      if (annotations == null) {
         annotations = new AnnotationDesc[0];
      }
      return annotations;
   }

   /**
    * Checks if an argument of a method is annotated with an annotation.
    * 
    * @param param the argument to check for annotations.
    * @param type the type of annotation to look for.
    * @return <code>true</code> if the argument is annotated,
    *         <code>false</code> otherwise.
    */
   public static boolean isAnnotated(final Parameter param, final Class<?> type) {
      boolean result = false;
      for (final AnnotationDesc annotation : annotations(param)) {
         final String name = annotation.annotationType().qualifiedName();
         if (StringUtils.equals(type.getName(), name)) {
            result = true;
            break;
         }
      }
      return result;
   }

   /**
    * Finds the annotation the argument of a method is annotated with.
    * 
    * @param param the argument to check for annotations.
    * @param type the type of annotation to look for.
    * @return the annotation the argument is annotated with or
    *         <code>null</code> if the annotation is not found.
    */
   public static AnnotationDesc annotation(final Parameter param,
                                           final Class<?> type) {
      AnnotationDesc result = null;
      for (final AnnotationDesc annotation : annotations(param)) {
         final String name = annotation.annotationType().qualifiedName();
         if (StringUtils.equals(type.getName(), name)) {
            result = annotation;
            break;
         }
      }
      return result;
   }

   /**
    * Gets the element-value pairs of an annotation an argument of a method is
    * annotated with.
    * 
    * @param param the argument to check for annotations.
    * @param type the type of annotation to look for.
    * @return the element-value pairs of the annotation or an empty array if
    *         the annotation is not found.
    */
   public static ElementValuePair[] elementValues(final Parameter param,
                                                  final Class<?> type) {
      return elementValues(annotation(param, type));
   }

   /**
    * Gets the element-value pairs of an annotation an argument of a method is
    * annotated with.
    * 
    * @param annotation annotations
    * @return the element-value pairs of the annotation or an empty array if
    *         the annotation is not found.
    */
   private static ElementValuePair[] elementValues(final AnnotationDesc annotation) {

      ElementValuePair[] result;
      if (annotation == null) {
         result = new ElementValuePair[0];
      } else {
         if (annotation.elementValues() == null) {
            result = new ElementValuePair[0];
         } else {
            result = annotation.elementValues();
         }
      }
      return result;

   }

   /**
    * Gets an element-value pair of an annotation an argument of a method is
    * annotated with.
    * 
    * @param param the argument to check for annotations.
    * @param type the type of annotation to look for.
    * @param elementName the name of the element in the annotation to look for.
    * @return the value of the element in the annotation or <code>null</code>
    *         if the annotation is not found or the name of the element is not
    *         found.
    */
   public static AnnotationValue elementValue(final Parameter param,
                                              final Class<?> type,
                                              final String elementName) {
      AnnotationValue value = null;
      final ElementValuePair[] pairs = elementValues(param, type);
      for (final ElementValuePair pair : pairs) {
         if (StringUtils.equals(elementName, pair.element().name())) {
            value = pair.value();
            break;
         }
      }
      return value;
   }

   /**
    * Gets the annotations an element (class or method) is annotated with.
    * 
    * @param element the element to look for annotations.
    * @return the annotations the element is annotated with or an empty array
    *         if the element is not annotated.
    */
   public static AnnotationDesc[] annotations(final ProgramElementDoc element) {
      AnnotationDesc[] annotations = element.annotations();
      if (annotations == null) {
         annotations = new AnnotationDesc[0];
      }
      return annotations;
   }

   /**
    * Checks if an element (class or method) is annotated with an annotation.
    * 
    * @param element the element to check for annotations.
    * @param type the type of annotation to look for.
    * @return <code>true</code> if the element is annotated, <code>false</code>
    *         otherwise.
    */
   public static boolean isAnnotated(final ProgramElementDoc element,
                                     final Class<?> type) {
      boolean result = false;
      for (final AnnotationDesc annotation : annotations(element)) {
         final String name = annotation.annotationType().qualifiedName();
         if (StringUtils.equals(type.getName(), name)) {
            result = true;
            break;
         }
      }
      return result;
   }


   /**
    * Finds the annotation an element (class or method) is annotated with.
    * 
    * @param element the element to check for annotations.
    * @param type the type of annotation to look for.
    * @return the annotation the element is annotated with or <code>null</code>
    *         if the annotation is not found.
    */
   public static AnnotationDesc annotation(final ProgramElementDoc element,
                                           final Class<?> type) {
      AnnotationDesc result = null;
      for (final AnnotationDesc annotation : annotations(element)) {
         final String name = annotation.annotationType().qualifiedName();
         if (StringUtils.equals(type.getName(), name)) {
            result = annotation;
            break;
         }
      }
      return result;
   }

   /**
    * Gets the element-value pairs of an annotation an element (class or
    * method) is annotated with.
    * 
    * @param element the element to check for annotations.
    * @param type the type of annotation to look for.
    * @return the element-value pairs of the annotation or an empty array if
    *         the annotation is not found.
    */
   public static ElementValuePair[] elementValues(final ProgramElementDoc element,
                                                  final Class<?> type) {
      return elementValues(annotation(element, type));
   }

   /**
    * Gets an element-value pair of an annotation an element (class or method)
    * is annotated with.
    * 
    * @param element the element to check for annotations.
    * @param type the type of annotation to look for.
    * @param elementName the name of the element in the annotation to look for.
    * @return the value of the element in the annotation or <code>null</code>
    *         if the annotation is not found or the name of the element is not
    *         found.
    */
   public static AnnotationValue elementValue(final ProgramElementDoc element,
                                              final Class<?> type,
                                              final String elementName) {
      AnnotationValue value = null;
      final ElementValuePair[] pairs = elementValues(element, type);
      for (ElementValuePair pair : pairs) {
         if (StringUtils.equals(elementName, pair.element().name())) {
            value = pair.value();
            break;
         }
      }
      return value;
   }

}
