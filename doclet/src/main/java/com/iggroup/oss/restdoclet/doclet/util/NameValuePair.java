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

import static org.apache.commons.lang.StringUtils.indexOf;
import static org.apache.commons.lang.StringUtils.trimToNull;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * This class creates a name-value pair.
 */
public class NameValuePair {

   /**
    * The name.
    */
   private String name;

   /**
    * The value.
    */
   private String value;

   /**
    * No-argument constructor for this class to be used as a bean.
    */
   public NameValuePair() {
      super();
   }

   /**
    * Constructs this class with the name and the value.
    * 
    * @param name            the name.
    * @param value           the value.
    */
   public NameValuePair(final String name, final String value) {
      super();
      this.name = trimToNull(name);
      this.value = trimToNull(value);
   }

   /**
    * Constructs this pair with the string containing the name and the value. The name and the
    * value should be delimited by '='.
    * 
    * @param pair            the name-value pair.
    */
   public NameValuePair(final String pair) {
      super();
      initName(trimToNull(pair));
      initValue(trimToNull(pair));
   }

   /**
    * Initialises the name.
    * 
    * @param pair            the name-value pair.
    */
   private void initName(final String pair) {
      final int index = indexOf(pair, '=');
      if (index >= 0) {
         name = trimToNull(pair.substring(0, index));
      } else {
         name = trimToNull(pair);
      }
   }

   /**
    * Initialises the value.
    * 
    * @param pair            the name-value pair.
    */
   private void initValue(final String pair) {
      final int index = indexOf(pair, '=');
      if (index >= 0 && index != pair.length() - 1) {
         value = trimToNull(pair.substring(index + 1));
      }
   }

   /**
    * Gets the name.
    * 
    * @return                the name.
    */
   public String getName() {
      return name;
   }

   /**
    * Sets the name.
    * 
    * @param name            the name.
    */
   public void setName(final String name) {
      this.name = name;
   }

   /**
    * Gets the value.
    * 
    * @return                the value.
    */
   public String getValue() {
      return value;
   }

   /**
    * Sets the value.
    * 
    * @param value           the value.
    */
   public void setValue(final String value) {
      this.value = value;
   }

   /**
    * {@inheritDoc}
    */
   public boolean equals(final Object obj) {
      boolean result;
      if (obj instanceof NameValuePair) {
         final NameValuePair pair = (NameValuePair) obj;
         result = new EqualsBuilder()
               .append(name, pair.name)
               .append(value, pair.value)
               .isEquals();
      } else {
         result = false;
      }
      return result;
   }

   /**
    * {@inheritDoc}
    */
   public int hashCode() {
      return new HashCodeBuilder()
            .append(name)
            .append(value)
            .toHashCode();
   }

   /**
    * {@inheritDoc}
    */
   public String toString() {
      return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("name", name)
            .append("value", value)
            .toString();
   }

}
