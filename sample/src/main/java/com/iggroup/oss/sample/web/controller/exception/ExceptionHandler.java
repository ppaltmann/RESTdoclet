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

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.iggroup.oss.sample.domain.exception.DuplicateReferenceException;
import com.iggroup.oss.sample.domain.exception.ReferenceNotFoundException;
import com.iggroup.oss.sample.web.controller.error.DuplicateReferenceError;
import com.iggroup.oss.sample.web.controller.error.ReferenceNotFoundError;

/**
 * Exception handler for all internal service exceptions, converting them to
 * SampleException with structured error contents and defined HTTP status code.
 */
@Aspect
@Component("exceptionHandler")
public class ExceptionHandler {

   /**
    * Exception handler for DuplicateReferenceExceptions
    * 
    * @param dre the exception being handled
    */
   @AfterThrowing(pointcut = "execution (* com.iggroup.wt.referencerest.service.*.*(..))", throwing = "dre")
   public void handleDuplicateKeyException(DuplicateReferenceException dre) {
      throw new SampleException(
         new DuplicateReferenceError(dre.getReference()),
         HttpStatus.BAD_REQUEST);
   }

   /**
    * Exception handler for ReferenceNotFoundExceptions
    * 
    * @param nfe the exception being handled
    */
   @AfterThrowing(pointcut = "execution (* com.iggroup.wt.referencerest.service.*.*(..))", throwing = "nfe")
   public void handleNotFoundException(ReferenceNotFoundException nfe) {
      throw new SampleException(
         new ReferenceNotFoundError(nfe.getReference()), HttpStatus.NOT_FOUND);
   }

}
