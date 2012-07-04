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
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestParam;

import com.sun.javadoc.AnnotationValue;
import com.sun.javadoc.ParamTag;
import com.sun.javadoc.Parameter;

/**
 * This class creates documentation for a request-parameter. These parameters
 * are method-arguments annotated with <code>@RequestParam</code>.
 */
public class RequestParameter extends AbstractDocType {

   private static final Logger LOG = Logger.getLogger(RequestParameter.class);

   /**
    * Whether this parameter is required or not
    */
   private boolean required = true;

   /**
    * An optional default value for this parameter
    */
   private String defaultValue = "";

   /**
    * No-argument constructor for this class to be used as a bean or by JiBX
    * binding.
    */
   public RequestParameter() {
      super();
   }

   /**
    * Constructs this parameter from its Java documentation object and Java
    * documentation tags of the parameters of the method it belongs to.
    * <p>
    * The name of this parameter is the value of <code>@RequestParam</code>
    * annotation. If not defined, the name of its argument is used.
    * 
    * @param param the parameter's Java documentation object.
    * @param tags the Java documentation tags of the parameters of the method
    *           this parameter belongs to.
    */
   public RequestParameter(final Parameter param, final ParamTag[] tags) {
      super();
      initName(param);
      initType(param);
      initJavadoc(param, tags);
      initRequired(param, tags);
      initDefaultValue(param, tags);
      assertValid();
   }

   /**
    * Initialises the name of this parameter.
    * 
    * @param param the parameter's Java documentation object.
    */
   private void initName(final Parameter param) {
      final AnnotationValue value =
         elementValue(param, RequestParam.class, "value");
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

   /**
    * Initialises the required value of this parameter.
    * 
    * @param param the parameter's Java documentation object.
    * @param tags the Java documentation tags of the parameters of the method
    *           this parameter belongs to.
    */
   private void initRequired(final Parameter param, final ParamTag[] tags) {
      final AnnotationValue value =
         elementValue(param, RequestParam.class, "required");
      if (value != null) {
         required = Boolean.getBoolean(value.value().toString().trim());
      }

   }

   /**
    * Initialises the default value of this parameter.
    * 
    * @param param the parameter's Java documentation object.
    * @param tags the Java documentation tags of the parameters of the method
    *           this parameter belongs to.
    */
   private void initDefaultValue(final Parameter param, final ParamTag[] tags) {
      final AnnotationValue value =
         elementValue(param, RequestParam.class, "defaultValue");
      if (value != null) {
         defaultValue = value.value().toString().trim();
      }

   }

   /**
    * Returns whether this parameter is required or not
    * 
    * @return whether this parameter is required or not
    */
   public boolean isRequired() {
      return required;
   }

   /**
    * Set whether this parameter is required or not
    * 
    * @param required required or not
    */
   public void setRequired(boolean required) {
      this.required = required;
   }

   /**
    * Get the (optional) default value for this parameter
    * 
    * @return the default value for this parameter or "" if unspecified
    */
   public String getDefaultValue() {
      return defaultValue;
   }

   /**
    * Set the (optional) default value for this parameter
    * 
    * @param defaultValue parameter default value
    */
   public void setDefaultValue(String defaultValue) {
      this.defaultValue = defaultValue;
   }

   /**
    * Asserts that this object is valid
    */
   @Override
   public void assertValid() {
      super.assertValid();
      if (getJavadoc() == null || getJavadoc().isEmpty()) {
         LOG.warn("Missing javadoc " + this.toString());
      }
   }

}
