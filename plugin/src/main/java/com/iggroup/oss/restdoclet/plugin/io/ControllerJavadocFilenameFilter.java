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

import java.io.File;
import java.io.FilenameFilter;

import com.iggroup.oss.restdoclet.doclet.type.Controller;

/**
 * Creates a filename filter for documentation of controllers created by XmlDoclet.
 */
public class ControllerJavadocFilenameFilter implements FilenameFilter {

   /**
    * {@inheritDoc}
    */
   public boolean accept(final File dir, final String name) {
      boolean result;
      if (name.endsWith(Controller.FILE_SUFFIX)) {
         result = true;
      } else {
         result = false;
      }
      return result;
   }

}
