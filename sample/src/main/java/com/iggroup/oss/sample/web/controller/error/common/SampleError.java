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
 * Sample error, consisting of error code and a list error parameters. These
 * would be used by the client to lookup and populate a locale sensitive,
 * parameterised message.
 */
@XmlRootElement
public class SampleError {

   /**
    * error code
    */
   private SampleErrorCode code;

   /**
    * error parameters
    */
   private List<ErrorParameter> parameters;

   /**
    * Constructor
    */
   public SampleError() {
   }

   /**
    * Constructor
    * 
    * @param code error code
    */
   public SampleError(SampleErrorCode code) {
      this.code = code;
   }

   /**
    * Constructor
    * 
    * @param code error code
    * @param parameters error parameter list
    */
   public SampleError(SampleErrorCode code, List<ErrorParameter> parameters) {
      this.code = code;
      this.parameters = parameters;
   }

   /**
    * Constructor
    * 
    * @param code error code
    * @param parameter error parameter
    */
   public SampleError(SampleErrorCode code, ErrorParameter parameter) {
      this.code = code;
      this.parameters = new ArrayList<ErrorParameter>();
      this.parameters.add(parameter);
   }

   @XmlElement
   public SampleErrorCode getCode() {
      return code;
   }

   public void setCode(SampleErrorCode code) {
      this.code = code;
   }

   @XmlElement(name = "parameter")
   public List<ErrorParameter> getParameters() {
      return parameters;
   }

   public void setParameters(List<ErrorParameter> parameters) {
      this.parameters = parameters;
   }

}
