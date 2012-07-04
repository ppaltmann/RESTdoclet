/*
 * #%L
 * restdoc-plugin
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
package com.iggroup.oss.restdoclet.plugin.util;

import java.io.IOException;
import java.util.Properties;

/**
 * Managing properties of <code>plugin</code> module.
 */
public final class PluginUtils {

   /**
    * The name of the file that contains the properties. This file is packaged with
    * <code>plugin</code>'s archive (JAR).
    */
   public static final String PROPERTIES_FILE = "oss-restdoclet-plugin.properties";

   /**
    * The property containing the artifact-identifier of <code>plugin</code> module.
    */
   public static final String ARTIFACT_ID = "project.artifactId";

   /**
    * The singleton instance of this class.
    */
   private static PluginUtils instance;

   /**
    * The properties of <code>plugin</code> module.
    */
   private final transient Properties properties;

   /**
    * Constructs the singleton instance of this class.
    * 
    * @throws IOException    if <code>plugin</code>'s properties can't be read.
    */
   private PluginUtils() throws IOException {
      super();
      properties = new Properties();
      properties.load(Thread.currentThread().getContextClassLoader()
            .getResourceAsStream(PROPERTIES_FILE));
   }

   /**
    * Gets the singleton instance of this class.
    * 
    * @return                the singleton instance of this class.
    * @throws IOException    if <code>plugin</code>'s properties can't be read.
    */
   public static PluginUtils getInstance() throws IOException {
      synchronized (PROPERTIES_FILE) {
         if (instance == null) {
            instance = new PluginUtils();
         }
         return instance;
      }
   }

   /**
    * Gets the artifact-identifier of <code>plugin</code> module.
    * 
    * @return                the artifact-identifier.
    * @throws IOException    if <code>plugin</code>'s properties can't be read.
    */
   public static String artifactIdentifier() throws IOException {
      return getInstance().properties.getProperty(ARTIFACT_ID);
   }

}
