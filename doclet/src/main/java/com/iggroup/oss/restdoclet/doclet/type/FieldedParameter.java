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

import java.util.Collection;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * This class creates documentation for a model-parameter.
 */
public abstract class FieldedParameter extends BaseType {

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
