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
package com.iggroup.oss.sample.web.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.iggroup.oss.sample.web.controller.error.ValidationError;
import com.iggroup.oss.sample.web.controller.error.common.SampleError;
import com.iggroup.oss.sample.web.controller.error.common.SampleErrorList;
import com.iggroup.oss.sample.web.controller.exception.SampleException;

/**
 * Abstract base controller for sample controllers. Contains common validation
 * and exception handling functionality.
 */
@Component
@Controller
public abstract class BaseController {

   private static final Logger LOGGER = Logger.getLogger(BaseController.class);

   /**
    * Spring wired global bean validator
    */
   @Autowired
   private Validator globalValidator;

   /**
    * SampleException handler. Set the HTTP status and return a SampleErrorList
    * object
    * 
    * @param se the sample exception being handled
    * @param req HTTP request
    * @param res HTTP response
    * @return a JAXB friendly SampleErrorList
    * @throws IOException IO exception
    */
   @ExceptionHandler(SampleException.class)
   @ResponseBody
   public SampleErrorList exceptionHandler(SampleException se,
                                           HttpServletRequest req,
                                           HttpServletResponse res)
      throws IOException {

      LOGGER.debug("Handling:", se);
      res.setStatus(se.getHttpStatus());

      return new SampleErrorList(se.getErrors());

   }

   /**
    * Handler for illegal argument exceptions. Spring will convert this to an
    * HTTP 400 error.
    */
   @ExceptionHandler(IllegalArgumentException.class)
   @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "validation error")
   public void veryBadRequest() {
   }

   /**
    * Validate the given object using the configured validator, throwing a
    * BusinessException if the object fails validation
    * 
    * @param o the object to be validated
    */
   protected void validate(Object o) {

      ArrayList<SampleError> errors = new ArrayList<SampleError>();
      for (ConstraintViolation<Object> violation : globalValidator.validate(o)) {

         errors.add(new ValidationError(violation.getLeafBean().getClass()
            .getSimpleName(), violation.getPropertyPath().toString(),
            violation.getInvalidValue().toString(), violation
               .getRootBeanClass().getSimpleName()));
      }
      if (!errors.isEmpty()) {
         LOGGER.debug(errors);
         throw new SampleException(errors, HttpStatus.BAD_REQUEST);
      }

   }

}
