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
package com.iggroup.oss.restdoclet.doclet.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;

import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;

import com.iggroup.oss.restdoclet.doclet.type.Controller;
import com.iggroup.oss.restdoclet.doclet.type.Service;
import com.iggroup.oss.restdoclet.doclet.type.Services;

/**
 * Utility class for marshalling and unmarshalling using JiBX.
 */
public final class JiBXUtils {

   /**
    * Encoding for XML data.
    */
   public static final String ENCODING = "UTF-8";

   /**
    * The indent to be used while marshalling XML.
    */
   public static final int INDENT = 3;

   /**
    * Constructor to "silence" PMD warning.
    */
   private JiBXUtils() {
      super();
   }

   /**
    * Static method for marshalling a controller.
    * 
    * @param controller the controller to be marshalled.
    * @param file the file the controller has to be marshalled to.
    * @throws JiBXException if JiBX fails.
    * @throws IOException if an input- or output-exception occurs.
    */
   public static void marshallController(final Controller controller,
                                         final File file)
      throws JiBXException, IOException {
      final Writer writer = new FileWriter(file);
      final IBindingFactory factory =
         BindingDirectory.getFactory(Controller.class);
      final IMarshallingContext context = factory.createMarshallingContext();
      context.setIndent(INDENT);
      context.setOutput(writer);
      context.marshalDocument(controller, ENCODING, null);
   }

   /**
    * Static method for unmarshalling a controller.
    * 
    * @param file the file the controller has to be unmarshalled from.
    * @return the unmarshalled controller.
    * @throws JiBXException if JiBX fails.
    * @throws FileNotFoundException if the input file can't be found.
    */
   public static Controller unmarshallController(final File file)
      throws JiBXException, FileNotFoundException {
      final IBindingFactory factory =
         BindingDirectory.getFactory(Controller.class);
      final IUnmarshallingContext context =
         factory.createUnmarshallingContext();
      final InputStream stream = new FileInputStream(file);
      return (Controller) context.unmarshalDocument(stream, ENCODING);
   }

   /**
    * Static method for marshalling a service.
    * 
    * @param service the service to be marshalled.
    * @param file the file the service has to be marshalled to.
    * @throws JiBXException if JiBX fails.
    * @throws IOException if an input- or output-exception occurs.
    */
   public static void marshallService(final Service service, final File file)
      throws JiBXException, IOException {
      final Writer writer = new FileWriter(file);
      final IBindingFactory factory =
         BindingDirectory.getFactory(Service.class);
      final IMarshallingContext context = factory.createMarshallingContext();
      context.setIndent(INDENT);
      context.setOutput(writer);
      context.marshalDocument(service, ENCODING, null);
   }

   /**
    * Static method for unmarshalling a service.
    * 
    * @param input the input-stream the service has to be unmarshalled from.
    * @return the unmarshalled service.
    * @throws JiBXException if JiBX fails.
    */
   public static Service unmarshallService(final InputStream input)
      throws JiBXException {
      final IBindingFactory factory =
         BindingDirectory.getFactory(Service.class);
      final IUnmarshallingContext context =
         factory.createUnmarshallingContext();
      return (Service) context.unmarshalDocument(input, ENCODING);
   }

   /**
    * Static method for marshalling services (list).
    * 
    * @param services the services to be marshalled.
    * @param file the file the services have to be marshalled to.
    * @throws JiBXException if JiBX fails.
    * @throws IOException if an input- or output-exception occurs.
    */
   public static void marshallServices(final Services services, final File file)
      throws JiBXException, IOException {
      final Writer writer = new FileWriter(file);
      final IBindingFactory factory =
         BindingDirectory.getFactory(Services.class);
      final IMarshallingContext context = factory.createMarshallingContext();
      context.setIndent(INDENT);
      context.setOutput(writer);
      context.marshalDocument(services, ENCODING, null);
   }

   /**
    * Static method for unmarshalling services.
    * 
    * @param input the input-stream services have to be unmarshalled from.
    * @return the unmarshalled services..
    * @throws JiBXException if JiBX fails.
    * @throws FileNotFoundException if the input file can't be found.
    */
   public static Services unmarshallServices(final InputStream input)
      throws JiBXException, FileNotFoundException {
      final IBindingFactory factory =
         BindingDirectory.getFactory(Services.class);
      final IUnmarshallingContext context =
         factory.createUnmarshallingContext();
      return (Services) context.unmarshalDocument(input, ENCODING);
   }

}
