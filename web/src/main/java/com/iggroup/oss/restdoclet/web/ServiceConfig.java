/*
 * #%L
 * restdoc-indexer
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
package com.iggroup.oss.restdoclet.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.jibx.runtime.JiBXException;

import com.iggroup.oss.restdoclet.doclet.RestDocumentationProperties;
import com.iggroup.oss.restdoclet.doclet.type.Service;
import com.iggroup.oss.restdoclet.doclet.type.Services;
import com.iggroup.oss.restdoclet.doclet.type.Uri;
import com.iggroup.oss.restdoclet.doclet.util.JiBXUtils;

/**
 * Static class for accessing service configuration loaded from
 */
public final class ServiceConfig {

   private static final Logger LOGGER = Logger.getLogger(ServiceConfig.class);

   private static String configPath;

   /**
    * Unused default constructor
    */
   private ServiceConfig() {
   }

   /**
    * Set the service configuration file system path
    * 
    * @param configPath file system path
    * @return the specified path if found
    */
   public static String setConfigPath(final String configPath) {

      LOGGER.info("Setting RESTdoc config path to " + configPath);

      ServiceConfig.configPath = configPath;
      if (configPath == null || configPath.isEmpty()) {
         throw new IllegalArgumentException(
            "Service configuration path not found");
      }

      return configPath;
   }

   /**
    * Return a list of service application names
    * 
    * @return list of names
    */
   public static Collection<String> getApplicationNames() {

      Collection<String> serviceNames = new ArrayList<String>();

      File configDir = new File(configPath);

      LOGGER.debug("getApplicationNames " + configDir.getName());

      if (configDir != null && configDir.isDirectory()) {

         String[] children = configDir.list();
         for (int i = 0; i < children.length; i++) {

            File serviceDir = new File(configDir, children[i]);
            if (serviceDir != null && serviceDir.isDirectory()) {
               serviceNames.add(children[i]);
            } else {
               LOGGER.warn(children[i] + " is not a directory");
            }

         }
      } else {
         LOGGER.error(configDir.getName() + " is not a directory");
      }

      return serviceNames;

   }

   /**
    * Find services with URI's containing the given search term
    * 
    * @param searchTerm
    * @return list of service summary objects
    * @throws FileNotFoundException
    * @throws JiBXException if the service configuration files don't parse
    */
   public static Collection<Service> findServices(final String searchTerm)
      throws FileNotFoundException, JiBXException {

      LOGGER.info("Searching for " + searchTerm);

      Collection<Service> matchingServices = new ArrayList<Service>();

      String lowerSearchTerm = searchTerm.toLowerCase();
      for (String application : getApplicationNames()) {

         for (Service service : getServices(application)) {

            for (Uri uri : service.getUris()) {

               if (uri.getUri().contains(lowerSearchTerm)) {
                  matchingServices.add(service);
               }
            }
         }
      }

      return matchingServices;
   }

   /**
    * Return the services for an application
    * 
    * @param applicationName application name
    * @return list of service summaries
    * @throws FileNotFoundException
    * @throws JiBXException if the service configuration does not parse
    */
   public static Collection<Service> getServices(final String applicationName)
      throws FileNotFoundException, JiBXException {

      LOGGER.debug("getServices " + configPath + "/" + applicationName);
      Services services;

      File servicesConfigFile =
         new File(configPath + File.separator + applicationName
            + File.separator + "restdoc-services.xml");

      services =
         JiBXUtils.unmarshallServices(new FileInputStream(servicesConfigFile));

      for (Service service : services.getServices()) {
         service.setContext(applicationName);
      }

      return services.getServices();

   }

   /**
    * Get a service by application name and service id
    * 
    * @param applicationName
    * @param id
    * @return service
    * @throws FileNotFoundException
    * @throws JiBXException parsing error
    */
   public static Service getService(final String applicationName,
                                    final String id)
                                       throws FileNotFoundException, JiBXException {

      Service service;

      File serviceConfigFile =
         new File(configPath + File.separator + applicationName
            + File.separator + "restdoc-service-" + id + ".xml");

      service =
         JiBXUtils.unmarshallService(new FileInputStream(serviceConfigFile));

      service.setContext(applicationName);

      return service;

   }

   /**
    * Get an application's properties
    * 
    * @param applicationName
    * @return a restdoc property object
    * @throws IOException
    */
   public static RestDocumentationProperties getProperties(final String applicationName)
      throws IOException {

      File propFile =
         new File(configPath + File.separator + applicationName
            + File.separator + "rd.properties");

      return new RestDocumentationProperties(new FileInputStream(propFile));

   }

}
