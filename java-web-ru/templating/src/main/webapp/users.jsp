<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- BEGIN -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <meta charset="UTF-8">
        <title>Users</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We"
            crossorigin="anonymous">
    </head>
    <body>
        <c:forEach var="user" items="${users}">
            <tr><a href='/users/show?id=${user.get("id")}'>
            <td>${user.get("id")}.</td>
            <td>${user.get("firstName")}</td>
            <td>${user.get("lastName")}</td>
            <br/></a>
            </tr>
        </c:forEach>
    </body>
</html>
<!-- END -->
