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

import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;

import com.iggroup.oss.restdoclet.doclet.type.ControllerSummary;

/**
 * This class is used to search a controller by its Java type.
 */
public class ControllerTypePredicate implements Predicate {

   /**
    * The Java type to search for.
    */
   private String type;

   /**
    * Constructs this class from the Java type to search for.
    * 
    * @param type            the Java type to be searched for.
    */
   public ControllerTypePredicate(final String type) {
      this.type = type;
   }

   /**
    * Gets the Java type to search for.
    * 
    * @return                the Java type to search for.
    */
   public String getType() {
      return type;
   }

   /**
    * Sets the Java type to search for.
    * 
    * @param type            the Java type to be search for.
    */
   public void setType(final String type) {
      this.type = type;
   }

   /**
    * {@inheritDoc}
    */
   public boolean evaluate(final Object object) {
      boolean result;
      if (object instanceof ControllerSummary) {
         result = StringUtils.equals(type, ((ControllerSummary) object).getType());
      } else {
         result = false;
      }
      return result;
   }

}
