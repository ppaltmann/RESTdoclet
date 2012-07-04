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

import com.iggroup.oss.restdoclet.doclet.type.AbstractDocType;

/**
 * This predicate is used to search a parameter by its name.
 */
public class ParameterNamePredicate implements Predicate {

   /**
    * The name to searched for.
    */
   private String name;

   /**
    * Constructs this predicate from the name to be searched for.
    * 
    * @param name            the name to search for.
    */
   public ParameterNamePredicate(final String name) {
      this.name = name;
   }

   /**
    * Gets the name to be searched for.
    * 
    * @return                the name to searched for.
    */
   public String getName() {
      return name;
   }

   /**
    * Sets the name to be searched for.
    * 
    * @param name            the name to searched for.
    */
   public void setName(final String name) {
      this.name = name;
   }

   /**
    * {@inheritDoc}
    */
   public boolean evaluate(final Object object) {
      boolean result;
      if (object instanceof AbstractDocType
            && StringUtils.equals(name, ((AbstractDocType) object).getName())) {
         result = true;
      } else {
         result = false;
      }
      return result;
   }

}
