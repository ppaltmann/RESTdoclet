/*
 * #%L
 * restdoc-plugin
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
package com.iggroup.oss.restdoclet.plugin.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;

/**
 * Utility class for input-output operations.
 */
public final class IOUtils {

   private static final Logger LOG = Logger.getLogger(IOUtils.class);

   /**
    * Private constructor to "silence" PMD.
    */
   private IOUtils() {
      super();
   }

   /**
    * Copies an input-stream to an output-stream. This method lacks performance
    * as it copies characters one by one.
    * 
    * @param input the input-stream.
    * @param output the output-stream.
    * @param close <code>true</code> if the output-stream has to be closed
    *           after copying, <code>false</code> otherwise.
    * @return the number of characters copied.
    * @throws IOException if an input-output exception occurs.
    */
   public static long copy(final InputStream input, final OutputStream output,
                           final boolean close) throws IOException {
      long count = 0;
      int read = 0;
      do {
         read = input.read();
         if (read != -1) {
            output.write(read);
            count++;
         }
      } while (read != -1);
      input.close();
      if (close) {
         output.flush();
         output.close();
      }
      return count;
   }

   /**
    * Copies an input-stream to a file.
    * 
    * @param input the input-stream.
    * @param output the output-file.
    * @param close <code>true</code> if the output-stream has to be closed
    *           after copying.
    * @throws IOException if an input-output exception occurs.
    */
   public static void copy(final InputStream input, final File output,
                           final boolean close) throws IOException {
      LOG.debug("Copying " + output.toString());
      copy(input, new FileOutputStream(output, false), close);
   }

   /**
    * Copies and input-stream to an output-file.
    * 
    * @param input the input-stream.
    * @param output the output-file.
    * @throws IOException if an input-output exception occurs.
    */
   public static void copy(final InputStream input, final File output)
      throws IOException {
      copy(input, output, true);
   }

   /**
    * Copies a file to an output-stream. This operation does <i>not</i> close
    * the output-stream.
    * 
    * @param input the input-file.
    * @param output the output-stream.
    * @throws IOException if an input-output exception occurs.
    */
   public static void copy(final File input, final OutputStream output)
      throws IOException {
      copy(new FileInputStream(input), output, false);
   }

   /**
    * Copies a <i>large</i> input-stream to an output-stream.
    * 
    * @param input the <i>large</i> input-stream.
    * @param output the output-stream.
    * @param close <code>true</code> if the output-stream has to be closed
    *           after copying, <code>false</code> otherwise.
    * @throws IOException if an input-output exception occurs.
    */
   public static void copyLarge(final InputStream input,
                                final OutputStream output, final boolean close)
      throws IOException {
      org.apache.commons.io.IOUtils.copyLarge(input, output);
      input.close();
      if (close) {
         output.close();
      }
   }

   /**
    * Copies a <i>large</i> input-file to an output-stream.
    * 
    * @param input the <i>large</i> input-stream.
    * @param output the output-stream.
    * @param close <code>true</code> if the output-stream has to be closed
    *           after copying, <code>false</code> otherwise.
    * @throws IOException if an input-output exception occurs.
    */
   public static void copyLarge(final File input, final File output,
                                final boolean close) throws IOException {
      LOG.debug("Copying " + output.toString());
      copyLarge(new FileInputStream(input), new FileOutputStream(output),
         close);
   }

   /**
    * Copies a <i>large</i> input-file to an output-file.
    * 
    * @param input the <i>large</i> input-file.
    * @param output the output-file.
    * @throws IOException if an input-output exception occurs.
    */
   public static void copyLarge(final File input, final File output)
      throws IOException {
      copyLarge(input, output, true);
   }

   /**
    * Copies a <i>large</i> input-file to an output-stream. This operation does
    * <i>not</i> close the output-stream.
    * 
    * @param input the <i>large</i> input-file.
    * @param output the output-stream.
    * @throws IOException if an input-output exception occurs.
    */
   public static void copyLarge(final File input, final OutputStream output)
      throws IOException {
      copyLarge(new FileInputStream(input), output, false);
   }

}
