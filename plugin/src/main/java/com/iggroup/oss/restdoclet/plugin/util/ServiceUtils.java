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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.iggroup.oss.restdoclet.doclet.type.Controller;
import com.iggroup.oss.restdoclet.plugin.io.ControllerJavadocFilenameFilter;
import com.iggroup.oss.restdoclet.plugin.io.DirectoryBuilder;
import com.iggroup.oss.restdoclet.plugin.io.MavenDirectoryFilter;

/**
 * This class contains utility methods for generating services.
 */
public final class ServiceUtils {

   /**
    * The prefix of the file containing the services.
    */
   public static final String SERVICES_FILE_PREFIX = "restdoc";

   /**
    * The suffix of the file containing the services.
    */
   public static final String SERVICES_FILE_SUFFIX = "-services.xml";

   /**
    * The prefix of the file containing the service.
    */
   public static final String SERVICE_FILE_PREFIX = "-service-";

   /**
    * The suffix of the file containing the service.
    */
   public static final String SERVICE_FILE_SUFFIX = ".xml";

   private static final Logger LOG = Logger.getLogger(ServiceUtils.class);

   /**
    * Private constructor to "silence" PMD.
    */
   private ServiceUtils() {
      super();
   }

   /**
    * Collects documentation of controllers created by XmlDoclet. It collects
    * documentation from the current directory and all its sub-directories
    * recursively.
    * 
    * @param start the directory to start looking for documentation.
    * @param javadocs the collection to which documentation files are added.
    */
   private static void collectControllerJavadocs(final File start,
                                                 final Collection<File> javadocs) {
      final ControllerJavadocFilenameFilter jfilter =
         new ControllerJavadocFilenameFilter();
      for (File javadoc : start.listFiles(jfilter)) {
         javadocs.add(javadoc);
         LOG.info(javadoc.getAbsolutePath());
      }
      final MavenDirectoryFilter dfilter =
         new MavenDirectoryFilter(true, false);
      for (File dir : start.listFiles(dfilter)) {
         collectControllerJavadocs(dir, javadocs);
      }
   }

   /**
    * This method collects documentation of controllers created by XmlDoclet.
    * It collects documentation from the current directory and all its
    * sub-directories.
    * 
    * @param start the directory to start looking for documentation.
    * @return the collection to which documentation files are added.
    */
   public static Collection<File> collectControllerJavadocs(final File start) {
      final Collection<File> javadocs = new ArrayList<File>();
      LOG.info("Controllers: " + start);
      collectControllerJavadocs(start, javadocs);
      return javadocs;
   }

   /**
    * Searches a list of controllers for a particular Java type.
    * 
    * @param type the Java type of the controller to search for.
    * @param controllers the list of controllers.
    * @return the controller of the Java type searched for.
    * @throws JavadocNotFoundException if the controller of the Java type can't
    *            be found.
    */
   public static Controller controller(final String type,
                                       final Collection<Controller> controllers)
                                          throws JavadocNotFoundException {
      for (Controller controller : controllers) {
         if (StringUtils.equals(type, controller.getType())) {
            return controller;
         }
      }
      throw new JavadocNotFoundException(type);
   }

   /**
    * Returns a file containing the service corresponding to an identifier.
    * 
    * @param dirs the directories containing all the services.
    * @param identifier the identifier of the service.
    * @return the service file.
    * @throws IOException if the properties of <code>web</code> module can't be
    *            read.
    */
   public static File serviceFile(final DirectoryBuilder dirs,
                                  final int identifier) throws IOException {
      return new File(dirs.getClassesDirectory(), SERVICES_FILE_PREFIX
         + SERVICE_FILE_PREFIX + identifier + SERVICE_FILE_SUFFIX);
   }

   /**
    * Returns the file containing the listing of services.
    * 
    * @param dirs the directories containing all the services.
    * @return the file containing the listing of services.
    * @throws IOException if the properties of <code>web</code> module can't be
    *            read.
    */
   public static File servicesFile(final DirectoryBuilder dirs)
      throws IOException {
      return new File(dirs.getClassesDirectory(), SERVICES_FILE_PREFIX
         + SERVICES_FILE_SUFFIX);
   }

}
