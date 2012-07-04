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
import java.util.Arrays;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.iggroup.oss.restdoclet.plugin.util.ServiceUtils;

/**
 * Creates a filename filter for files containing Spring's beans.
 */
public class BeansFilenameFilter implements FilenameFilter {

   /**
    * The prefix of the file containing the service.
    */
   public static final String SERVICE_FILE_PREFIX = "-service-";

   /**
    * The excluded files.
    */
   public static final Collection<String> EXCLUDES = Arrays
      .asList(new String[] {"binding.xml", /* JiBX */
      "checkstyle-checker.xml", /* Checkstyle */
      "checkstyle-result.xml", /* Checkstyle */
      "coverage.xml", /* Cobertura */
      "ehcache.xml", /* Ehcache */
      "findbugs.xml", /* Findbugs */
      "plugin.xml", /* ? */
      "pom.xml", /* Maven */
      "rd-config.xml", /* RESTDocumentation */
      "resources.xml", /* ? */
      "wt-restdoc-servlet.xml", /* RESTDocumentation */
      "web.xml", /* web-application */
      "webapp-cache.xml", /* ? */
      "weblogic.xml"}); /* Weblogic */

   /**
    * The excluded filename patterns. These patterns are not required if beans
    * are not searched in the <code>target</code> directory of Maven.
    */
   public static final Collection<Pattern> EXCLUDE_PATTERNS = Arrays
      .asList(new Pattern[] {
         Pattern.compile(".+?" + ServiceUtils.SERVICES_FILE_SUFFIX),
         /* RESTDocumentation */
         Pattern.compile(".+?" + SERVICE_FILE_PREFIX + "[0-9]+?"
            + ServiceUtils.SERVICE_FILE_SUFFIX),
         /* RESTDocumentation */
         Pattern.compile(".+?" + "-restdoc-config.xml"),
         /* RESTDocumentation */
         Pattern.compile("TEST-.+?.xml")});
   /* Surefire */

   /**
    * The filename suffix of XML files.
    */
   public static final String SUFFIX = ".xml";

   /**
    * The filename filter for filtering documentation of controllers created by
    * XmlDoclet}. This filter is not required if beans are not searched in the
    * <code>target</code> directory of Maven.
    */
   public static final ControllerJavadocFilenameFilter CONTROLLER_FILTER =
      new ControllerJavadocFilenameFilter();

   /**
    * Indicates if a filename is excluded.
    * 
    * @param name the filename.
    * @return <code>true</code> if the filename is excluded, <code>false</code>
    *         otherwise.
    */
   private boolean acceptExclude(final String name) {
      return EXCLUDES.contains(name);
   }

   /**
    * Indicates if the filename pattern is excluded.
    * 
    * @param name the filename.
    * @return <code>true</code> if the filename pattern is excluded,
    *         <code>false</code> otherwise.
    */
   private boolean acceptExcludePattern(final String name) {
      boolean result = false;
      for (Pattern pattern : EXCLUDE_PATTERNS) {
         final Matcher matcher = pattern.matcher(name);
         if (matcher.matches()) {
            result = true;
            break;
         }
      }
      return result;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean accept(final File dir, final String name) {
      boolean result;
      if (name.endsWith(SUFFIX) && !acceptExclude(name)
         && !acceptExcludePattern(name)
         && !CONTROLLER_FILTER.accept(dir, name)) {
         result = true;
      } else {
         result = false;
      }
      return result;
   }

}
