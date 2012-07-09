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
package com.iggroup.oss.restdoclet.doclet.type.builder;

import com.iggroup.oss.restdoclet.doclet.type.FieldParameter;
import com.iggroup.oss.restdoclet.doclet.util.DocTypeUtils;
import com.sun.javadoc.FieldDoc;

/**
 * This class populates a FieldParameter class from JavaDoc types.
 */
public class FieldParameterBuilder {

   /**
    * Populates a FieldParameter
    * 
    * @param type type to populate
    * @param fieldDoc field doc
    * @return populated type
    */
   public FieldParameter build(FieldParameter type, final FieldDoc fieldDoc) {
      type.setName(fieldDoc.name());
      type.setType(DocTypeUtils.getTypeName(fieldDoc.type()));

      type.assertValid();

      return type;
   }

}
