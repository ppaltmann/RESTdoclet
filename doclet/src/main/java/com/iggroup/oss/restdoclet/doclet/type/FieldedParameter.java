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
package com.iggroup.oss.restdoclet.doclet.type;

import static org.apache.commons.lang.StringUtils.isBlank;

import java.util.Collection;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.iggroup.oss.restdoclet.doclet.util.DocletUtils;
import com.sun.javadoc.AnnotationValue;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.ParamTag;
import com.sun.javadoc.Parameter;

/**
 * This class creates documentation for a model-parameter.
 */
public abstract class FieldedParameter extends AbstractDocType {

   /**
    * The field-parameters in this model-parameter.
    */
   protected Collection<FieldParameter> fields;

   /**
    * No-argument constructor for this class to be used as a bean or by JiBX
    * binding.
    */
   public FieldedParameter() {
      super();
   }

   /**
    * Constructs this parameter from its Java documentation object and Java
    * documentation tags of the parameters of the method it belongs to.
    * 
    * @param param the parameter's Java documentation object.
    * @param tags the Java documentation tags of the parameters of the method
    *           this parameter belongs to.
    */
   public FieldedParameter(final Parameter param, final ParamTag[] tags) {
      super(param, tags);
      initName(param);
      initFields(param.type().asClassDoc());
   }

   /**
    * Initialises the field-parameters in this model-parameter.
    * 
    * @param classDoc the parameter's Java documentation object.
    */
   private void initFields(final ClassDoc classDoc) {

      fields = DocletUtils.getPublicFields(classDoc);

   }

   /**
    * Initialises the name of this parameter.
    * 
    * @param param the parameter's Java documentation object.
    */
   private void initName(final Parameter param) {

      final AnnotationValue value = getAnnotationValue(param);
      if (value == null) {
         setName(param.name());
      } else {
         if (isBlank(value.value().toString())) {
            setName(param.name());
         } else {
            setName(value.value().toString().trim());
         }
      }
   }

   /**
    * Abstract method to determine annotation value from a parameter for a
    * particular parameter subtype
    * 
    * @param param paramter
    * @return annotation value
    */
   protected abstract AnnotationValue getAnnotationValue(final Parameter param);

   /**
    * Gets the field-parameters of this parameter.
    * 
    * @return the parameter's field-parameters.
    */
   public Collection<FieldParameter> getFields() {
      return fields;
   }

   /**
    * Sets the field-parameters of this parameter.
    * 
    * @param fields the parameter's field-parameters.
    */
   public void setFields(final Collection<FieldParameter> fields) {
      this.fields = fields;
   }

   /**
    * Asserts that this object is valid
    */
   @Override
   public void assertValid() {
      super.assertValid();
      for (FieldParameter f : fields) {
         f.assertValid();
      }
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String toString() {
      return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
         .append(super.toString()).append("fields", fields).toString();
   }

}
