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

import org.perf4j.aop.Profiled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iggroup.oss.sample.domain.Sample;
import com.iggroup.oss.sample.domain.SampleList;
import com.iggroup.oss.sample.domain.SampleType;
import com.iggroup.oss.sample.eai.dao.SampleDAO;

/**
 * Sample service implementation, providing basic sample CRUD operations via
 * the auto wired sample DAO
 */
@Service("sampleService")
public class SampleServiceImpl implements SampleService {

   @Autowired
   private SampleDAO sampleDAO;

   /**
    * Default constructor
    */
   public SampleServiceImpl() {
   }

   @Profiled
   @Override
   public Sample getSampleByReference(String reference) {

      Sample sample;
      sample = sampleDAO.loadSample(reference);
      return sample;
   }

   @Profiled
   @Override
   public SampleList findSamplesByName(String nameFragment) {

      return new SampleList(sampleDAO.findSamples(nameFragment));

   }

   @Profiled
   @Override
   public SampleList findSamplesByType(SampleType type) {

      return new SampleList(sampleDAO.findSamples(type));
   }

   @Profiled
   @Override
   public SampleList findAllSamples() {

      return new SampleList(sampleDAO.findSamples());
   }

   @Profiled
   @Override
   public void createSample(Sample sample) {

      sampleDAO.createSample(sample);

   }

   @Profiled
   @Override
   public void deleteSample(String reference) {

      sampleDAO.deleteSample(reference);

   }

   @Profiled
   @Override
   public void updateSample(Sample sample) {

      sampleDAO.updateSample(sample);
   }

}
