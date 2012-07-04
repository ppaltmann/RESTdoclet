<%@ include file="includes.jsp" %>
<html>
<head>
   <c:set var="services" value="<%= ServiceConfig.getServices(request.getParameter(\"APPLICATION\")) %>"/>
   <c:set var="props"    value="<%= ServiceConfig.getProperties(request.getParameter(\"APPLICATION\")) %>"/>
</head>

<body>


<div class="projectDetails">

   <h1 class="mainHeading">
      <span class="title">RESTdoclet for:</span>
      <span class="application">
         <em><span class="application">${param["APPLICATION"]}</span></em>
      </span>
   </h1>

   <h3>
      <span class="version">Version: <em>${props.version}</em></span>
      <span class="timestamp">As of: <em>${props.timestamp}</em></span>
   </h3>
</div>

<div class="services">
   <div class="sourceControlLocation">
      Under version control at <a href="${props.scmUrl}">${props.scmUrl}</a>
   </div>

   <div class="showDeprecatedWrapper">
      <input type="checkbox" name="showDeprecated"
             value="true" id="showDeprecated" />
      <label for="showDeprecated">Show Deprecated</label>
   </div>

   <table class="topLevel">
      <thead>
      <tr>
         <th>URI</th>
         <th>Actions</th>
      </tr>
      </thead>
      <tbody>

      <c:forEach var="service" items="${services}">

         <c:set var="showRow" value="false"/>
         <c:forEach var="uri" items="${service.uris}">
            <c:if test="${!uri.deprecated}">
               <c:set var="showRow" value="true"/>
            </c:if>
         </c:forEach>

         <c:if test="${showRow == 'true'}">
            <tr>

               <td class="uri">
                  <c:forEach var="uri" items="${service.uris}">


                     <div class="${uri.deprecated? 'deprecated': 'active'}">

                        <a href="service?APPLICATION=${param["APPLICATION"]}&SERVICE_ID=${service.identifier}">${uri.uri}</a>

                        <c:if test="${uri.deprecated}">
                           <span class="deprecatedLabel">(deprecated)</span>
                        </c:if>
                     </div>

                  </c:forEach></td>
               <td>
                  <table class="methods">
                     <c:forEach var="method" items="${service.methods}">
                        <tr>
                           <td class="requestMethod">${method.requestMethod}</td class="javadoc">
                           <td class="javadoc">${method.javadoc}</td>
                        </tr>
                     </c:forEach>
                  </table>
               </td>

            </tr>
         </c:if>

      </c:forEach>

      </tbody>
   </table>

</div>

</body>
</html>