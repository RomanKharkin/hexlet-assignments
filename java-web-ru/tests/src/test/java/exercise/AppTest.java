package exercise;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import io.javalin.Javalin;
import io.ebean.DB;

import exercise.domain.User;
import exercise.domain.query.QUser;
import io.ebean.Database;

class AppTest {

    private static Javalin app;
    private static String baseUrl;

    // BEGIN
    @BeforeAll
    public static void beforeAll() {
        // Получаем инстанс приложения
        app = App.getApp();
        // Запускаем приложение на рандомном порту
        app.start(0);
        // Получаем порт, на которм запустилось приложение
        int port = app.port();
        // Формируем базовый URL
        baseUrl = "http://localhost:" + port;
    }

    @AfterAll
    public static void afterAll() {
        // Останавливаем приложение
        app.stop();
    }
    // END

    // Между тестами база данных очищается
    // Благодаря этому тесты не влияют друг на друга
    @BeforeEach
    void beforeEach() {
        Database db = DB.getDefault();
        db.truncate("user");
        User existingUser = new User("Wendell", "Legros", "a@a.com", "123456");
        existingUser.save();
    }

    @Test
    void testUsers() {

        // Выполняем GET запрос на адрес http://localhost:port/users
        HttpResponse<String> response = Unirest
            .get(baseUrl + "/users")
            .asString();
        // Получаем тело ответа
        String content = response.getBody();

        // Проверяем код ответа
        assertThat(response.getStatus()).isEqualTo(200);
        // Проверяем, что страница содержит определенный текст
        assertThat(response.getBody()).contains("Wendell Legros");
    }

    @Test
    void testNewUser() {

        HttpResponse<String> response = Unirest
                .get(baseUrl + "/users/new")
                .asString();

        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    void testNewUserValid() {
        // BEGIN
        // Выполняем POST запрос при помощи агента Unirest
        HttpResponse<String> responsePost = Unirest
                // POST запрос на URL
                .post(baseUrl + "/users")
                // Устанавливаем значения полей
                .field("firstName", "Roma")
                .field("lastName", "Romanov")
                .field("email", "rvh@mail.ru")
                .field("password", "1234")
                // Выполняем запрос и получаем тело ответ с телом в виде строки
                .asString();

        // Проверяем статус ответа
        assertThat(responsePost.getStatus()).isEqualTo(302);

        // Проверяем, что компания добавлена в БД
        User actualUser = new QUser()
                .lastName.equalTo("Romanov")
                .findOne();

        assertThat(actualUser).isNotNull();

        // И что её данные соответствуют переданным
        assertThat(actualUser.getFirstName()).isEqualTo("Roma");
        assertThat(actualUser.getLastName()).isEqualTo("Romanov");
        assertThat(actualUser.getEmail()).isEqualTo("rvh@mail.ru");
    }

    @Test
    void testNewUserNotValid() {
        HttpResponse<String> responsePost = Unirest
                // POST запрос на URL
                .post(baseUrl + "/users")
                // Устанавливаем значения полей
                .field("firstName", "")
                .field("lastName", "")
                .field("email", "rvhmail.ru")
                .field("password", "123")
                // Выполняем запрос и получаем тело ответ с телом в виде строки
                .asString();
        // Проверяем статус ответа
        assertThat(responsePost.getStatus()).isEqualTo(422);

        User actualUser = new QUser()
                .email.equalTo("rvhmail.ru")
                .findOne();
        // Можно проверить, что такой компании нет в БД
        assertThat(actualUser).isNull();

        // Так можно получить содержимое тела ответа
        String content = responsePost.getBody();
        // И проверить, что оно содержит определённую строку
        assertThat(content).contains("rvhmail.ru");
        assertThat(content).contains("123");
        assertThat(content).contains("Имя не должно быть пустым");
        assertThat(content).contains("Фамилия не должна быть пустой");
        assertThat(content).contains("Должно быть валидным email");
        assertThat(content).contains("Пароль должен содержать не менее 4 символов");

    }
    // END
}
