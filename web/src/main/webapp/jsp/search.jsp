<%@ include file="includes.jsp" %>
<html>
<head>
   <c:set var="searchTerm"       value="<%= request.getParameter(\"search\") %>"/>
   <c:set var="matchingservices" value="<%= ServiceConfig.findServices(request.getParameter(\"search\")) %>"/>
</head>

<body>

<table>

   <thead>
      <tr>
         <th>URI</th>
         <th>Actions</th>
      </tr>
   </thead>

   <tbody>

   <c:forEach var="service" items="${matchingservices}">

      <c:set var="match" value="false"/>

      <c:forEach var="uri" items="${service.uris}">

         <c:if test="${fn:indexOf(uri.uri, searchTerm) >= 0}">
            <c:set var="match" value="true"/>
         </c:if>
      </c:forEach>

      <c:if test="${match == 'true'}">

         <tr>
            <td class="uri">

               <c:forEach var="uri" items="${service.uris}">

                  <div class="${uri.deprecated? 'deprecated': 'active'}">

                     <a href="${contextPath}/service?APPLICATION=${service.context}&SERVICE_ID=${service.identifier}">${service.context}${uri.uri}</a>

                     <c:if test="${uri.deprecated}">
                        <span class="deprecatedLabel">(deprecated)</span>
                     </c:if>
                  </div>

               </c:forEach>

            </td>

            <td>
               <table class="methods">

                  <c:forEach var="method" items="${service.methods}">

                     <tr>
                        <td class="requestMethod">${method.requestMethod}</td>
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

</body>
</html>