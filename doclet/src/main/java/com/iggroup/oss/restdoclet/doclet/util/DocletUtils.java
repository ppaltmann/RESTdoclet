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
package com.iggroup.oss.restdoclet.doclet.util;

import static com.iggroup.oss.restdoclet.doclet.util.AnnotationUtils.isAnnotated;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

import com.iggroup.oss.restdoclet.doclet.type.Controller;
import com.iggroup.oss.restdoclet.doclet.type.FieldParameter;
import com.iggroup.oss.restdoclet.doclet.type.builder.FieldParameterBuilder;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.MethodDoc;

/**
 * Manages properties of <code>doclet</code> module. This class also contains
 * utility operations.
 */
public final class DocletUtils {

   /**
    * The name of the file that contains the properties. This file is packaged
    * with <code>doclet</code>'s archive.
    */
   public static final String PROPERTIES_FILE = "oss-restdoclet-doclet.properties";

   private static final String GETTER_PREFIX = "get";

   /**
    * Environment variable to control restdoc logging
    */
   private static final String RESTDOCLET_LOGGING = "RESTDOCLET_LOGGING";

   /**
    * The property containing the artifact-identifier of <code>doclet</code>
    * module.
    */
   public static final String ARTIFACT_ID = "project.artifactId";

   /**
    * The singleton instance of this class.
    */
   private static DocletUtils instance;

   /**
    * The properties of <code>doclet</code> module.
    */
   private final Properties properties;

   /**
    * Constructs the singleton instance of this class.
    * 
    * @throws IOException if <code>doclet</code>'s properties can't be read.
    */
   private DocletUtils() throws IOException {
      super();
      properties = new Properties();
      properties.load(Thread.currentThread().getContextClassLoader()
         .getResourceAsStream(PROPERTIES_FILE));
   }

   /**
    * Gets the singleton instance of this class.
    * 
    * @return the singleton instance of this class.
    * @throws IOException if <code>doclet</code>'s properties can't be read.
    */
   public static DocletUtils getInstance() throws IOException {
      synchronized (PROPERTIES_FILE) {
         if (instance == null) {
            instance = new DocletUtils();
         }
         return instance;
      }
   }

   /**
    * Gets the properties of <code>doclet</code> module.
    * 
    * @return <code>doclet</code>'s properties.
    */
   public Properties getProperties() {
      return properties;
   }

   /**
    * Sets the properties of <code>doclet</code> module. This operation is not
    * supported.
    * 
    * @param properties <code>doclet</code>'s properties.
    */
   public void setProperties(final Properties properties) {
      throw new UnsupportedOperationException(
         "DocletUtils.setProperties(Properties)");
   }

   /**
    * Gets the artifact-identifier of <code>doclet</code> module.
    * 
    * @return the artifact-identifier.
    * @throws IOException if <code>doclet</code>'s properties can't be read.
    */
   public static String artifactIdentifier() throws IOException {
      return getInstance().properties.getProperty(ARTIFACT_ID);
   }

   /**
    * Determines if a class is Spring's controller.
    * 
    * @param classDoc the Java documentation object of the class.
    * @return <code>true</code> if the class is a Spring's controller,
    *         <code>false</code> otherwise.
    */
   public static boolean isController(final ClassDoc classDoc) {
      return isAnnotated(classDoc,
         org.springframework.stereotype.Controller.class);
   }

   /**
    * Gets the file containing the documentation of a class.
    * 
    * @param classDoc the Java documentation object of the class.
    * @return the file containing documentation.
    */
   public static File documentationFile(final ClassDoc classDoc) {
      final StringBuffer path = new StringBuffer();
      path.append(classDoc.qualifiedName().replace('.', File.separatorChar));
      if (isController(classDoc)) {
         path.append(Controller.FILE_SUFFIX);
      } else {
         path.append(".class.xml");
      }
      final File doc = new File(path.toString());
      doc.getParentFile().mkdirs();
      return doc;
   }

   /**
    * Return a list of all fields that have a public getter
    * 
    * @param classDoc class documentation
    * @return list of all fields that have a public getter
    */
   public static Collection<FieldParameter> getPublicFields(final ClassDoc classDoc) {

      ArrayList<FieldParameter> fields = new ArrayList<FieldParameter>();

      MethodDoc[] methods = classDoc.methods();

      for (FieldDoc fieldDoc : classDoc.fields(false)) {

         boolean found = false;
         for (MethodDoc md : methods) {
            if (md.name().equalsIgnoreCase(GETTER_PREFIX + fieldDoc.name())) {
               found = true;
            }
         }

         if (found) {
            fields.add(new FieldParameterBuilder().build(new FieldParameter(),
               fieldDoc));
         }
      }

      return fields;
   }

   /**
    * Initialise log4j based on RESTDOC_DEBUG environment variable
    */
   public static void initialiseLogging() {

      String debugLevel = System.getProperty(RESTDOCLET_LOGGING);

      if (debugLevel != null
         && (debugLevel.equalsIgnoreCase("debug")
            || debugLevel.equalsIgnoreCase("info") || debugLevel
            .equalsIgnoreCase("error"))) {

         System.out
         .println("Setting RESTDoclet logging level to : "
            + debugLevel);

         try {

            Properties logProperties = new Properties();

            InputStream is =
               DocletUtils.class.getClassLoader().getResourceAsStream(
                  "log4j.properties." + debugLevel.toLowerCase());

            if (is == null) {
               System.err
               .println("[ERROR] Failed to load file : log4j.properties."
                  + debugLevel.toLowerCase());
            } else {
               logProperties.load(is);
               PropertyConfigurator.configure(logProperties);
            }

         } catch (Exception ioe) {
            System.err
            .println("[ERROR] - Failed to initialised RESTDoclet doclet logging");
            ioe.printStackTrace();
         }

      }
   }
}
