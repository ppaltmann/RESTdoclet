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

import static com.iggroup.oss.restdoclet.doclet.util.AnnotationUtils.elementValue;

import org.springframework.web.bind.annotation.ModelAttribute;

import com.sun.javadoc.AnnotationValue;
import com.sun.javadoc.ParamTag;
import com.sun.javadoc.Parameter;

/**
 * This class creates documentation for a model-parameter.
 */
public class ModelParameter extends FieldedParameter {

   /**
    * No-argument constructor for this class to be used as a bean or by JiBX
    * binding.
    */
   public ModelParameter() {
      super();
   }

   /**
    * Constructs this parameter from its Java documentation object and Java
    * documentation tags of the parameters of the method it belongs to.
    * 
    * @param param the parameter's Java documentation object.
    * @param tags the Java documentation tags of the parameters of the method
    *           this parameter belongs to.
    */
   public ModelParameter(final Parameter param, final ParamTag[] tags) {
      super(param, tags);
      assertValid();
   }

   @Override
   protected AnnotationValue getAnnotationValue(final Parameter param) {
      return elementValue(param, ModelAttribute.class, "value");
   }

}
