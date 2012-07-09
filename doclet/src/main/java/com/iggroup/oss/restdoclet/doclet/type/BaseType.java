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
package com.iggroup.oss.restdoclet.doclet.type;

import static org.apache.commons.lang.StringUtils.trimToNull;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.log4j.Logger;

import com.iggroup.oss.restdoclet.doclet.util.DocTypeUtils;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.ParamTag;
import com.sun.javadoc.Parameter;

/**
 * Base class for doclet types
 */
public abstract class BaseType implements Comparable<BaseType> {

   /**
    * Logger
    */
   private static final Logger LOG = Logger.getLogger(BaseType.class);

   /**
    * The name of this parameter.
    */
   protected String name;

   /**
    * The Java type of this parameter.
    */
   protected String type;

   /**
    * The documentation of this parameter.
    */
   protected String javadoc;

   /**
    * Default constructor
    */
   public BaseType() {

   }

   /**
    * Parameterised constructor
    * 
    * @param name parameter name
    * @param type parameter type
    * @param javadoc parameter documentation
    */
   public BaseType(String name, String type, String javadoc) {
      setName(name);
      setType(type);
      setJavadoc(javadoc);
   }

   /**
    * Constructor
    * 
    * @param classDoc class doc
    */
   public BaseType(ClassDoc classDoc) {
      setType(classDoc.qualifiedName());
      setJavadoc(trimToNull(classDoc.commentText()));
   }

   /**
    * Constructor
    * 
    * @param param parameter
    * @param tags parameter tags
    */
   public BaseType(final Parameter param, final ParamTag[] tags) {
      super();
      initType(param);
      initJavadoc(param, tags);
   }

   /**
    * Gets the name of this parameter
    * 
    * @return parameter name.
    */
   public String getName() {
      return name;
   }

   /**
    * Sets the name of this parameter.
    * 
    * @param name the parameter's name.
    */
   public void setName(final String name) {
      assert name != null : "name is null";
      this.name = trimToNull(name);
   }

   /**
    * Gets the type of this parameter.
    * 
    * @return parameter type.
    */
   public String getType() {
      return type;
   }

   /**
    * Sets the type of this parameter.
    * 
    * @param type the parameter's Java type.
    */
   public void setType(final String type) {
      assert type != null : "type is null";
      this.type = type;
   }

   /**
    * Gets the documentation of this parameter.
    * 
    * @return the parameter's documentation.
    */
   public String getJavadoc() {
      LOG.debug("getJavadoc : " + type + " - " + name + " - " + javadoc);
      return javadoc;
   }

   /**
    * Sets the documentation of this parameter.
    * 
    * @param javadoc the parameter's documentation.
    */
   public void setJavadoc(final String javadoc) {
      assert javadoc != null : "javadoc is null";
      this.javadoc = trimToNull(javadoc);
   }

   /**
    * Initialises the documentation of this parameter.
    * 
    * @param param the parameter's Java documentation object.
    * @param tags the Java documentation tags of the parameters of the method
    *           this parameter belongs to.
    */
   protected void initJavadoc(final Parameter param, final ParamTag[] tags) {

      for (ParamTag tag : tags) {
         if (param.name().equals(tag.parameterName())) {
            String comment = DocTypeUtils.getTypeDoc(param.type());
            setJavadoc(comment == null || comment.isEmpty() ? tag
               .parameterComment() : comment);
         }
      }
   }

   /**
    * Initialises the Java type of this parameter.
    * 
    * @param param the parameter's Java documentation object.
    */
   protected void initType(final Parameter param) {
      setType(DocTypeUtils.getTypeName(param.type()));
   }

   /**
    * Asserts that this object is valid
    */
   public void assertValid() {
      assert type != null && !type.isEmpty() : "Missing type "
         + this.toString();
      assert name != null && !name.isEmpty() : "Missing name "
         + this.toString();
      if (!type.equalsIgnoreCase("void")
         && (getJavadoc() == null || getJavadoc().isEmpty())) {
         LOG.warn("Missing javadoc " + this.toString());
      }
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean equals(final Object obj) {
      boolean result;
      if (obj instanceof BaseType) {
         final BaseType param = (BaseType) obj;
         result =
            new EqualsBuilder().append(name, param.getName())
            .append(type, param.getType()).isEquals();
      } else {
         result = false;
      }
      return result;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public int hashCode() {
      return new HashCodeBuilder().append(name).append(type).toHashCode();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public int compareTo(final BaseType param) {
      int result;
      if (param == null) {
         result = 1;
      } else {
         result =
            new CompareToBuilder().append(name, param.name)
            .append(type, param.type).toComparison();
      }
      return result;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String toString() {
      return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
      .append("name", name).append("type", type).append("javadoc", javadoc)
      .toString();
   }

}
