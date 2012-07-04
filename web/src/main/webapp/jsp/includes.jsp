<%@ page isELIgnored="false" %>
<%@ page session="true" %>
<%@ page import="com.iggroup.oss.restdoclet.web.ServiceConfig" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="contextRoot"  value="<%= request.getContextPath().substring(request.getContextPath().lastIndexOf(\"/\")+1) %>" />
<c:set var="contextPath"  value="<%= request.getContextPath() %>" />
<c:set var="ignore" value="<%= ServiceConfig.setConfigPath(System.getenv(\"RESTDOCLET_DEPLOY\")) %>" />