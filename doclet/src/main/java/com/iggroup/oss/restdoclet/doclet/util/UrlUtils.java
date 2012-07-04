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

import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * Contains utility methods for parsing URLs defined in
 * <code>RESTURLTreeHandlerMapping</code>.
 */
/**
 * @author morschr
 */
public final class UrlUtils {

   /**
    * The pattern of URLs defined in <code>RESTURLTreeHandlerMapping</code>.
    */
   public static final Pattern PARAM_PATTERN = Pattern.compile(
      "\\(\\*:.+?\\)", Pattern.CASE_INSENSITIVE);

   /**
    * No-argument constructor to "silence" PMD.
    */
   private UrlUtils() {
      super();
   }

   /**
    * Convert a URI list in the form of {"","",...""} into a String[] of URIs
    * 
    * @param multiUri URI list in the form "" or {"","",...""}
    * @return URIs
    */
   public static String[] parseMultiUri(final String multiUri) {

      String value;

      value = StringUtils.removeStart(multiUri.trim(), "{");
      value = StringUtils.removeEnd(value, "}");
      value = value.replace("\"", "");
      value = value.replace(" ", "");

      return value.split(",");
   }
}
