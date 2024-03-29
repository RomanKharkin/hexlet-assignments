package exercise.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.RequestDispatcher;

import java.util.HashMap;
import java.util.Map;

import static exercise.App.getUsers;
import exercise.Users;

public class SessionServlet extends HttpServlet {

    private Users users = getUsers();

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
                throws IOException, ServletException {

        if (request.getRequestURI().equals("/login")) {
            showLoginPage(request, response);
            return;
        }

        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @Override
    public void doPost(HttpServletRequest request,
                      HttpServletResponse response)
                throws IOException, ServletException {

        switch (request.getRequestURI()) {
            case "/login" -> login(request, response);
            case "/logout" -> logout(request, response);
            default -> response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void showLoginPage(HttpServletRequest request,
                               HttpServletResponse response)
                 throws IOException, ServletException {

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/login.jsp");
        requestDispatcher.forward(request, response);
    }

    private void login(HttpServletRequest request,
                               HttpServletResponse response)
                 throws IOException, ServletException {

        // BEGIN
        HttpSession session = request.getSession();

        // Получаем пользователя по логину
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Map<String, String> user = users.findByEmail(email);

        final int SC_UNPROCESSABLE_ENTITY = 422;

        // Далее нужно проверить, зарегистрирован ли такой пользователь
        if (user == null || !password.equals("password")) {
            user = new HashMap<>();
            user.put("email", email);
            request.setAttribute("user", user);
            session.setAttribute("flash", "Неверные логин или пароль");
            response.setStatus(SC_UNPROCESSABLE_ENTITY);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/login.jsp");
            requestDispatcher.forward(request, response);
            return;
        }
        // Установка атрибутов сессии
        // Вход в систему сводится к записи данных пользователя в сессию
        session.setAttribute("userId", user.get("id"));
        // Механизм работы флеш-сообщений тоже основан на сессии
        // Устанавливаем в сессию атрибут с текстом сообщения
        // Далее мы сможем получить эти данные в шаблонах
        session.setAttribute("flash", "Вы успешно вошли");

        // Выполняем редирект на главную страницу
        response.sendRedirect("/");
        // END
    }

    private void logout(HttpServletRequest request,
                               HttpServletResponse response)
                 throws IOException {

        // BEGIN
        HttpSession session = request.getSession();
        session.setAttribute("flash", "Вы успешно вышли");
        // Удаляем атрибут из сессии
        session.removeAttribute("userId");
        response.sendRedirect("/");
        // END
    }
}
