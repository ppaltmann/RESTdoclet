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
import static com.iggroup.oss.restdoclet.doclet.util.AnnotationUtils.isAnnotated;
import static com.iggroup.oss.restdoclet.doclet.util.UrlUtils.parseMultiUri;
import static org.apache.commons.lang.StringUtils.trimToNull;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.iggroup.oss.restdoclet.doclet.util.DocTypeUtils;
import com.iggroup.oss.restdoclet.doclet.util.NameValuePair;
import com.iggroup.oss.restdoclet.doclet.util.ParameterNamePredicate;
import com.iggroup.oss.restdoclet.doclet.util.RequestMappingParamsParser;
import com.sun.javadoc.AnnotationValue;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.ParamTag;
import com.sun.javadoc.Parameter;

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
    * Constructs this method with its Java documentation object.
    * 
    * @param methodDoc the method's Java documentation object.
    */
   public Method(final MethodDoc methodDoc) {

      LOG.info("Initialising method: " + methodDoc);
      initName(methodDoc);
      initRequestMethod(methodDoc);
      initJavadoc(methodDoc);
      /* request parameters -- annotated with @RequestParam */
      initRequestParams(methodDoc.parameters(), methodDoc.paramTags());
      /* path parameters -- annotated with @PathVariable */
      initPathParams(methodDoc.parameters(), methodDoc.paramTags());
      /* model parameters -- annotated with @ModelAttribute */
      initModelParams(methodDoc.parameters(), methodDoc.paramTags());
      /* body parameters -- annotated with @RequestBody */
      initBodyParams(methodDoc.parameters(), methodDoc.paramTags());
      /* REST parameters -- 'params' attribute in @RequestMapping */
      initRestParams(methodDoc);
      /* Response parameter -- @ResponseBody tag */
      initResponseParams(methodDoc);
   }

   /**
    * Initialises the name of this method.
    * 
    * @param methodDoc the method's Java documentation object.
    */
   private void initName(final MethodDoc methodDoc) {
      name = methodDoc.name();
      LOG.debug(name);
   } // method

   /**
    * Initialises the HTTP request-method this method is mapped to.
    * 
    * @param methodDoc the method's Java documentation object.
    */
   private void initRequestMethod(final MethodDoc methodDoc) {
      final AnnotationValue value =
         elementValue(methodDoc, RequestMapping.class, "method");

      if (value == null) {
         /* default */
         LOG.debug("No method found.... defaulting to GET");
         requestMethod = RequestMethod.GET.toString();
      } else {
         /*
          * Java 1.6: AnnotationValue.toString() returns qualified classname
          * example:
          * org.springframework.web.bind.annotation.RequestMethod.GET Java
          * 1.5: AnnotationValue.toString() returns simple classname example:
          * GET
          */
         if (value.toString().contains(".")) {
            requestMethod =
               value.toString().substring(
                  value.toString().lastIndexOf(".") + 1);
         } else {
            requestMethod = value.toString();
         }
      }
      LOG.debug(requestMethod);
   }

   /**
    * Initialises the documentation of this method.
    * 
    * @param methodDoc the method's Java documentation object.
    */
   private void initJavadoc(final MethodDoc methodDoc) {
      javadoc = methodDoc.commentText();
      LOG.debug(javadoc);
      if (javadoc.contains("@inheritDoc")) {
         // Look in parent class for javadoc
         ClassDoc containingClass = methodDoc.containingClass();
         ClassDoc superClass = containingClass.superclass().asClassDoc();
         for (MethodDoc md : superClass.methods()) {
            if (md.name().equalsIgnoreCase(methodDoc.name())
               && md.signature().equalsIgnoreCase(methodDoc.signature())) {
               javadoc = md.commentText();
               return;
            }
         }
      }
      if (javadoc.contains("@inheritDoc")) {
         // Look in interfaces for javadoc
         ClassDoc containingClass = methodDoc.containingClass();
         for (ClassDoc cd : containingClass.interfaces()) {
            for (MethodDoc md : cd.methods()) {
               if (md.name().equalsIgnoreCase(methodDoc.name())
                  && md.signature().equalsIgnoreCase(methodDoc.signature())) {
                  javadoc = md.commentText();
                  return;
               }
            }
         }
      }
   }

   /**
    * Initialises the request-parameters of this method.
    * 
    * @param params the method's parameters.
    * @param tags the parameters' Java documentation tags.
    */
   private void initRequestParams(final Parameter[] params,
                                  final ParamTag[] tags) {
      requestParams = new ArrayList<RequestParameter>();
      for (Parameter param : params) {
         if (isAnnotated(param, RequestParam.class)
            || isAnnotated(param, RequestBody.class)) {
            requestParams.add(new RequestParameter(param, tags));
         }
      }
   }

   /**
    * Initialises the path-parameters of this method.
    * 
    * @param params the method's path parameters.
    * @param tags the parameters' Java documentation tags.
    */
   private void initPathParams(final Parameter[] params, final ParamTag[] tags) {
      pathParams = new ArrayList<PathParameter>();
      for (Parameter param : params) {
         if (isAnnotated(param, PathVariable.class)) {
            pathParams.add(new PathParameter(param, tags));
         }
      }
   }

   /**
    * Initialises the model-parameters of this method.
    * 
    * @param params the method's parameters.
    * @param tags the parameters' Java documentation tags.
    */
   private void initModelParams(final Parameter[] params, final ParamTag[] tags) {
      modelParams = new ArrayList<ModelParameter>();
      for (Parameter param : params) {
         if (isAnnotated(param, ModelAttribute.class)) {
            modelParams.add(new ModelParameter(param, tags));
         }
      }
   }

   /**
    * Initialises the request body parameters of this method.
    * 
    * @param params the method's parameters.
    * @param tags the parameters' Java documentation tags.
    */
   private void initBodyParams(final Parameter[] params, final ParamTag[] tags) {
      bodyParams = new ArrayList<BodyParameter>();
      for (Parameter param : params) {
         if (isAnnotated(param, RequestBody.class)) {
            bodyParams.add(new BodyParameter(param, tags));
         }
      }
   }

   /**
    * Initialises the REST-parameters of this method.
    * 
    * @param methodDoc the method's Java documentation object.
    */
   private void initRestParams(final MethodDoc methodDoc) {

      LOG.debug("Getting REST params ...");

      restParams = new ArrayList<RestParameter>();

      for (NameValuePair pair : new RequestMappingParamsParser(elementValue(
         methodDoc, RequestMapping.class, "params")).parse()) {

         final Predicate predicate =
            new ParameterNamePredicate(pair.getName());

         if (!CollectionUtils.exists(requestParams, predicate)) {
            LOG.debug(pair.getName() + " - " + pair.getValue());
            restParams.add(new RestParameter(pair));
         }

      }

      AnnotationValue urlAnnotation =
         elementValue(methodDoc, RequestMapping.class, "value");
      if (urlAnnotation != null) {
         Boolean deprecatedMatch = false;
         String[] methodUris = parseValueAnnotation(urlAnnotation);
         String[] deprecatedURIs = DocTypeUtils.getDeprecatedURIs(methodDoc);

         for (final String uri : methodUris) {
            LOG.debug("uri:" + uri);
            boolean deprecated = false;
            if (deprecatedURIs != null) {
               for (final String deprecatedUri : deprecatedURIs) {
                  LOG.debug("deprecated:" + deprecatedUri);
                  if (StringUtils.equals(deprecatedUri, uri)) {
                     LOG.debug("=DEPRECATED");
                     deprecated = true;
                     deprecatedMatch = true;
                     break;
                  }
               }
            }
            this.uris.add(new Uri(uri, deprecated));
         }

         if (deprecatedURIs != null && !deprecatedMatch) {
            LOG.warn("Deprecated URI tag on method " + methodDoc.name()
               + " does not match any service URIs.");
         }
      }

   }

   /**
    * Parse @RequestMapping value annotation
    * 
    * @param av value of form "xxx" or {"xx","yy",...}
    * @return String array of URIs
    */
   private String[] parseValueAnnotation(final AnnotationValue av) {

      return parseMultiUri(av.toString().trim());

   }

   private void initResponseParams(final MethodDoc methodDoc) {

      responseParams = new ArrayList<ResponseParameter>();
      // Add return type
      if (methodDoc.returnType() != null) {
         responseParams.add(new ResponseParameter(DocTypeUtils
            .getTypeName(methodDoc.returnType()), DocTypeUtils
            .getTypeName(methodDoc.returnType()), DocTypeUtils
            .getReturnDoc(methodDoc)));
      }
      // Add any checked exceptions
      for (ClassDoc exceptionDoc : methodDoc.thrownExceptions()) {
         responseParams.add(new ResponseParameter(DocTypeUtils
            .getTypeName(exceptionDoc),
            DocTypeUtils.getTypeName(exceptionDoc), DocTypeUtils
               .getTypeDoc(exceptionDoc)));
      }
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
