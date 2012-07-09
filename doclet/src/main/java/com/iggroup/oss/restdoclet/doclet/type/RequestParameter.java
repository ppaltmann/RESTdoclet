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

import org.apache.log4j.Logger;

/**
 * This class creates documentation for a request-parameter. These parameters
 * are method-arguments annotated with <code>@RequestParam</code>.
 */
public class RequestParameter extends BaseType {

   private static final Logger LOG = Logger.getLogger(RequestParameter.class);

   /**
    * Whether this parameter is required or not
    */
   private boolean required = true;

   /**
    * An optional default value for this parameter
    */
   private String defaultValue = "";

   /**
    * No-argument constructor for this class to be used as a bean or by JiBX
    * binding.
    */
   public RequestParameter() {
      super();
   }


   /**
    * Returns whether this parameter is required or not
    * 
    * @return whether this parameter is required or not
    */
   public boolean isRequired() {
      return required;
   }

   /**
    * Set whether this parameter is required or not
    * 
    * @param required required or not
    */
   public void setRequired(boolean required) {
      this.required = required;
   }

   /**
    * Get the (optional) default value for this parameter
    * 
    * @return the default value for this parameter or "" if unspecified
    */
   public String getDefaultValue() {
      return defaultValue;
   }

   /**
    * Set the (optional) default value for this parameter
    * 
    * @param defaultValue parameter default value
    */
   public void setDefaultValue(String defaultValue) {
      this.defaultValue = defaultValue;
   }

   /**
    * Asserts that this object is valid
    */
   @Override
   public void assertValid() {
      super.assertValid();
      if (getJavadoc() == null || getJavadoc().isEmpty()) {
         LOG.warn("Missing javadoc " + this.toString());
      }
   }

}
