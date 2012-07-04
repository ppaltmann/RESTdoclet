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

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.iggroup.oss.restdoclet.doclet.util.NameValuePair;

/**
 * This class creates documentation for a REST-parameter. These are HTTP
 * parameters not in the arguments of a method but defined in
 * <code>@RequestMapping</code> annotation.
 */
public class RestParameter extends AbstractDocType {

   /**
    * The value of this parameter.
    */
   private String value;

   /**
    * No-argument constructor for this class to be used as a bean or by JiBX
    * binding.
    */
   public RestParameter() {
      super();
   }

   /**
    * Constructs this class from a name-value pair.
    * 
    * @param pair the name-value pair.
    */
   public RestParameter(final NameValuePair pair) {
      super();
      setName(pair.getName());
      value = pair.getValue();
      setType(String.class.getSimpleName());
      setJavadoc("REST parameter");
      assertValid();
   }

   /**
    * Constructs this class from a string. The string can contain the name of
    * this parameter or the name and the value of this parameter. In the latter
    * case, the name and the value are delimited by '='.
    * 
    * @param pair the string.
    */
   public RestParameter(final String pair) {
      this(new NameValuePair(pair));
   }

   /**
    * Gets the value of this parameter.
    * 
    * @return the parameter's value.
    */
   public String getValue() {
      return value;
   }

   /**
    * Sets the value of this parameter.
    * 
    * @param value the parameter's value.
    */
   public void setValue(final String value) {
      this.value = value;
   }

   /**
    * Asserts that this object is valid
    */
   @Override
   public void assertValid() {
      super.assertValid();
      assert !value.isEmpty() : "Missing value " + this.toString();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String toString() {
      return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
         .append(super.toString()).append("value", value).toString();
   }

}
