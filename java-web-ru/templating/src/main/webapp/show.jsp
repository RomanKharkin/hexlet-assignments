<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- BEGIN -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <meta charset="UTF-8">
        <title>User</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We"
            crossorigin="anonymous">
    </head>

    <body>
            <tr>
            <td>id: <strong> ${user.get("id")} </strong>,</td>
            <td>firstName: <strong> ${user.get("firstName")} </strong>,</td>
            <td>lastName: <strong> ${user.get("lastName")} </strong>,</td>
            <td>email: <strong> ${user.get("email")} </strong></td>
            <td border = "1"><a href='/users/delete?id=${user.get("id")}'>
                <button type="submit" class="btn btn-danger" > Delete </button> </a></td>
            </tr>
    </body>
</html>
<!-- END -->
