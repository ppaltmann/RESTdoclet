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
package com.iggroup.oss.restdoclet.doclet;

import static com.iggroup.oss.restdoclet.doclet.util.AnnotationUtils.isAnnotated;
import static com.iggroup.oss.restdoclet.doclet.util.JiBXUtils.marshallController;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.jibx.runtime.JiBXException;

import com.iggroup.oss.restdoclet.doclet.type.Controller;
import com.iggroup.oss.restdoclet.doclet.type.builder.ControllerBuilder;
import com.iggroup.oss.restdoclet.doclet.util.DocletUtils;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.Doclet;
import com.sun.javadoc.LanguageVersion;
import com.sun.javadoc.RootDoc;

/**
 * Doclet to generate Java documentation in XML format.
 */
public final class XmlDoclet extends Doclet {

   private static final Logger LOG = Logger.getLogger(XmlDoclet.class);

   /**
    * Private constructor to "silence" PMD.
    */
   private XmlDoclet() {
      super();
      /* no-argument constructor */
   }

   /**
    * NOTE: Without this method present and returning LanguageVersion.JAVA_1_5,
    * Javadoc will not process generics because it assumes
    * LanguageVersion.JAVA_1_1
    * 
    * @return language version (hard coded to LanguageVersion.JAVA_1_5)
    */
   public static LanguageVersion languageVersion() {
      return LanguageVersion.JAVA_1_5;
   }

   /**
    * Generates Java documentation for controllers.
    * 
    * @param rootDoc the root Java documentation object.
    * @throws IoException
    * @throws JiBXException
    */
   public static void controllerDocs(final RootDoc rootDoc)
      throws IOException, JiBXException {

      LOG.info("Finding controllers.....");
      Boolean found = false;
      for (ClassDoc classDoc : rootDoc.classes()) {
         LOG.debug("Controller? " + classDoc.qualifiedName() + ".java");
         if (isAnnotated(classDoc,
            org.springframework.stereotype.Controller.class)) {
            LOG.info("Found controller.  Generating javadoc xml for "
               + classDoc.qualifiedName() + ".java");
            marshallController(
               new ControllerBuilder().build(new Controller(), classDoc),
               DocletUtils.documentationFile(classDoc));
            found = true;
         }
      }
      if (!found) {
         throw new IllegalArgumentException(
            "No controllers with Spring @Controller annotation found");
      }
      LOG.info("Done finding controllers.");
   }

   /**
    * Invoked by the Java documentation tool.
    * 
    * @param rootDoc the root Java documentation object.
    * @return <code>true</code> if documentation was generated successfully,
    *         <code>false</code> otherwise.
    */
   public static boolean start(final RootDoc rootDoc) {
      boolean result = true;

      DocletUtils.initialiseLogging();
      try {
         LOG.info("Generating RESTDoc... ");
         controllerDocs(rootDoc);
      } catch (Exception e) {
         LOG.error(e);
         e.printStackTrace();
         result = false;
      }
      return result;
   }

}
