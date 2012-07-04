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

import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.iggroup.oss.sample.service.SampleService;

/**
 * SampleController test suite. Tests the Spring-wired service implementation.
 */
public class SampleControllerTest extends AbstractSampleServiceTest {

   /**
    * Spring-wire the service implementation to be tested using the project
    * wiring file
    */
   @Before
   public void setUp() {
      ApplicationContext context =
         new ClassPathXmlApplicationContext(
            new String[] {"dispatcher-servlet.xml"});
      setService((SampleService) context.getBean("sampleService"));
   }

}
