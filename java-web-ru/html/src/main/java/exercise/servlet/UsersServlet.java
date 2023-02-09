package exercise.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.ArrayUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

public class UsersServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {

        String pathInfo = request.getPathInfo();

        if (pathInfo == null) {
            showUsers(request, response);
            return;
        }

        String[] pathParts = pathInfo.split("/");
        String id = ArrayUtils.get(pathParts, 1, "");

        showUser(request, response, id);
    }

    private List<User> getUsers() throws JsonProcessingException, IOException {
        // BEGIN
        InputStream stream =
                this.getClass().getClassLoader().getResource("users.json").openStream();

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(stream, new TypeReference<List<User>>() {
        });
        // END
    }

    private void showUsers(HttpServletRequest request,
                           HttpServletResponse response)
            throws IOException {

        // BEGIN
        StringBuilder body = new StringBuilder();
        body.append("""
                <!DOCTYPE html>
                <html lang=\"ru\">
                    <head>
                        <meta charset=\"UTF-8\">
                        <title>Example application | Users</title>
                        <link rel=\"stylesheet\" href=\"mysite.css\">
                    </head>
                    <body>
                    <table border="1" cellpadding="4" >
                """);
        for (User user : getUsers()) {
            body.append("  <tr>\n"
                                + "    <td>" + user.getId() + "</td>\n"
                                + "    <td>\n"
                                + "      <a href=\"/users/" + user.getId() + "\\\">" + user.getFirstName() + " "
                                + user.getLastName() + "</a>\n"
                                + "    </td>\n"
                                + "  </tr>");
        }

        body.append("""
                    </table>
                    </body>
                </html>
                """);

        response.setContentType("text/html;charset=UTF-8");

         response.setStatus(HttpServletResponse.SC_OK);

        var out = response.getWriter();
        out.println(body.toString());

        // END
    }

    private void showUser(HttpServletRequest request,
                          HttpServletResponse response,
                          String id)
            throws IOException {

        // BEGIN
        var users = getUsers();
        var optionalUser = users.stream().filter((User currentUser) -> id.equals(currentUser.getId())).findFirst();

        if(optionalUser.isPresent()) {

            StringBuilder body = new StringBuilder();
            body.append("""
                    <!DOCTYPE html>
                    <html lang=\"ru\">
                        <head>
                            <meta charset=\"UTF-8\">
                            <title>Example application | Users</title>
                            <link rel=\"stylesheet\" href=\"mysite.css\">
                        </head>
                        <body>
                        """
                    +
                        optionalUser.get().toString()

                         +
                                """
                        </body>
                    </html>
                    """);

            response.setContentType("text/html;charset=UTF-8");

            response.setStatus(HttpServletResponse.SC_OK);

            var out = response.getWriter();
            out.println(body.toString());
            return;
        }

        response.setContentType("text/plain;charset=UTF-8");
        var out = response.getWriter();
        out.println("Not Found");
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        // END
    }

    private static class User {
        private String firstName;
        private String lastName;
        private String id;
        private String email;


        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getId() {
            return id;
        }

        public String getEmail() {
            return email;
        }


        @Override
        public String toString() {
            return "<hr/> User{" +
                           "firstName = '" + firstName + '\'' +
                           ", lastName = '" + lastName + '\'' +
                           ", id = '" + id + '\'' +
                           ", email = '" + email + '\'' +
                           '}' + "<hr/>";
        }
    }
}
