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

import static org.apache.commons.lang.StringUtils.trimToNull;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.log4j.Logger;

/**
 * This class creates documentation for a method annotated with
 * <code>@RequestMapping</code>.
 */
public class Method implements Comparable<Method> {

   private static final Logger LOG = Logger.getLogger(Method.class);

   /**
    * The name of this method.
    */
   private String name;

   /**
    * The HTTP request-method this method is mapped to.
    */
   private String requestMethod;

   /**
    * The documentation of this method.
    */
   private String javadoc;

   /**
    * The URI of this method.
    */
   private Collection<Uri> uris = new ArrayList<Uri>();

   /**
    * The response-param of this method.
    * 
    * @TODO make this a single param (emulating applicationParams for now)
    * @see ApplicationParameter.
    */
   private Collection<ResponseParameter> responseParams;

   /**
    * The REST-parameters of this method.
    * 
    * @see RestParameter.
    */
   private Collection<RestParameter> restParams;

   /**
    * The request-parameters of this method.
    * 
    * @see RequestParameter.
    */
   private Collection<RequestParameter> requestParams;

   /**
    * The path-parameters of this method.
    * 
    * @see PathParameter.
    */
   private Collection<PathParameter> pathParams;

   /**
    * The model-parameters of this method.
    * 
    * @see ModelParameter.
    */
   private Collection<ModelParameter> modelParams;

   /**
    * The request body-parameters of this method.
    * 
    * @see BodyParameter.
    */
   private Collection<BodyParameter> bodyParams;

   /**
    * No-argument constructor for this class to be used as a bean or by JiBX.
    */
   public Method() {
      super();
   }


   /**
    * Gets the name of this method.
    * 
    * @return the method's name.
    */
   public String getName() {
      return name;
   }

   /**
    * Sets the name of this method.
    * 
    * @param name the method's name.
    */
   public void setName(final String name) {
      this.name = trimToNull(name);
   }

   /**
    * Gets the HTTP request-method this method is mapped to.
    * 
    * @return the method's HTTP request-method.
    */
   public String getRequestMethod() {
      return requestMethod;
   }

   /**
    * Sets the HTTP request-method this method is mapped to.
    * 
    * @param requestMethod the method's HTTP request-method.
    */
   public void setRequestMethod(final String requestMethod) {
      this.requestMethod = trimToNull(requestMethod);
   }

   /**
    * Gets the documentation of this method.
    * 
    * @return the method's documentation.
    */
   public String getJavadoc() {
      return javadoc;
   }

   /**
    * Sets the documentation of this method.
    * 
    * @param javadoc the method's documentation.
    */
   public void setJavadoc(final String javadoc) {
      this.javadoc = trimToNull(javadoc);
   }

   /**
    * Gets the body-parameter of this method.
    * 
    * @return the method's response-parameter.
    */
   public Collection<ResponseParameter> getResponseParams() {
      return responseParams;
   }

   /**
    * Sets the response-parameter of this method.
    * 
    * @param responseParams the method's response-parameters.
    */
   public void setResponseParams(final Collection<ResponseParameter> responseParams) {
      this.responseParams = responseParams;
   }

   /**
    * Gets the REST-parameters of this method.
    * 
    * @return the method's REST-parameters.
    */
   public Collection<RestParameter> getRestParams() {
      return restParams;
   }

   /**
    * Sets the REST-parameters of this method.
    * 
    * @param restParams the method's REST-parameters.
    */
   public void setRestParams(final Collection<RestParameter> restParams) {
      this.restParams = restParams;
   }

   /**
    * Gets the request-parameters of this method.
    * 
    * @return the method's request-parameters.
    */
   public Collection<RequestParameter> getRequestParams() {
      return requestParams;
   }

   /**
    * Sets the path-parameters of this method.
    * 
    * @param pathParams the method's request-parameters.
    */
   public void setPathParams(final Collection<PathParameter> pathParams) {
      this.pathParams = pathParams;
   }

   /**
    * Gets the path-parameters of this method.
    * 
    * @return the method's path-parameters.
    */
   public Collection<PathParameter> getPathParams() {
      return pathParams;
   }

   /**
    * Sets the request-parameters of this method.
    * 
    * @param requestParams the method's request-parameters.
    */
   public void setRequestParams(final Collection<RequestParameter> requestParams) {
      this.requestParams = requestParams;
   }

   /**
    * Gets the model-parameters of this method.
    * 
    * @return the method's model-parameters.
    */
   public Collection<ModelParameter> getModelParams() {
      return modelParams;
   }

   /**
    * Sets the model-parameters of this method.
    * 
    * @param modelParams the method's model-parameters.
    */
   public void setModelParams(final Collection<ModelParameter> modelParams) {
      this.modelParams = modelParams;
   }

   /**
    * Gets the body-parameters of this method.
    * 
    * @return the method's body-parameters.
    */
   public Collection<BodyParameter> getBodyParams() {
      return bodyParams;
   }

   /**
    * Sets the body-parameters of this method.
    * 
    * @param bodyParams the method's body-parameters.
    */
   public void setBodyParams(final Collection<BodyParameter> bodyParams) {
      this.bodyParams = bodyParams;
   }

   /**
    * Return the URIs
    * 
    * @return uris
    */
   public Collection<Uri> getUris() {
      return uris;
   }

   /**
    * Set the method URI
    * 
    * @param uris method uris
    */
   public void setUris(final Collection<Uri> uris) {

      this.uris = uris;
   }

   /**
    * Asserts that this object is valid
    */
   public void assertValid() {

      assert !name.isEmpty() : "Missing name " + this.toString();
      assert !requestMethod.isEmpty() : "Missing request method "
      + this.toString();
      if (javadoc.isEmpty()) {
         LOG.warn("Missing request method javadoc " + this.toString());
      }
      assert uris != null : "Missing uris " + this.toString();
      for (Uri uri : uris) {
         uri.assertValid();
      }

      for (ResponseParameter p : responseParams) {
         p.assertValid();
      }

      for (RestParameter p : restParams) {
         p.assertValid();
      }

      for (RequestParameter p : requestParams) {
         p.assertValid();
      }

      for (PathParameter p : pathParams) {
         p.assertValid();
      }

      for (ModelParameter p : modelParams) {
         p.assertValid();
      }

      for (BodyParameter p : bodyParams) {
         p.assertValid();
      }

   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean equals(final Object obj) {
      boolean result;
      if (obj instanceof Method) {
         final Method method = (Method) obj;
         result =
            new EqualsBuilder().append(name, method.name)
            .append(requestMethod, method.requestMethod).isEquals();
      } else {
         result = false;
      }
      return result;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public int hashCode() {
      return new HashCodeBuilder().append(name).append(requestMethod)
         .toHashCode();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public int compareTo(final Method method) {
      int result;
      if (method == null) {
         result = 1;
      } else {
         result =
            new CompareToBuilder().append(name, method.name)
            .append(requestMethod, method.requestMethod).toComparison();
      }
      return result;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String toString() {
      return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
      .append("name", name).append("requestMethod", requestMethod)
      .append("uris", uris).append("restParams", restParams)
      .append("requestParams", requestParams)
      .append("pathParams", pathParams).append("modelParams", modelParams)
      .append("javadoc", javadoc).toString();
   }

}
