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
package com.iggroup.oss.sample.eai.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.iggroup.oss.sample.domain.Sample;
import com.iggroup.oss.sample.domain.SampleType;
import com.iggroup.oss.sample.domain.exception.DuplicateReferenceException;
import com.iggroup.oss.sample.domain.exception.ReferenceNotFoundException;

/**
 * Sample data access object that provides access to a very dull, static
 * in-memory map of Sample objects.
 */
@Component("sampleDAO")
public class SampleDAO {

   /**
    * The "database"
    */
   private final HashMap<String, Sample> database =
      new HashMap<String, Sample>();

   private HashMap<String, Sample> getDatabase() {
      return database;
   }

   /**
    * Default constructor. Sets up the "database"
    */
   public SampleDAO() {

      getDatabase().put("00001",
         new Sample("00001", "Wuthering Heights", SampleType.BOOK));
      getDatabase().put("00002",
         new Sample("00002", "Silas Marner", SampleType.BOOK));
      getDatabase().put("00003",
         new Sample("00003", "The Life of Pi", SampleType.BOOK));
      getDatabase().put("00004",
         new Sample("00004", "Gladiator", SampleType.DVD));
      getDatabase().put("00005",
         new Sample("00005", "Oliver Twist", SampleType.BOOK));

   }

   /**
    * Loads a sample object by reference
    * 
    * @param reference the unique reference for a sample object
    * @return sample if found, else throws a NotFoundException
    */
   public Sample loadSample(String reference) {

      Sample sample = database.get(reference);

      if (sample == null) {
         throw new ReferenceNotFoundException(reference);
      }
      return sample;

   }

   /**
    * Return a list of samples with names containing the requested name
    * fragment, or an empty list if no matches were found
    * 
    * @param nameFragment the name fragment to match against
    * @return a list of samples, or an empty list if no matches
    */
   public List<Sample> findSamples(String nameFragment) {

      List<Sample> results = new ArrayList<Sample>();

      for (Sample sample : database.values()) {
         if (sample.getName().contains(nameFragment)) {
            results.add(sample);
         }
      }
      return results;
   }

   /**
    * Return all samples of the given type
    * 
    * @param type the sample type filter for this query
    * @return all samples of the given type
    */
   public List<Sample> findSamples(SampleType type) {

      List<Sample> results = new ArrayList<Sample>();

      for (Sample sample : database.values()) {
         if (sample.getType() == type) {
            results.add(sample);
         }
      }
      return results;

   }

   /**
    * Return all samples
    * 
    * @return all samples
    */
   public List<Sample> findSamples() {

      List<Sample> results = new ArrayList<Sample>();

      for (Sample sample : database.values()) {
         results.add(sample);
      }
      return results;
   }

   /**
    * Create a sample, throwing a duplicate key exception if a sample of the
    * given reference already exists
    * 
    * @param sample the sample to be created
    */
   public void createSample(Sample sample) {
      if (database.containsKey(sample.getReference())) {
         throw new DuplicateReferenceException(sample.getReference());
      }
      database.put(sample.getReference(), sample);
   }

   /**
    * Delete a sample, throwing a not found exception if the reference is not
    * found
    * 
    * @param reference the reference of the sample to be deleted
    */
   public void deleteSample(String reference) {
      if (!database.containsKey(reference)) {
         throw new ReferenceNotFoundException(reference);
      }
      database.remove(reference);
   }

   /**
    * Update a sample
    * 
    * @param sample the updated sample
    */
   public void updateSample(Sample sample) {
      if (!database.containsKey(sample.getReference())) {
         throw new ReferenceNotFoundException(sample.getReference());
      }
      database.put(sample.getReference(), sample);
   }
}
