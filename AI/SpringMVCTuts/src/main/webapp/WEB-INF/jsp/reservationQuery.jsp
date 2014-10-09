<%-- 
    Document   : reservationQuery
    Created on : Oct 1, 2014, 10:20:42 PM
    Author     : Petricioiu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ReservationQuerry</title>
    </head>
    <body>
        <h1>Le Reservation</h1>
        <form method="post">
            <label>Court Name <input type="text" name="courtName" value="${courtName}" /></label>
            <input type="submit" value="Query" />
        </form>
            
            <table border="1">
                <tr>
                    <th>Court Name</th>
                    <th>Date</th>
                    <th>Hour</th>
                    <th>Player</th>
                </tr>
                
                <c:forEach items="${reservations}" var="reservation">
                    <tr>
                        <td>${reservation.courtName}</td>
                        <td>${reservation.date}</td>
                        <td>${reservation.hour}</td>
                        <td>${reservation.player.name}</td>
                </c:forEach>
            </table>
    </body>
</html>
