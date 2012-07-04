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
 * Manages the properties of <code>web</code> module.
 */
public final class PropertyUtils {

   /**
    * The name of the file that contains the properties. This file is packaged
    * with <code>web</code>'s archive (JAR).
    */
   public static final String PROPERTIES_FILE = "oss-restdoclet-plugin.properties";

   /**
    * The property containing the artifact-identifier of the parent of
    * <code>web</code> module. The XPath of this property is
    * <code>/project/parent/artifactId</code>.
    */
   public static final String PARENT_ARTIFACT = "parent.artifactId";

   /**
    * The property containing the group-identifier of <code>web</code> module.
    * The XPath of this property is <code>/project/groupId</code>.
    */
   public static final String PROJECT_GROUP = "project.groupId";

   /**
    * The property containing the artifact-identifier of <code>web</code>
    * module. The XPath of this property is <code>/project/artifactId</code>.
    */
   public static final String PROJECT_ARTIFACT = "project.artifactId";

   /**
    * The property containing the version of <code>web</code> module. The XPath
    * of this property is <code>/project/version</code>.
    */
   public static final String PROJECT_VERSION = "project.version";

   /**
    * The singleton instance of this class.
    */
   private static PropertyUtils instance;

   /**
    * The properties of the <code>web</code> module.
    */
   private final transient Properties properties;

   /**
    * Constructs the singleton instance of this class.
    * 
    * @exception IllegalStateException if <code>web</code>'s properties can't
    *               be read.
    */
   private PropertyUtils() {
      super();
      properties = new Properties();
      try {
         properties.load(Thread.currentThread().getContextClassLoader()
            .getResourceAsStream(PROPERTIES_FILE));
      } catch (final IOException e) {
         throw new IllegalStateException(e.getMessage(), e);
      }
   }

   /**
    * Gets the singleton instance of this class.
    * 
    * @return the singleton instance of this class.
    */
   public static PropertyUtils getInstance() {
      synchronized (PROPERTIES_FILE) {
         if (instance == null) {
            instance = new PropertyUtils();
         }
         return instance;
      }
   }

   /**
    * Returns the artifact-identifier of the parent of <code>web</code> module.
    * 
    * @return the parent's artifact-identifier.
    */
   public static String parentArtifact() {
      return getInstance().properties.getProperty(PARENT_ARTIFACT);
   }

   /**
    * Returns the group-identifier of <code>web</code> module.
    * 
    * @return the group-identifier.
    */
   public static String projectGroup() {
      return getInstance().properties.getProperty(PROJECT_GROUP);
   }

   /**
    * Returns the artifact-identifier of <code>web</code> module.
    * 
    * @return the artifact-identifier.
    */
   public static String projectArtifact() {
      return getInstance().properties.getProperty(PROJECT_ARTIFACT);
   }

   /**
    * Returns the version of <code>web</code> module.
    * 
    * @return the version.
    */
   public static String projectVersion() {
      return getInstance().properties.getProperty(PROJECT_VERSION);
   }

}
