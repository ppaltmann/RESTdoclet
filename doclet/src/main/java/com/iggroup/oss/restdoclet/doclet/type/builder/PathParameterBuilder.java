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

import static com.iggroup.oss.restdoclet.doclet.util.AnnotationUtils.elementValue;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;

import com.iggroup.oss.restdoclet.doclet.type.PathParameter;
import com.sun.javadoc.AnnotationValue;
import com.sun.javadoc.ParamTag;
import com.sun.javadoc.Parameter;

/**
 * This class populates a PathParameter class from JavaDoc types.
 */
public class PathParameterBuilder extends BaseTypeBuilder {

   /**
    * Populate a PathParameter
    * 
    * @param type type to populate
    * @param param param info
    * @param tags tag info
    * @return populated type
    */
   public PathParameter build(PathParameter type, final Parameter param, final ParamTag[] tags) {
      super.build(type, param, tags);
      initName(type, param);
      type.assertValid();
      return type;
   }

   /**
    * Initialises the name of this parameter.
    * 
    * @param param the parameter's Java documentation object.
    */
   private void initName(PathParameter type, final Parameter param) {
      final AnnotationValue value =
         elementValue(param, PathVariable.class, "value");
      if (value == null) {
         type.setName(param.name());
      } else {
         if (StringUtils.isBlank(value.value().toString())) {
            type.setName(param.name());
         } else {
            type.setName(value.value().toString().trim());
         }
      }
   }

}
