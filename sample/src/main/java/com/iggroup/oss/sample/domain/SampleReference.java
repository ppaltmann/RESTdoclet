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
package com.iggroup.oss.sample.domain;

import javax.validation.constraints.Pattern;

/**
 * Numeric identifier with a 5 digit validation constraint
 */
public class SampleReference {

   private static final int REF_LENGTH = 5;

   @Pattern(regexp = "[0-9][0-9][0-9][0-9][0-9]")
   private String reference;

   /**
    * Default constructor
    */
   public SampleReference() {
   }

   /**
    * Constructor for long references
    * 
    * @param numericReference long reference
    */
   public SampleReference(Long numericReference) {
      reference = numericReference.toString();
      while (reference.length() < REF_LENGTH) {
         reference = "0" + reference;
      }
   }

   /**
    * Constructor
    * 
    * @param reference the reference
    */
   public SampleReference(final String reference) {
      assert reference.length() == REF_LENGTH;
      this.reference = reference;
   }

   /**
    * Reference setter
    * 
    * @param reference the reference
    */
   public void setReference(final String reference) {
      assert reference.length() == REF_LENGTH;
      this.reference = reference;
   }

   /**
    * Return the reference
    * 
    * @return reference
    */
   public String getReference() {
      return reference;
   }

   @Override
   public String toString() {
      return this.reference;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      }
      if (!(o instanceof SampleReference)) {
         return false;
      }
      SampleReference r = (SampleReference) o;
      return r.getReference().equalsIgnoreCase(reference);
   }

   @Override
   public int hashCode() {
      return reference.hashCode();
   }

}
