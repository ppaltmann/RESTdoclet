<%@ include file="includes.jsp" %>

<c:set var="applications" value="<%= ServiceConfig.getApplicationNames() %>" />

<h1>Applications</h1>
<ul>
   <c:forEach var="application" items="${applications}">
      <li>
         <a href="${contextPath}/services?APPLICATION=${application}">${application}</a>
      </li>
   </c:forEach>
</ul>