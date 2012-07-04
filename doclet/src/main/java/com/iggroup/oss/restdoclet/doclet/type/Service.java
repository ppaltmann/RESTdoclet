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

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 * A service refers to an URL defined in <code>RESTURLTreeHandlerMapping</code>
 * and mapped to a controller.
 * <p>
 * Multiple URLs can be mapped to the same controller. A service can also be
 * mapped to multiple methods depending on the HTTP request-method. Therefore,
 * relationships between services, controllers and methods can be defined as:
 * 
 * <pre>
 * Service (n) --- (1) Controller
 *    (1)                 (1)
 *     |                   |
 *     |                   |
 *     |                  (n)
 *     `---(HTTP)--- (n) Method
 * </pre>
 * 
 * This class maps a service to a single controller.
 */
public class Service implements Comparable<Service> {

   /**
    * The identifier of this service.
    */
   private int identifier;

   /**
    * The context root of this service
    */
   private String context;

   /**
    * The URIs of this service.
    */
   private Collection<Uri> uris;

   /**
    * The controller that maps to this service.
    */
   private Controller controller;

   /**
    * No-argument constructor for this class to be used as a bean or by JiBX
    * binding.
    */
   public Service() {
      super();
   }

   /**
    * Constructs this service with its identifier, its URL and the controller
    * it maps to.
    * 
    * @param identifier the service's identifier.
    * @param uris the service's URI's.
    * @param controller the controller this service is maps to.
    */
   public Service(final int identifier, final Collection<Uri> uris,
      final Controller controller) {

      this.identifier = identifier;
      this.uris = uris;
      this.controller = controller;
   }

   /**
    * Gets the identifier of this service.
    * 
    * @return the service's identifier.
    */
   public int getIdentifier() {
      return identifier;
   }

   /**
    * Sets the identifier of this service.
    * 
    * @param identifier the service's identifier.
    */
   public void setIdentifier(final int identifier) {
      this.identifier = identifier;
   }

   public String getContext() {
      return context;
   }

   public void setContext(String context) {
      this.context = context;
   }

   /**
    * Gets the URIs of this service.
    * 
    * @return the service's URIs.
    */
   public Collection<Uri> getUris() {
      return uris;
   }

   /**
    * Sets the URIs of this service.
    * 
    * @param uris the service's URIs.
    */
   public void setUri(final Collection<Uri> uris) {
      this.uris = uris;
   }

   /**
    * Gets the controller this service is mapped to.
    * 
    * @return the controller this service is mapped to.
    */
   public Controller getController() {
      return controller;
   }

   /**
    * Sets the controller this service is mapped to.
    * 
    * @param controller the controller this service is mapped to.
    */
   public void setController(final Controller controller) {
      this.controller = controller;
   }

   /**
    * Return a consolidated list of methods for all the controllers
    * 
    * @return collection of methods
    */
   public Collection<Method> getMethods() {
      ArrayList<Method> methods = new ArrayList<Method>();
      for (Method method : getController().getMethods()) {
         methods.add(method);
      }
      return methods;
   }

   /**
    * Asserts that this object is valid
    */
   public void assertValid() {
      assert uris != null && uris.size() > 0 : "Missing uris "
         + this.toString();
      assert controller != null : "Missing controller " + this.toString();

      controller.assertValid();

   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean equals(final Object obj) {
      boolean result;
      if (obj instanceof Service) {
         final Service service = (Service) obj;
         result =
            new EqualsBuilder().append(identifier, service.identifier)
               .append(controller, service.controller).isEquals();
      } else {
         result = false;
      }
      return result;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public int hashCode() {
      return new HashCodeBuilder().append(identifier).append(uris)
         .append(controller).toHashCode();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public int compareTo(final Service service) {
      int result;
      if (service == null) {
         result = 1;
      } else {
         result =
            new CompareToBuilder().append(uris.iterator().next().getUri(),
               service.getUris().iterator().next().getUri()).toComparison();
      }
      return result;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String toString() {
      return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
         .append("identifier", identifier).append("uris", uris)
         .append("controller", controller).toString();
   }

}
