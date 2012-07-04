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

/**
 * Contains utility-methods for Maven operations.
 */
public final class MavenUtils {

   /**
    * The name of POM file.
    */
   public static final String POM_FILE = "pom.xml";

   /**
    * The name of source-directory.
    */
   public static final String SRC_DIR = "src";

   /**
    * The name of test-directory.
    */
   public static final String TEST_DIR = "test";

   /**
    * The name of target-directory.
    */
   public static final String TARGET_DIR = "target";

   /**
    * The packaging used for web-applications.
    */
   public static final String WAR_PACKAGING = "war";

   /**
    * The packaging used for jars.
    */
   public static final String JAR_PACKAGING = "jar";

   /**
    * No-argument constructor to "silence" PMD.
    */
   private MavenUtils() {
      super();
   }

   /**
    * Gets the root (or project) directory of a project. The working-directory
    * can be any of the modules.
    * 
    * @param workingDirectory the working-directory.
    * @return the root-directory.
    */
   public static File getRoot(final File workingDirectory) {
      File ref = workingDirectory;
      while (ref.getParentFile() != null
         && new File(ref.getParentFile(), POM_FILE).exists()) {
         ref = ref.getParentFile();
      }
      return ref;
   }

}
