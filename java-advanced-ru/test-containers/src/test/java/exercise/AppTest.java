package exercise;

import com.fasterxml.jackson.databind.ObjectMapper;
import exercise.model.Person;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import org.springframework.http.MediaType;

import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.junit.jupiter.Container;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc

// BEGIN
@Testcontainers
@Transactional
// END
public class AppTest {

    @Autowired
    private MockMvc mockMvc;

    // BEGIN
    @Container
    // Создаём контейнер с СУБД PostgreSQL
    // В конструктор передаём имя образа, который будет скачан с Dockerhub
    // Если не указать версию, будет скачана последняя версия образа
    private static PostgreSQLContainer<?> database = new PostgreSQLContainer<>("postgres")
            // Создаём базу данных с указанным именем
            .withDatabaseName("dbname")
            // Указываем имя пользователя и пароль
            .withUsername("sa")
            .withPassword("sa")
            // Скрипт, который будет выполнен при запуске контейнера и наполнит базу тестовыми данными
            .withInitScript("init.sql");

    @DynamicPropertySource
    public static void properties(DynamicPropertyRegistry registry) {
        // Устанавливаем URL базы данных
        registry.add("spring.datasource.url", database::getJdbcUrl);
        // Имя пользователя и пароль для подключения
        registry.add("spring.datasource.username", database::getUsername);
        registry.add("spring.datasource.password", database::getPassword);
        // Эти значения приложение будет использовать при подключении к базе данных
    }
    // END

    @Test
    void testCreatePerson() throws Exception {
        MockHttpServletResponse responsePost = mockMvc
            .perform(
                post("/people")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"firstName\": \"Jackson\", \"lastName\": \"Bind\"}")
            )
            .andReturn()
            .getResponse();

        assertThat(responsePost.getStatus()).isEqualTo(201);

        MockHttpServletResponse response = mockMvc
            .perform(get("/people"))
            .andReturn()
            .getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
        assertThat(response.getContentAsString()).contains("Jackson", "Bind");
    }
    @Test
    void testGetPeople() throws Exception {
        // Создаем несколько пользователей для проверки
        mockMvc.perform(
                post("/people")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\": \"John\", \"lastName\": \"Doe\"}")
        );

        mockMvc.perform(
                post("/people")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\": \"Alice\", \"lastName\": \"Smith\"}")
        );

        // Получаем список всех пользователей
        MockHttpServletResponse response = mockMvc
                .perform(get("/people"))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
        assertThat(response.getContentAsString()).contains("John", "Doe", "Alice", "Smith");
    }

    @Test
    void testGetPerson() throws Exception {
        // Создаем пользователя для проверки
        MockHttpServletResponse responsePost = mockMvc
                .perform(
                        post("/people")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"firstName\": \"John\", \"lastName\": \"Doe\"}")
                )
                .andReturn()
                .getResponse();

        // Получаем id нового пользователя из ответа
        String responseBody = responsePost.getContentAsString();
        Person person = new ObjectMapper().readValue(responseBody, Person.class);
        Long id = person.getId();

        // Получаем созданного пользователя
        MockHttpServletResponse getResponse = mockMvc
                .perform(get("/people/" + id))
                .andReturn()
                .getResponse();

        assertThat(getResponse.getStatus()).isEqualTo(200);
        assertThat(getResponse.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
        assertThat(getResponse.getContentAsString()).contains("John", "Doe");
    }

    @Test
    void testUpdatePerson() throws Exception {
        // Создаем пользователя для обновления
        mockMvc.perform(
                post("/people")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\": \"Jane\", \"lastName\": \"Doe\"}")
        );

        // Обновляем пользователя
        MockHttpServletResponse response = mockMvc
                .perform(
                        patch("/people/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"firstName\": \"Jane Updated\", \"lastName\": \"Doe Updated\"}")
                )
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(200);

        // Проверяем, что пользователь обновлен успешно
        MockHttpServletResponse getResponse = mockMvc
                .perform(get("/people/1"))
                .andReturn()
                .getResponse();

        assertThat(getResponse.getStatus()).isEqualTo(200);
        assertThat(getResponse.getContentAsString()).contains("Jane Updated", "Doe Updated");
    }

    @Test
    void testDeletePerson() throws Exception {
        // Создаем пользователя для удаления
        mockMvc.perform(
                post("/people")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\": \"Alice\", \"lastName\": \"Smith\"}")
        );

        // Удаляем пользователя
        MockHttpServletResponse response = mockMvc
                .perform(delete("/people/1"))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(200);

        assertThat(response.getContentAsString()).doesNotContain("Alice", "Smith");
    }
}
