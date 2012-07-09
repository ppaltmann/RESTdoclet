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


/**
 * This class creates documentation for a field-parameter. These parameters are
 * fields in method-arguments annotated with <code>@ModelAttribute</code>.
 */
public class FieldParameter extends BaseType {

   /**
    * No-argument constructor for this class to be used as a bean or by JiBX
    * binding.
    */
   public FieldParameter() {
      super();
   }

   /**
    * Constructs this parameter with its name and its Java type.
    * 
    * @param name the parameter's name.
    * @param type the parameter's Java type.
    */
   public FieldParameter(final String name, final String type) {
      super(name, type, "");
      assertValid();
   }


}
