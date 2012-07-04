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
package com.iggroup.oss.restdoclet.plugin.io;

import static com.iggroup.oss.restdoclet.plugin.util.MavenUtils.SRC_DIR;
import static com.iggroup.oss.restdoclet.plugin.util.MavenUtils.TARGET_DIR;
import static com.iggroup.oss.restdoclet.plugin.util.MavenUtils.TEST_DIR;

import java.io.File;
import java.io.FileFilter;

/**
 * This class filters Maven's directories.
 */
public class MavenDirectoryFilter implements FileFilter {

   /**
    * Indicates if Maven's test directories are filtered.
    */
   private boolean filterTest;

   /**
    * Indicates if Maven's target directories are filtered.
    */
   private boolean filterTarget;

   /**
    * Indicates if Maven's test directories are filtered.
    * 
    * @return                <code>true</code> if test directories are filtered, <code>false</code>
    *                        otherwise.
    */
   public boolean isFilterTest() {
      return filterTest;
   }

   /**
    * Sets if Maven's test directories are filtered.
    * 
    * @param filter          <code>true</code> if test directories are filtered,
    *                        <code>false</code> otherwise.
    */
   public void setFilterTest(final boolean filter) {
      this.filterTest = filter;
   }

   /**
    * Indicates if Maven's target directories are filtered.
    * 
    * @return                <code>true</code> if target directories are filtered,
    *                        <code>false</code> otherwise.
    */
   public boolean isFilterTarget() {
      return filterTarget;
   }

   /**
    * Sets if Maven's target directories are filtered.
    * 
    * @param filterTarget    <code>true</code> if target directories are filtered,
    *                        <code>false</code> otherwise.
    */
   public void setFilterTarget(final boolean filterTarget) {
      this.filterTarget = filterTarget;
   }

   /**
    * Constructs this filter with if Maven's test and target directories have to be filtered.
    * 
    * @param filterTest      <code>true</code> if test directories are filtered, <code>false</code>
    *                        otherwise.
    * @param filterTarget    <code>true</code> if target directories are filtered,
    *                        <code>false</code> otherwise.
    */
   public MavenDirectoryFilter(final boolean filterTest, final boolean filterTarget) {
      this.filterTest = filterTest;
      this.filterTarget = filterTarget;
   }

   /**
    * Checks if a file is a Maven's test directory.
    * 
    * @param  dir            the directory to be checked.
    * @return                <code>true</code> if the file is a test directory, <code>false</code>
    *                        otherwise.
    */
   public boolean isTestDirectory(final File dir) {
      boolean result;
      if (dir.isDirectory() && TEST_DIR.equals(dir.getName())) {
         /* check if maven test-directory */
         if (dir.getParentFile() != null
               && SRC_DIR.equals(dir.getParentFile().getName())) {
            result = true;
         } else {
            result = false;
         }
      } else {
         result = false;
      }
      return result;
   }

   /**
    * Checks if a file is a Maven's target directory.
    * 
    * @param  dir            the directory to be checked.
    * @return                <code>true</code> if the file is a target directory, <code>false</code>
    *                        otherwise.
    */
   public boolean isTargetDirectory(final File dir) {
      boolean result;

      if (dir.isDirectory() && TARGET_DIR.equals(dir.getName())) {
         result = true;
      } else {
         result = false;
      }
      return result;
   }

   /**
    * {@inheritDoc}
    */
   public boolean accept(final File file) {
      if (filterTest && isTestDirectory(file)) {
         return false;
      }

      if (filterTarget && isTargetDirectory(file)) {
         return false;
      }

      return file.isDirectory();
   }

}
