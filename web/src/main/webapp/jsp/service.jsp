<%@ include file="includes.jsp" %>
<html>
<head>
   <c:set var="service" value="<%= ServiceConfig.getService(request.getParameter(\"APPLICATION\"), request.getParameter(\"SERVICE_ID\")) %>"/>
   <c:set var="props"   value="<%= ServiceConfig.getProperties(request.getParameter(\"APPLICATION\")) %>"/>
</head>

<body>

<div class="projectDetails">
   <h1 class="mainHeading">
      <span class="title">RESTdoclet for:</span>

      <em><a href="services?APPLICATION=${param["APPLICATION"]}">${param["APPLICATION"]}</a></em>

      <c:forEach var="uri" items="${service.uris}">
         <span class="path">
            <em>
               ${uri.uri}
               <c:if test="${uri.deprecated}">
                  <span class="deprecatedLabel">(deprecated)</span>
               </c:if>
            </em>
         </span>
      </c:forEach>
   </h1>

   <h3>
      <span class="version">Version: <em>${props.version}</em></span>
      <span class="timestamp">As of: <em>${props.timestamp}</em></span>
   </h3>

   <h2 class="javaClass">Java Class: <em>${service.controller.type}</em></h2>
</div>

<c:forEach var="method" items="${service.controller.methods}">
   <div class="method">

      <h3 class="httpMethod">${method.requestMethod}</h3>

      <p class="methodJsDoc">${method.javadoc}</p>

      <c:if test="${!empty method.pathParams || !empty method.restParams || !empty method.requestParams}">

         <div class="input">
            <h3>Request input</h3>
            <table class="topLevel methodDetails">
               <thead>
               <tr>
                  <th>Name</th>
                  <th>Description</th>
                  <th>Param Type</th>
               </tr>
               </thead>

               <tbody>

               <c:forEach var="parameter" items="${method.pathParams}">
                  <tr>
                     <td class="name">
                           ${parameter.type} ${parameter.name}
                     </td>
                     <td class="javadoc">
                           ${parameter.javadoc}
                     </td>
                     <td class="path">
                        Path (Mandatory)
                     </td>
                  </tr>
               </c:forEach>

               <c:forEach var="parameter" items="${method.restParams}">
                  <tr>
                     <td class="name">
                           ${parameter.type} ${parameter.name}</td>
                     <td class="javadoc">
                           ${parameter.javadoc}</td>
                     <td class="path">
                        REST
                     </td>
                  </tr>
               </c:forEach>

               <c:forEach var="parameter" items="${method.requestParams}">
                  <tr>
                     <td class="name">
                           ${parameter.type} ${parameter.name}</td>
                     <td class="javadoc">
                           ${parameter.javadoc}</td>
                     <td class="path">
                        Request
                        <c:if test="${parameter.required == 'true'}">
                           (Mandatory)
                        </c:if>
                        <c:if test="${parameter.required == 'false'}">
                           (Optional)
                        </c:if>
                        <c:if test="${parameter.defaultValue != ''}">
                           (Default=${parameter.defaultValue})
                        </c:if>
                     </td>
                  </tr>
               </c:forEach>
               </tbody>
            </table>
         </div>
      </c:if>
      <c:if test="${!empty method.responseParams}">

         <div class="response">
            <h3>Response contents</h3>
            <table class="topLevel methodDetails">
               <thead>
               <tr>
                  <th class="type">Response Type</th>
                  <th class="description">Description</th>
               </tr>
               </thead>
               <tbody>
               <c:forEach var="parameter" items="${method.responseParams}">
                  <tr>
                     <td class="type">${parameter.type}</td>
                     <td class="javadoc">${parameter.javadoc}</td>
                  </tr>
               </c:forEach>
               </tbody>
            </table>
         </div>
      </c:if>
   </div>
</c:forEach>

</body>
</html>