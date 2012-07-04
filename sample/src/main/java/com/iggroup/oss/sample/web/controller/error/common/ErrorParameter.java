/*
 * #%L
 * restdoc-sample
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
package com.iggroup.oss.sample.web.controller.error.common;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Error parameter, consisting of parameter name and value
 */
@XmlRootElement(name = "errorParameter")
public class ErrorParameter {

   /**
    * parameter name
    */
   private String name;

   /**
    * parameter value
    */
   private String value;

   /**
    * Default constructor. Not to be used
    */
   private ErrorParameter() {
   }

   /**
    * Standard constructor
    * 
    * @param name parameter name
    * @param value parameter value
    */
   public ErrorParameter(String name, String value) {
      this.name = name;
      this.value = value;
   }

   @XmlElement
   public String getName() {
      return name;
   }

   @XmlElement
   public String getValue() {
      return value;
   }
}
