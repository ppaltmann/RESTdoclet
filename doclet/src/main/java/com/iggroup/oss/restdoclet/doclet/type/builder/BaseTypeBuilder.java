package com.iggroup.oss.restdoclet.doclet.type.builder;/*
 * #%L
 * restdoclet-doclet
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


import static org.apache.commons.lang.StringUtils.trimToNull;

import com.iggroup.oss.restdoclet.doclet.type.BaseType;
import com.iggroup.oss.restdoclet.doclet.util.DocTypeUtils;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.ParamTag;
import com.sun.javadoc.Parameter;

/**
 * This class populates a BaseType class from JavaDoc types.
 */
public class BaseTypeBuilder {

   /**
    * Populate a BaseType
    * 
    * @param type type to populate
    * @param name element name
    * @param typeName element type
    * @param doc element doc
    * @return populated type
    */
   public BaseType build(BaseType type, String name, String typeName,
                         String doc) {
      type.setName(name);
      type.setType(typeName);
      type.setJavadoc(doc);
      return type;
   }

   /**
    * Populate a BaseType
    * 
    * @param type to populate
    * @param classDoc class info
    * @return populated type
    */
   public BaseType build(BaseType type, ClassDoc classDoc) {
      type.setName(classDoc.name());
      type.setType(classDoc.qualifiedName());
      type.setJavadoc(trimToNull(classDoc.commentText()));
      return type;
   }

   /**
    * Populate a BaseType
    * 
    * @param type type to populate
    * @param param parameter info
    * @param tags parameter tags
    * @return populated type
    */
   public BaseType build(BaseType type, final Parameter param,
                         final ParamTag[] tags) {
      type.setName(param.name());
      initType(type, param);
      initJavadoc(type, param, tags);

      return type;
   }

   /**
    * Initialise type javadoc
    * 
    * @param type type to initialise
    * @param param parameter info
    * @param tags tag info
    */
   private void initJavadoc(BaseType type, final Parameter param,
                            final ParamTag[] tags) {

      for (ParamTag tag : tags) {
         if (param.name().equals(tag.parameterName())) {
            String comment = DocTypeUtils.getTypeDoc(param.type());
            type.setJavadoc(comment == null || comment.isEmpty() ? tag
               .parameterComment() : comment);
         }
      }
   }

   /**
    * Initialse type type
    * 
    * @param type type to initialise
    * @param param parameter info
    */
   private void initType(BaseType type, final Parameter param) {
      type.setType(DocTypeUtils.getTypeName(param.type()));
   }

}