<%-- 
    Document   : drive
    Created on : Nov 10, 2014, 1:30:54 PM
    Author     : Petricioiu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello, ${utilizator.username}</h1>
        <a href="<c:url value="/disconnect/google" />">Logout</a>
    </body>
</html>
