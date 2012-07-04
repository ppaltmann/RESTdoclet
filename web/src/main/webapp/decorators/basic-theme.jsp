<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN"
   "http://www.w3.org/TR/html4/frameset.dtd">
<html>
<head>
   <title>RESTdoclet Catalogue</title>

   <c:set var="contextPath"  value="<%= request.getContextPath() %>" />

   <link rel="stylesheet" type="text/css" href="${contextPath}/css/reset.css" />
   <link rel="stylesheet" type="text/css" href="${contextPath}/css/styles.css"/>

   <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
   <script src="${contextPath}/js/scripts.js"></script>

</head>
<body>

   <div class="header">
      <div id="searchBox">
         <form action="search" method="get">
            <input type="text" name="search" />
            <input type="submit" value="Search" />
         </form>
      </div>
      <h1><abbr>REST</abbr>doclet Catalogue</h1>
   </div>

   <div class="applications">
      <jsp:include page="/applications" />
   </div>

   <div class="main">
      <decorator:body />
   </div>

</body>
</html>