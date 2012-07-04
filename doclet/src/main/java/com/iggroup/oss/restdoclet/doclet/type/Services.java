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
import java.util.Collections;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * This class stores all the controllers
 * <p>
 * This class is used for generating the JSP (<code>services.htm</code>) for
 * listing services.
 * 
 * @see Service
 */
public class Services {

   /**
    * The controllers.
    */
   private Collection<ControllerSummary> controllers;

   private String context;

   /**
    * Gets all the controllers.
    * 
    * @return the controllers .
    */
   public Collection<ControllerSummary> getControllers() {
      return controllers;
   }

   /**
    * Gets a sorted list of all controllers' services.
    * 
    * @return the sorted list of all controllers' services .
    */
   public Collection<Service> getServices() {
      ArrayList<Service> services = new ArrayList<Service>();
      for (ControllerSummary controller : controllers) {
         services.addAll(controller.getServices());
      }
      Collections.sort(services);

      return services;
   }

   /**
    * Sets the controllers.
    * 
    * @param controllers .
    */
   public void setControllers(final Collection<ControllerSummary> controllers) {
      this.controllers = controllers;
   }

   /**
    * Adds a controller.
    * 
    * @param controller the controller to be added.
    */
   public void addController(final ControllerSummary controller) {
      if (controllers == null) {
         controllers = new ArrayList<ControllerSummary>();
      }
      controllers.add(controller);
   }

   /**
    * Asserts that this object is valid
    */
   public void assertValid() {
      assert controllers != null && controllers.size() > 0 : "Missing controllers";
      for (ControllerSummary cs : controllers) {
         cs.assertValid();
      }

      Collection<Service> services = getServices();
      assert services != null && services.size() > 0;
   }

   /**
    * Get the web context for these services
    * 
    * @return web context
    */
   protected String getContext() {
      return context;
   }

   /**
    * Set the web context for these services
    * 
    * @param context web context
    */
   protected void setContext(String context) {
      this.context = context;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String toString() {
      return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
         .append("controllers", controllers).toString();
   }

}
