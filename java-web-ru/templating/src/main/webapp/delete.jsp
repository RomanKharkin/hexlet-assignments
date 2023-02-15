<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- BEGIN -->
<html lang="ru">
    <head>
        <meta charset="UTF-8">
        <title>Company</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We"
            crossorigin="anonymous">
    </head>
    <body>
        <pre>Are you sure you want to delete this user?</pre>
        <form action="/users/delete?id=${user.get("id")}" method="post">
            <button type="submit" class="btn btn-danger">Delete</button>
        </form>
        Vernie
    </body>
</html>

<!-- END -->
