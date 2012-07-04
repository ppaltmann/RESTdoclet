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
package com.iggroup.oss.sample.web.controller.error;

import java.util.ArrayList;

import com.iggroup.oss.sample.web.controller.error.common.ErrorParameter;
import com.iggroup.oss.sample.web.controller.error.common.SampleError;
import com.iggroup.oss.sample.web.controller.error.common.SampleErrorCode;

/**
 * Java bean validation error
 */
public class ValidationError extends SampleError {

   private static final String FIELD_TYPE = "fieldType";
   private static final String FIELD_NAME = "fieldName";
   private static final String FIELD_VALUE = "fieldValue";
   private static final String BEAN_TYPE = "beanType";

   /**
    * Constructor
    * 
    * @param fieldName the name of the field failing validation
    * @param fieldType the type of the field failing validation
    * @param fieldValue the value of the field failing validation
    * @param beanType the type of the bean containing the failing field
    */
   public ValidationError(String fieldName, String fieldType,
      String fieldValue, String beanType) {

      super(SampleErrorCode.VALIDATION_ERROR);

      ArrayList<ErrorParameter> errorParameters =
         new ArrayList<ErrorParameter>();
      errorParameters.add(new ErrorParameter(FIELD_TYPE, fieldType));
      errorParameters.add(new ErrorParameter(FIELD_NAME, fieldName));
      errorParameters.add(new ErrorParameter(FIELD_VALUE, fieldValue));
      errorParameters.add(new ErrorParameter(BEAN_TYPE, beanType));
      setParameters(errorParameters);

   }

}
