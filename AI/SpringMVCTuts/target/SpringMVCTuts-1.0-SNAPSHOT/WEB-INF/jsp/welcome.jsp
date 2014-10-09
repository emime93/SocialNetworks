<%-- 
    Document   : welcome
    Created on : Oct 1, 2014, 10:20:31 PM
    Author     : Petricioiu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><spring:message code="welcome.title"/></title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <h2> Today is  <fmt:formatDate value="${today}" pattern="yyyy-mm-dd" /></h2>
        <h3> The handling time for this page is ${handlingTime} milliseconds </h3>
        <h4> Page context locale: ${pageContext.response.locale} </h4>
        <h5><spring:message code="welcome.message" /></h5>
    </body>
</html>
