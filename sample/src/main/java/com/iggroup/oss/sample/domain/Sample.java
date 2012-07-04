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

import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Simple domain class. A sample has a name, type (e.g. BOOK) and a reference
 * number
 */
@XmlRootElement
public class Sample {

   /* This class contains attributes of various types with
   * validation constraints using Spring and JSR-303 Bean Validation API
   * (http://static.springsource.org/spring/docs/3.0.0.RC3/spring-framework
   * -reference/html/ch05s07.html)
   */

   private static final int MAX_NAME_LENGTH = 20;

   /**
    * The sample reference (PK)
    */
   private SampleReference reference;

   /**
    * The sample name
    */
   @Size(min = 1, max = MAX_NAME_LENGTH)
   private String name;

   /**
    * The sample type, e.g. BOOK
    */
   private SampleType type;

   /**
    * Default constructor, required for JAXB
    */
   public Sample() {
   }

   /**
    * Main constructor
    * 
    * @param reference the sample's string value
    * @param name the sample's name
    * @param type the sample's type
    */
   public Sample(final String reference, final String name,
      final SampleType type) {
      this.reference = new SampleReference(reference);
      this.name = name;
      this.type = type;
   }

   /**
    * Getter for stringValue
    * 
    * @return the sample's reference number
    */
   @XmlElement
   public String getReference() {
      return reference.toString();
   }

   /**
    * Setter for reference
    * 
    * @param reference the sample's reference number
    */
   public void setReference(String reference) {
      this.reference = new SampleReference(reference);
   }

   /**
    * Getter for name
    * 
    * @return the sample's name
    */
   @XmlElement
   public String getName() {
      return name;
   }

   /**
    * Setter for name
    * 
    * @param name a free format string name
    */
   public void setName(String name) {
      this.name = name;
   }

   /**
    * Getter for type
    * 
    * @return the sample's type
    */
   @XmlElement
   public SampleType getType() {
      return type;
   }

   /**
    * Setter for type
    * 
    * @param type the sample's type
    */
   public void setType(SampleType type) {
      this.type = type;
   }

}
