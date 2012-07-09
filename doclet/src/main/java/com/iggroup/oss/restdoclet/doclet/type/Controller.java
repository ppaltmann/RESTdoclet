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

import java.util.Collection;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.log4j.Logger;

/**
 * This class creates documentation for Spring's controllers. The classes
 * should be annotated with <code>@Controller</code>.
 */
public class Controller extends BaseType {

   /**
    * Logger
    */
   private static final Logger LOG = Logger.getLogger(Controller.class);

   /**
    * The filename suffix of documentation created by XmlDoclet for
    * controllers. For example, if the classname is <code>foo.Baz</code>, its
    * documentation will be created in <code>foo/Baz.controller.xml</code>.
    */
   public static final String FILE_SUFFIX = ".controller.xml";

   /**
    * The methods annotated with <code>@RequestMapping</code> in the source
    * class.
    */
   private Collection<Method> methods;

   /**
    * No-argument constructor for this class to be used as a bean or by JiBX
    * binding.
    */
   public Controller() {
      /* no-argument constructor */
   }

   /**
    * Constructs this controller using its Java type, its documentation and its
    * methods. The methods are annotated with <code>@RequestMapping</code>.
    * <p>
    * This constructor is used while generating services.
    * 
    * @param type the controller's Java type.
    * @param javadoc the controller's documentation.
    * @param methods the controller's methods.
    */
   public Controller(final String type, final String javadoc,
                     final Collection<Method> methods) {
      super(type, type, javadoc);
      this.methods = methods;
   }


   /**
    * Gets the methods in this controller annotated with
    * <code>@RequestMapping</code> in the source class.
    * 
    * @return the controller's methods.
    */
   public Collection<Method> getMethods() {
      return methods;
   }

   /**
    * Sets the methods in this controller annotated with
    * <code>@RequestMapping</code> in the source class.
    * 
    * @param methods the controller's methods.
    */
   public void setMethods(final Collection<Method> methods) {
      if (methods == null) {
         throw new IllegalArgumentException("setMethods(null)");
      } else {
         this.methods = methods;
      }
   }

   /**
    * Asserts that this object is valid
    */
   @Override
   public void assertValid() {

      super.assertValid();
      assert methods != null : "Missing methods " + this.toString();

      for (Method m : methods) {
         m.assertValid();
      }
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String toString() {
      return new ToStringBuilder(ToStringStyle.SHORT_PREFIX_STYLE)
      .append("type", super.toString()).append("methods", methods)
      .toString();
   }

}
