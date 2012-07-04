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

import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.iggroup.oss.sample.domain.Sample;
import com.iggroup.oss.sample.domain.SampleList;
import com.iggroup.oss.sample.domain.SampleReference;
import com.iggroup.oss.sample.domain.SampleType;
import com.iggroup.oss.sample.service.SampleService;

/**
 * Sample Controller Controller for all sample REST services.
 */
@Component
@Controller("sampleController")
public class SampleController extends BaseController {

   private static final Logger LOGGER = Logger
      .getLogger(SampleController.class);

   @Autowired
   private SampleService service;

   /**
    * Find sample by unique lookup reference
    * 
    * @param reference the sample reference, a 5 digit text field
    * @return the sample object corresponding to the lookup reference
    */
   @RequestMapping(value = "/samples/{reference}", method = {RequestMethod.GET})
   @ResponseBody
   public Sample getSampleByReference(/*
                                      * @Valid @Pattern(regexp=
                                      * "[0-9][0-9][0-9][0-9][0-9]")
                                      */@PathVariable String reference) {

      LOGGER.info("getSampleByReference " + reference);

      // since @Valid doesn't work properly until Spring 3.1
      // Note: could use SampleReference as a RequestParam but then the
      // interface is a little clunky
      validate(new SampleReference(reference));

      return service.getSampleByReference(reference);

   }

   /**
    * Find all samples
    * 
    * @return all sample objects
    */
   @RequestMapping(value = "/samples", method = {RequestMethod.GET})
   @ResponseBody
   public SampleList findAllSamples() {

      LOGGER.info("getSamples ");

      return new SampleList(service.findAllSamples());

   }

   /**
    * Find samples matching name
    * 
    * @param name the sample name fragment to search by
    * @return the sample objects matching the requested name
    */
   @RequestMapping(value = "/samples", params = "name", method = {RequestMethod.GET})
   @ResponseBody
   public SampleList findSamplesByName(@RequestParam String name) {

      LOGGER.info("getSamplesByName " + name);

      return new SampleList(service.findSamplesByName(name));

   }

   /**
    * Find samples matching type
    * 
    * @param type the sample type to search by
    * @return the sample objects matching the requested type
    */
   @RequestMapping(value = "/samples", params = "type", method = {RequestMethod.GET})
   @ResponseBody
   public SampleList findSamplesByType(@RequestParam SampleType type) {

      LOGGER.info("getSamplesByType " + type);

      validate(type);

      return new SampleList(service.findSamplesByType(type));

   }

   /**
    * Delete the sample indicated by the reference
    * 
    * @param reference the sample's reference, a 5 digit text field
    */
   @RequestMapping(value = "/samples/{reference}", method = {RequestMethod.DELETE})
   @ResponseStatus(value = HttpStatus.NO_CONTENT)
   public void deleteSample(@PathVariable String reference) {

      LOGGER.info("deleteSample " + reference);

      validate(new SampleReference(reference));

      service.deleteSample(reference);

   }

   /**
    * Create a sample or return a list of validation errors
    * 
    * @param sample the sample to be created
    * @param response http response
    */
   @RequestMapping(value = "/samples", method = {RequestMethod.POST})
   @ResponseStatus(value = HttpStatus.CREATED)
   public void createSample(@RequestBody Sample sample,
                            HttpServletResponse response) {

      LOGGER.info("createSample " + sample.getReference());

      validate(sample);

      service.createSample(sample);

      response.setHeader("Location", "/samples/" + sample.getReference());

   }

   /**
    * Update a sample or return a list of validation errors
    * 
    * @param sample fully populated sample object
    */
   @RequestMapping(value = "/samples", method = {RequestMethod.PUT})
   @ResponseStatus(value = HttpStatus.NO_CONTENT)
   public void updateSample(@Valid @RequestBody Sample sample) {

      LOGGER.info("updateSample " + sample.getReference());

      validate(sample);

      service.updateSample(sample);

   }

   /**
    * Test hashMap
    * 
    * @return test hashmap
    * @uriDeprecated {"/deprecated"}
    */
   @RequestMapping(value = {"/map", "/deprecated"}, method = {RequestMethod.GET})
   @ResponseBody
   public HashMap<String, Sample> getMap() {

      LOGGER.info("getMap");

      HashMap<String, Sample> sampleMap = new HashMap<String, Sample>();

      SampleList samples = findAllSamples();

      for (Sample sample : samples) {
         sampleMap.put(sample.getReference(), sample);
      }

      return sampleMap;

   }

}
