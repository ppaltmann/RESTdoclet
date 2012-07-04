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
package com.iggroup.oss.sample.service;

import com.iggroup.oss.sample.domain.Sample;
import com.iggroup.oss.sample.domain.SampleList;
import com.iggroup.oss.sample.domain.SampleType;

/**
 * Sample service interface, providing basic sample CRUD operations
 */
public interface SampleService {

   /**
    * Get a sample by reference
    * 
    * @param reference sample unique reference
    * @return sample
    */
   Sample getSampleByReference(String reference);

   /**
    * Find samples by name fragment
    * 
    * @param nameFragment name fragment
    * @return list of samples
    */
   SampleList findSamplesByName(String nameFragment);

   /**
    * Find samples by type
    * 
    * @param type sample type
    * @return list of samples
    */
   SampleList findSamplesByType(SampleType type);

   /**
    * Find all samples
    * 
    * @return list of samples
    */
   SampleList findAllSamples();

   /**
    * Create a sample
    * 
    * @param sample new sample
    */
   void createSample(Sample sample);

   /**
    * Delete a sample
    * 
    * @param reference sample reference
    */
   void deleteSample(String reference);

   /**
    * Update a sample
    * 
    * @param sample sample reference
    */
   void updateSample(Sample sample);

}
