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

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * List of samples.
 */
@SuppressWarnings("serial")
@XmlRootElement
public class SampleList extends ArrayList<Sample> {

   /* 
    * Note this class is only really required if you're using JAXB since it does not handle
    * Lists
    */

   /**
    * Default Constructor
    */
   public SampleList() {
   }

   /**
    * Constructor
    * 
    * @param samples list of samples
    */
   public SampleList(List<Sample> samples) {
      super();
      this.addAll(samples);
   }

   @XmlElement(name = "sample")
   public List<Sample> getSamples() {
      return this;
   }

   /**
    * Setter
    * 
    * @param samples list of samples
    */
   public void setSamples(List<Sample> samples) {
      this.addAll(samples);
   }

}
