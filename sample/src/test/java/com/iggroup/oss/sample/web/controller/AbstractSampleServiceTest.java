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

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.iggroup.oss.sample.domain.Sample;
import com.iggroup.oss.sample.domain.SampleReference;
import com.iggroup.oss.sample.domain.SampleType;
import com.iggroup.oss.sample.service.SampleService;

/**
 * Abstract test suite for the SampleService interface. Concrete testers have
 * to supply the service implementation.
 */
public abstract class AbstractSampleServiceTest {

   /**
    * Service interface to be supplied by concrete classes.
    */
   private SampleService service;

   public SampleService getService() {
      return service;
   }

   public void setService(SampleService service) {
      this.service = service;
   }

   /**
    * Success test case for getSampleByReference
    */
   @Test
   public void testGetSampleByReference() {
      try {
         Sample sample = service.getSampleByReference("00001");
         assertTrue(sample.getReference().equalsIgnoreCase("00001"));
      } catch (Exception se) {
         assertTrue(false);
      }
   }

   /**
    * Failure test case for getSampleByReference
    */
   @Test
   public void testGetSampleByReferenceFail() {
      try {
         service.getSampleByReference("99999");
         assertTrue(false);
      } catch (Exception e) {
         assertTrue(true);
      }
   }

   /**
    * Success test case for findAllSamples
    */
   @Test
   public void testFindAllSamples() {
      try {
         List<Sample> samples = service.findAllSamples();
         assertTrue(samples.size() > 0);
      } catch (Exception e) {
         assertTrue(false);
      }
   }

   /**
    * Success test case for findSamplesByName
    */
   @Test
   public void testFindSamplesByName() {
      try {
         List<Sample> samples = service.findSamplesByName("Silas");
         assertTrue(samples.size() > 0);
      } catch (Exception e) {
         assertTrue(false);
      }
   }

   /**
    * Success test case for findSamplesByType
    */
   @Test
   public void testFindSamplesByType() {
      try {
         List<Sample> samples = service.findSamplesByType(SampleType.BOOK);
         assertTrue(samples.size() > 0);
      } catch (Exception be) {
         assertTrue(false);
      }
   }

   /**
    * Failure test case for deleteSample
    */
   @Test
   public void testDeleteSampleFail() {

      try {
         service.deleteSample("99999");
         assertTrue(false);
      } catch (Exception e) {
         assertTrue(true);
      }

   }

   /**
    * Success test case for createSample
    */
   @Test
   public void testCreateSample() {

      try {
         service.createSample(new Sample(getAvailableRef(), "New Sample",
            SampleType.BOOK));
         assertTrue(true);
      } catch (Exception e) {
         assertTrue(false);
      }
   }

   /**
    * Success test case for deleteSample
    */
   @Test
   public void testDeleteSample() {
      String tempRef = getAvailableRef();
      try {
         service.createSample(new Sample(tempRef, "New Sample",
            SampleType.BOOK));
         service.deleteSample(tempRef);
      } catch (Exception e) {
         assertTrue(false);
      }
      try {
         service.getSampleByReference(tempRef);
         assertTrue(false);
      } catch (Exception e) {
         assertTrue(true);
      }

   }

   /**
    * Success test case for updateSample
    */
   @Test
   public void testUpdateSample() {

      try {
         Sample sample = service.getSampleByReference("00001");
         sample.setName("New name");
         service.updateSample(sample);
      } catch (Exception e) {
         assertTrue(false);
      }

   }

   /**
    * Find an available reference by adding 1 to the largest existing reference
    * number
    * 
    * @return unique reference
    */
   private String getAvailableRef() {

      Long availRef = new Long(0);

      List<Sample> samples = service.findAllSamples();

      for (Sample s : samples) {
         Long ref = Long.valueOf(s.getReference());
         if (ref >= availRef) {
            availRef = ref + 1;
         }
      }

      return new SampleReference(availRef).toString();
   }

}
