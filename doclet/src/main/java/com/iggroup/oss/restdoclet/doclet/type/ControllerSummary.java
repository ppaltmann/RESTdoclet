/*
 * #%L
 * restdoc-doclet
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
package com.iggroup.oss.restdoclet.doclet.type;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 * This class summarises a controller with the services it supports.
 * 
 * @see Service
 */
public class ControllerSummary extends BaseType {

   /**
    * The services supported by this controller.
    */
   private Collection<Service> services;

   /**
    * No-argument constructor for this class to be used as a bean or by JiBX
    * binding.
    */
   public ControllerSummary() {
      super();
   }

   /**
    * Constructs this summary with the Java type of the controller and its
    * documentation.
    * 
    * @param type the controller's Java type.
    * @param javadoc the controller's documentation.
    */
   public ControllerSummary(final String type, final String javadoc) {
      super(type, type, javadoc);
      services = new ArrayList<Service>();
   }

   /**
    * Gets the services supported by this controller.
    * 
    * @return the services supported by this controller.
    */
   public Collection<Service> getServices() {
      return services;
   }

   /**
    * Sets the services supported by this controller.
    * 
    * @param services the services supported by this controller.
    */
   public void setServices(final Collection<Service> services) {
      this.services = services;
   }

   /**
    * Adds a service to this controller.
    * 
    * @param service the service to be added to this controller.
    */
   public void addService(final Service service) {
      if (services == null) {
         services = new ArrayList<Service>();
      }
      services.add(service);
   }

   /**
    * Asserts that this object is valid
    */
   @Override
   public void assertValid() {
      super.assertValid();
      assert services != null;
      assert services.size() > 0 : services.toString();
      for (Service ss : services) {
         ss.assertValid();
      }
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String toString() {
      return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
      .append("type", super.toString()).append("services", services)
      .toString();
   }

}
