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

import static org.apache.commons.lang.StringUtils.isBlank;

import com.iggroup.oss.restdoclet.doclet.type.FieldedParameter;
import com.iggroup.oss.restdoclet.doclet.util.DocletUtils;
import com.sun.javadoc.AnnotationValue;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.ParamTag;
import com.sun.javadoc.Parameter;

/**
 * This class populates a FieldedParameter class from JavaDoc types.
 */
public abstract class FieldedParameterBuilder extends BaseTypeBuilder {

   /**
    * Populat a FieldeParameter
    * 
    * @param type the type to populate
    * @param param param info
    * @param tags tag info
    * @return the populated info
    */
   public FieldedParameter build(FieldedParameter type, final Parameter param,
                                 final ParamTag[] tags) {
      super.build(type, param, tags);
      initName(type, param);
      initFields(type, param.type().asClassDoc());
      return type;
   }

   /**
    * Initialises the field-parameters in this model-parameter.
    * 
    * @param classDoc the parameter's Java documentation object.
    */
   private void initFields(FieldedParameter type, final ClassDoc classDoc) {

      type.setFields(DocletUtils.getPublicFields(classDoc));

   }

   /**
    * Initialises the name of this parameter.
    * 
    * @param param the parameter's Java documentation object.
    */
   private void initName(FieldedParameter type, final Parameter param) {

      final AnnotationValue value = getAnnotationValue(param);
      if (value == null) {
         type.setName(param.name());
      } else {
         if (isBlank(value.value().toString())) {
            type.setName(param.name());
         } else {
            type.setName(value.value().toString().trim());
         }
      }
   }

   /**
    * Abstract method to determine annotation value from a parameter for a
    * particular parameter subtype
    * 
    * @param param paramter
    * @return annotation value
    */
   protected abstract AnnotationValue getAnnotationValue(final Parameter param);

}
