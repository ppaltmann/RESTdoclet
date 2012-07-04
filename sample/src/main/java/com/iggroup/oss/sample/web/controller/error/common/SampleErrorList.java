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

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Ugh, since JAXB seemingly cannot handle lists or arrays!
 */
@SuppressWarnings("serial")
@XmlRootElement
public class SampleErrorList extends ArrayList<SampleError> {

   /**
    * Default constructor
    */
   public SampleErrorList() {
   }

   /**
    * Constructor
    * 
    * @param errors error list
    */
   public SampleErrorList(List<SampleError> errors) {
      super();
      this.addAll(errors);
   }

   /**
    * Return this list of errors
    * 
    * @return this list of errors
    */
   @XmlElement(name = "error")
   public List<SampleError> getErrors() {
      return this;
   }

   /**
    * Add the provided list of errors to my list
    * 
    * @param errors list of errors
    */
   public void setErrors(List<SampleError> errors) {
      this.addAll(errors);
   }

}
