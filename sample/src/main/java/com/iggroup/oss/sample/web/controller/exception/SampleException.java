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
package com.iggroup.oss.sample.web.controller.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.iggroup.oss.sample.web.controller.error.common.SampleError;

/**
 * Exception class for all sample service exceptions, containing HTTP status
 * code and sample error list.
 */
@SuppressWarnings("serial")
public class SampleException extends RuntimeException {

   private int httpStatus;
   private List<SampleError> errors;

   /**
    * Constructor
    */
   public SampleException() {
      super();
   }

   /**
    * Constructor
    * 
    * @param error sample error being thrown
    * @param httpStatus response http status
    */
   public SampleException(final SampleError error, HttpStatus httpStatus) {
      super();
      this.httpStatus = httpStatus.value();
      errors = new ArrayList<SampleError>();
      errors.add(error);
   }

   /**
    * Constructor
    * 
    * @param errors sample error list being thrown
    * @param httpStatus response http status
    */
   public SampleException(final List<SampleError> errors, HttpStatus httpStatus) {
      super();
      this.httpStatus = httpStatus.value();
      this.errors = errors;
   }

   public List<SampleError> getErrors() {
      return errors;
   }

   public int getHttpStatus() {
      return httpStatus;
   }

}
