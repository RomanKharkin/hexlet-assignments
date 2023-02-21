package exercise.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.apache.commons.lang3.ArrayUtils;

import static exercise.Data.getUsers;
import static exercise.Data.getNextId;

public class UsersServlet extends HttpServlet {

    private List<Map<String, String>> users = getUsers();

    private String getId(HttpServletRequest request) {
        return request.getParameter("id");
    }

    private String getAction(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null) {
            return "list";
        }
        String[] pathParts = pathInfo.split("/");
        return ArrayUtils.get(pathParts, 1, "");
    }

    private Map<String, String> getUserById(String id) {
        Map<String, String> user = users
            .stream()
            .filter(u -> u.get("id").equals(id))
            .findAny()
            .orElse(null);

        return user;
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
                throws IOException, ServletException {

        String action = getAction(request);

        switch (action) {
            case "list":
                showUsers(request, response);
                break;
            case "new":
                newUser(request, response);
                break;
            case "edit":
                editUser(request, response);
                break;
            case "show":
                showUser(request, response);
                break;
            case "delete":
                deleteUser(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    public void doPost(HttpServletRequest request,
                      HttpServletResponse response)
                throws IOException, ServletException {

        String action = getAction(request);

        switch (action) {
            case "new":
                createUser(request, response);
                break;
            case "edit":
                updateUser(request, response);
                break;
            case "delete":
                destroyUser(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void showUsers(HttpServletRequest request,
                          HttpServletResponse response)
                throws IOException, ServletException {

        request.setAttribute("users", users);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/users.jsp");
        requestDispatcher.forward(request, response);
    }


    private void showUser(HttpServletRequest request,
                         HttpServletResponse response)
                 throws IOException, ServletException {
        String id = getId(request);

        Map<String, String> user = getUserById(id);

        if (user == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        request.setAttribute("user", user);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/show.jsp");
        requestDispatcher.forward(request, response);
    }

    private void newUser(HttpServletRequest request,
                         HttpServletResponse response)
                 throws IOException, ServletException {

        // BEGIN
        Map<String, String> user = new HashMap<>();

        // Устанавливаем атрибуты запроса
        // Пустая компания
        request.setAttribute("user", user);
        // Пустое сообщение об ошибке
        request.setAttribute("error", "");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/new.jsp");
        requestDispatcher.forward(request, response);
        // END
    }

    private void createUser(HttpServletRequest request,
                         HttpServletResponse response)
                 throws IOException, ServletException {

        // BEGIN
        // Получаем из запроса данные компании
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        // Генерируем id
        String id = getNextId();
        // Создаём нового поль-ля заполняем её введенными данными
        Map<String, String> user = new HashMap<>();
        user.put("id", id);
        user.put("firstName", firstName);
        user.put("lastName",lastName);
        user.put("email", email);

        // Проверяем введенные данные
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()) {
            // Если данные не прошли валидацию выполняем редирект с кодом 422 на страницу создания нового пользователя
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/new.jsp");
            // Передаём туда введенные данные компании
            request.setAttribute("user", user);
            // И сообщение об ошибке
            request.setAttribute("error", "Поля  \"Имя\", \"Фамилия\", \"Электронная почта\" не могут быть пустыми");
            response.setStatus(422);
            requestDispatcher.forward(request, response);
            return;
        }
        // Если с данными все в порядке, добавляем компанию в список
        users.add(user);
        // И переходим на страницу компаний
        response.sendRedirect("/users");
        // END
    }

    private void editUser(HttpServletRequest request,
                         HttpServletResponse response)
                 throws IOException, ServletException {

        String id = getId(request);

        Map<String, String> user = getUserById(id);

        if (user == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // BEGIN
        // Устанавливаем атрибуты запроса
        // Пустая компания
        request.setAttribute("user", user);
        // Пустое сообщение об ошибке
        request.setAttribute("error", "");

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/edit.jsp");
        requestDispatcher.forward(request, response);
        // END
    }

    private void updateUser(HttpServletRequest request,
                         HttpServletResponse response)
                 throws IOException, ServletException {

        String id = getId(request);

        Map<String, String> user = getUserById(id);

        if (user == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }


        // BEGIN
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");

        user.put("firstName", firstName);
        user.put("lastName",lastName);
        user.put("email", email);

        // Проверяем введенные данные
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()) {
            // Если данные не прошли валидацию выполняем редирект с кодом 422 на страницу редактирования пользователя
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/edit.jsp");
            // Передаём туда введенные данные пользователя
            request.setAttribute("user", user);
            // И сообщение об ошибке
            request.setAttribute("error", "Поля  \"Имя\", \"Фамилия\", \"Электронная почта\" не могут быть пустыми");
            response.setStatus(422);
            requestDispatcher.forward(request, response);
            return;
        }
        // Если с данными все в порядке, добавляем компанию в список
        users.add(user);
        // И переходим на страницу пользователя
         response.sendRedirect("/users/show?id="+id);
        // END
    }

    private void deleteUser(HttpServletRequest request,
                         HttpServletResponse response)
                 throws IOException, ServletException {

        String id = getId(request);

        Map<String, String> user = getUserById(id);

        if (user == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        request.setAttribute("user", user);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/delete.jsp");
        requestDispatcher.forward(request, response);

    }

    private void destroyUser(HttpServletRequest request,
                         HttpServletResponse response)
                 throws IOException, ServletException {

        String id = getId(request);

        Map<String, String> user = getUserById(id);

        if (user == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        users.remove(user);
        response.sendRedirect("/users");
    }
}
