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

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.javadoc.AnnotationValue;

/**
 * Parses the <code>params</code> attribute of <code>@RequestMapping</code> annotation.
 */
public class RequestMappingParamsParser {

   /**
    * The pattern of the <code>params</code> attribute.
    */
   public static final Pattern PATTERN = Pattern.compile("\"(.+?)\"", Pattern.CASE_INSENSITIVE);

   /**
    * The <code>params</code> attribute of <code>@RequestMapping</code> annotation.
    */
   private AnnotationValue value;

   /**
    * No-argument constructor for this class to be used as a bean.
    */
   public RequestMappingParamsParser() {
      super();
   }

   /**
    * Constructs this parser from the <code>params</code> attribute of <code>@RequestMapping</code>
    * annotation.
    * 
    * @param value           the <code>params</code> attribute.
    */
   public RequestMappingParamsParser(final AnnotationValue value) {
      this.value = value;
   }

   /**
    * Gets the <code>params</code> attribute of <code>@RequestMapping</code> annotation.
    * 
    * @return                the <code>params</code> attribute.
    */
   public AnnotationValue getValue() {
      return value;
   }

   /**
    * Sets the <code>params</code> attribute of <code>@RequestMapping</code> annotation.
    * 
    * @param value           the <code>params</code> attribute.
    */
   public void setValue(final AnnotationValue value) {
      this.value = value;
   }

   /**
    * Parses the <code>params</code> attribute of <code>@RequestMapping</code> annotation.
    * 
    * @return                the name-value pairs after parsing.
    */
   public Collection<NameValuePair> parse() {
      final Collection<NameValuePair> pairs = new ArrayList<NameValuePair>();
      try {
         final Matcher matcher = PATTERN.matcher(value.toString());
         while (matcher.find()) {
            pairs.add(new NameValuePair(matcher.group(1)));
         }
      } catch (NullPointerException e) {
         /* exception ignored */
      }
      return pairs;
   }

}
