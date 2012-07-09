/*
 * #%L restdoc-doclet %% Copyright (C) 2012 IG Group %% Licensed under the
 * Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License
 * at http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable
 * law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License. #L%
 */
package com.iggroup.oss.restdoclet.doclet.type.builder;

import static com.iggroup.oss.restdoclet.doclet.util.AnnotationUtils.elementValue;

import org.springframework.web.bind.annotation.RequestBody;

import com.iggroup.oss.restdoclet.doclet.type.BodyParameter;
import com.sun.javadoc.AnnotationValue;
import com.sun.javadoc.ParamTag;
import com.sun.javadoc.Parameter;

/**
 * This class populates a BodyParameter class from JavaDoc types.
 */
public class BodyParameterBuilder extends FieldedParameterBuilder {

   /**
    * Populate a BodyParameter
    * 
    * @param type type to populate
    * @param param parameter info
    * @param tags tag info
    * @return populated type
    */
   public BodyParameter build(BodyParameter type, final Parameter param,
                              final ParamTag[] tags) {
      super.build(type, param, tags);
      type.assertValid();
      return type;
   }

   @Override
   protected AnnotationValue getAnnotationValue(final Parameter param) {
      return elementValue(param, RequestBody.class, "value");
   }

}
