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

import static com.iggroup.oss.restdoclet.doclet.RestDocumentationProperties.FILE;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.iggroup.oss.restdoclet.doclet.RestDocumentationProperties;

/**
 * Copies configuration files to the restdoc application
 */
public class ConfigCopier {

   /**
    * The files to be copied to <code>WEB-INF/classes</code> directory.
    */
   public static final String[] RESOURCE_CONFIGS = new String[] {};

   /**
    * The <code>target</code> directories of the module RestDocumentationMojo
    * is running on.
    */
   private transient DirectoryBuilder directories;

   /**
    * Constructs this copier with the <code>target</code> directories of the
    * module RestDocumentationMojo is running on.
    * 
    * @param directories the <code>target</code> directories.
    */
   public ConfigCopier(final DirectoryBuilder directories) {
      this.directories = directories;
   }

   /**
    * Copies resource files
    * 
    * @throws IOException if the files can't be copied.
    */
   public void copyResourceConfigs() throws IOException {
      for (String config : RESOURCE_CONFIGS) {
         IOUtils.copy(this.getClass().getResourceAsStream(config), new File(
            directories.getClassesDirectory(), config));
      }
   }

   /**
    * Facade method for copying copying configuration-files.
    * 
    * @throws IOException if the files can't be copied.
    */
   public void copy() throws IOException {
      copyResourceConfigs();
   }

   /**
    * Creates properties of the restdoc meta data
    * 
    * @param artifactId the artifact-identifier.
    * @param version the version.
    * @param finalName the build-name of the module.
    * @param classifier the classifier used while building documentation.
    * @param scmUrl the scm link to the source code
    * @throws IOException if the properties can't be written.
    */
   public void createProperties(final String artifactId, final String version,
                                final String finalName,
                                final String classifier, final String scmUrl)
      throws IOException {
      final RestDocumentationProperties props =
         new RestDocumentationProperties();
      props.setArtifactId(artifactId);
      props.setVersion(version);
      props.setFinalName(finalName);
      props.setClassifier(classifier);
      props.setScmUrl(scmUrl);
      props.setTimestamp(Calendar.getInstance().getTime().toString());
      props.store(new FileOutputStream(new File(directories
         .getClassesDirectory(), FILE)));
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String toString() {
      return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
         .append("directories", directories).toString();
   }

}
