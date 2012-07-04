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

import static org.apache.commons.lang.StringUtils.trimToNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * This class manages properties of the web-application documentation was built
 * on. The properties are packaged along with the web-application
 * <i>generated</i> by documentation.
 */
public class RestDocumentationProperties {

   /**
    * Log4j logger for this class.
    */
   public static final Logger LOGGER = Logger
      .getLogger(RestDocumentationProperties.class);

   /**
    * The name of the file containing the properties.
    */
   public static final String FILE = "rd.properties";

   /**
    * The property containing the artifact-identifier of the web-application
    * the documentation was built on.
    */
   public static final String ARTIFACT_ID = "project.artifactId";

   /**
    * The property containing the version of the web-application documentation
    * was built on.
    */
   public static final String VERSION = "project.version";

   /**
    * The property containing the build-name of the web-application
    * documentation was built on.
    */
   public static final String FINAL_NAME = "project.build.finalName";

   /**
    * The property containing the classifier used while building the
    * web-application <i>generated</i> by documentation.
    */
   public static final String CLASSIFIER = "project.rd.classifier";

   /**
    * The property containing the project scm url of the web-application
    * documentation was built on.
    */
   public static final String SCM_URL = "project.scm.url";

   /**
    * The property containing the project timestamp of the web-application
    * documentation was built on.
    */
   public static final String TIMESTAMP = "project.timestamp";

   /**
    * The artifact-identifier of the web-application documentation was built
    * on.
    */
   private String artifactId;

   /**
    * The version of the web-application documentation was built on.
    */
   private String version;

   /**
    * The build-name of the web-application documentation was built on.
    */
   private String finalName;

   /**
    * The classifier used while building the web-application <i>generated</i>
    * by documentation.
    */
   private String classifier;

   /**
    * The SCM URL of the web-application documentation was built on.
    */
   private String scmUrl;

   /**
    * The timestamp of the web-application documentation was built on.
    */
   private String timestamp;

   /**
    * No-argument constructor for this class to be used as a bean.
    */
   public RestDocumentationProperties() {
      super();
   }

   /**
    * Constructs this class by loading the properties from an input-stream.
    * 
    * @param inStream the input-stream where the properties have to be read.
    * @throws IOException if the input-stream can't be read.
    */
   public RestDocumentationProperties(final InputStream inStream)
      throws IOException {

      final Properties props = new Properties();
      props.load(inStream);
      artifactId = props.getProperty(ARTIFACT_ID);
      LOGGER.debug(FILE + ": " + ARTIFACT_ID + '=' + artifactId);
      version = props.getProperty(VERSION);
      LOGGER.debug(FILE + ": " + VERSION + '=' + version);
      finalName = props.getProperty(FINAL_NAME);
      LOGGER.debug(FILE + ": " + FINAL_NAME + '=' + finalName);
      classifier = props.getProperty(CLASSIFIER);
      LOGGER.debug(FILE + ": " + CLASSIFIER + '=' + classifier);
      scmUrl = props.getProperty(SCM_URL);
      LOGGER.debug(FILE + ": " + SCM_URL + '=' + scmUrl);
      timestamp = props.getProperty(TIMESTAMP);
      LOGGER.debug(FILE + ": " + TIMESTAMP + '=' + timestamp);
   }

   /**
    * Gets the artifact-identifier of the web-application documentation was
    * built on.
    * 
    * @return the artifact-identifier.
    */
   public String getArtifactId() {
      return artifactId;
   }

   /**
    * Sets the artifact-identifier of the web-application documentation was
    * built on.
    * 
    * @param artifactId the artifact-identifier.
    */
   public void setArtifactId(final String artifactId) {
      this.artifactId = trimToNull(artifactId);
   }

   /**
    * Gets the version of the web-application documentation was built on.
    * 
    * @return the version.
    */
   public String getVersion() {
      return version;
   }

   /**
    * Sets the version of the web-application documentation was built on.
    * 
    * @param version the version.
    */
   public void setVersion(final String version) {
      this.version = trimToNull(version);
   }

   /**
    * Gets the build-name of the web-application documentation was built on.
    * 
    * @return the build-name.
    */
   public String getFinalName() {
      return finalName;
   }

   /**
    * Sets the build-name of the web-application documentation was built on.
    * 
    * @param finalName the build-name.
    */
   public void setFinalName(final String finalName) {
      this.finalName = trimToNull(finalName);
   }

   /**
    * Gets the classifier used while building the web-application
    * <i>generated</i> by documentation.
    * 
    * @return the classifier used.
    */
   public String getClassifier() {
      return classifier;
   }

   /**
    * Sets the classifier used while building the web-application
    * <i>generated</i> by documentation.
    * 
    * @param classifier the classifier used.
    */
   public void setClassifier(final String classifier) {
      this.classifier = classifier;
   }

   /**
    * Gets the SCM URL used while building the web-application <i>generated</i>
    * by documentation.
    * 
    * @return SCM URL
    */
   public String getScmUrl() {
      return scmUrl;
   }

   /**
    * Sets the SCM URL used while building the web-application <i>generated</i>
    * by documentation.
    * 
    * @param scmUrl
    */
   public void setScmUrl(String scmUrl) {
      this.scmUrl = scmUrl;
   }

   /**
    * Gets the build timestamp used while building the web-application
    * <i>generated</i> by documentation.
    * 
    * @return build timestamp
    */
   public String getTimestamp() {
      return timestamp;
   }

   /**
    * Sets the build timestamp used while building the web-application
    * <i>generated</i> by documentation.
    * 
    * @param timestamp
    */
   public void setTimestamp(String timestamp) {
      this.timestamp = timestamp;
   }

   /**
    * Writes the properties in this object to an output-stream.
    * 
    * @param out the output-stream the properties have to be written to.
    * @throws IOException if the output-stream can't be written to.
    */
   public void store(final OutputStream out) throws IOException {
      final Properties props = new Properties();
      props.put(ARTIFACT_ID, artifactId);
      props.put(VERSION, version);
      props.put(FINAL_NAME, finalName);
      props.put(CLASSIFIER, classifier);
      props.put(SCM_URL, scmUrl);
      props.put(TIMESTAMP, timestamp);
      props.store(out, "RESTDocumentation properies");
   }

}
