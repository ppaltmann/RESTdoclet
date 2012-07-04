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

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;

import com.sun.javadoc.AnnotationValue;
import com.sun.javadoc.ParamTag;
import com.sun.javadoc.Parameter;

/**
 * This class creates documentation for a path-parameter. These parameters are
 * method-arguments annotated with <code>@PathParam</code>.
 */
public class PathParameter extends AbstractDocType {

   /**
    * No-argument constructor for this class to be used as a bean or by JiBX
    * binding.
    */
   public PathParameter() {
      super();
   }

   /**
    * Constructs this parameter from its Java documentation object and Java
    * documentation tags of the parameters of the method it belongs to.
    * <p>
    * The name of this parameter is the value of <code>@PathParam</code>
    * annotation. If not defined, the name of its argument is used.
    * 
    * @param param the parameter's Java documentation object.
    * @param tags the Java documentation tags of the parameters of the method
    *           this parameter belongs to.
    */
   public PathParameter(final Parameter param, final ParamTag[] tags) {
      super();
      initName(param);
      initType(param);
      initJavadoc(param, tags);
      assertValid();
   }

   /**
    * Initialises the name of this parameter.
    * 
    * @param param the parameter's Java documentation object.
    */
   private void initName(final Parameter param) {
      final AnnotationValue value =
         elementValue(param, PathVariable.class, "value");
      if (value == null) {
         setName(param.name());
      } else {
         if (StringUtils.isBlank(value.value().toString())) {
            setName(param.name());
         } else {
            setName(value.value().toString().trim());
         }
      }
   }

}
