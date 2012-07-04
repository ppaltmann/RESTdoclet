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
package com.iggroup.oss.restdoclet.doclet.type;

import static org.apache.commons.lang.StringUtils.trimToNull;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * URI container
 */
public class Uri implements Comparable<Uri> {

   private String uri;
   private Boolean deprecated = false;

   /**
    * @return "deprecated" if this URI is deprecated
    */
   public Boolean getDeprecated() {
      return deprecated;
   }

   /**
    * Set whether this URI is deprecated or not
    * 
    * @param deprecated deprecated true or false
    */
   public void setDeprecated(boolean deprecated) {
      this.deprecated = deprecated;
   }

   /**
    * The Java type of this parameter. The type of application-parameters is
    * <code>java.lang.String</code>.
    */
   private String type = String.class.getName();

   /**
    * No-argument constructor for this class to be used as a bean or by JiBX
    * binding.
    */
   public Uri() {
      super();
   }

   /**
    * Constructor.
    * 
    * @param uri uri value
    * @param deprecated value true or false
    */
   public Uri(final String uri, final boolean deprecated) {
      super();
      this.uri = trimToNull(uri);
      setDeprecated(deprecated);
   }

   /**
    * Uri getter.
    * 
    * @return uri
    */
   public String getUri() {
      return uri;
   }

   /**
    * Uri setter
    * 
    * @param uri uri value
    */
   public void setUri(final String uri) {
      this.uri = uri;
      assertValid();
   }

   /**
    * Paramter type getter
    * 
    * @return type
    */
   public String getType() {
      return type;
   }

   /**
    * Sets the Java type of this parameter.
    * 
    * @param type the parameter's Java type.
    */
   public void setType(final String type) {
      this.type = trimToNull(type);
   }

   /**
    * Asserts that this object is valid
    */
   public void assertValid() {
      assert uri != null;
      assert uri.length() > 0 && uri.contains("/") : "Invalid uri "
         + this.toString();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean equals(final Object obj) {
      boolean result;
      if (obj instanceof Uri) {
         final Uri param = (Uri) obj;
         result =
            new EqualsBuilder().append(uri, param.uri)
               .append(type, param.type).isEquals();
      } else {
         result = false;
      }
      return result;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public int hashCode() {
      return new HashCodeBuilder().append(uri).append(type).toHashCode();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public int compareTo(final Uri param) {
      int result;
      if (param == null) {
         result = 1;
      } else {
         result =
            new CompareToBuilder().append(uri, param.uri)
               .append(type, param.type).toComparison();
      }
      return result;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String toString() {
      return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
         .append("uri", uri).append("deprecated", deprecated).toString();
   }

}
