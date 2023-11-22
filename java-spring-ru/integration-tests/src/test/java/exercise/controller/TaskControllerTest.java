package exercise.controller;

import org.junit.jupiter.api.Test;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

import org.instancio.Instancio;
import org.instancio.Select;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.http.MediaType;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.datafaker.Faker;
import exercise.repository.TaskRepository;
import exercise.model.Task;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@SpringBootTest
@AutoConfigureMockMvc
// BEGIN
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Faker faker;

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private ObjectMapper om;

    @Test
    public void testUpdate() throws Exception {
        var task = Instancio.of(Task.class)
                .supply(Select.field(Task::getDescription), () -> faker.lorem().paragraph())
                .supply(Select.field(Task::getTitle), () -> faker.lorem().word())
                .ignore(Select.field(Task::getCreatedAt))
                .ignore(Select.field(Task::getId))
                .create();
        taskRepository.save(task);

        var data = new HashMap<>();
        data.put("description", "Mike shrike pike pups");


        var request = put("/tasks/" + task.getId())
                .contentType(MediaType.APPLICATION_JSON)
                // ObjectMapper конвертирует Map в JSON
                .content(om.writeValueAsString(data));

        mockMvc.perform(request)
                .andExpect(status().isOk());

        task = taskRepository.findById(task.getId()).get();
        assertThat(task.getDescription()).isEqualTo(("Mike shrike pike pups"));
    }

    @Test
    public void testCreate() throws Exception {
        var request = post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                // ObjectMapper конвертирует Map в JSON
                .content("{ \"title\":\"Helllo\",\"description\":\"Descripttion dddescriiption\"}");

        mockMvc.perform(request)
                .andExpect(status().isCreated());

        var task = taskRepository.findByTitle(
                "Helllo").get();
        assertThat(task.getDescription()).isEqualTo(("Descripttion dddescriiption"));
    }

    @Test
    public void testDelete() throws Exception {
        var task = Instancio.of(Task.class)
                .supply(Select.field(Task::getDescription), () -> faker.lorem().paragraph())
                .supply(Select.field(Task::getTitle), () -> faker.lorem().word())
                .ignore(Select.field(Task::getCreatedAt))
                .ignore(Select.field(Task::getId))
                .create();
        taskRepository.save(task);

        var request = delete("/tasks/" + task.getId());

        mockMvc.perform(request)
                .andExpect(status().isOk());

        var isTaskNotExist = taskRepository.findById(task.getId()).isEmpty();

        assertThat(isTaskNotExist).isTrue();
    }

    @Test
    public void testShow() throws Exception {
        var title = faker.lorem().word();
        var description = faker.lorem().paragraph();
        var task = Instancio.of(Task.class)
                .supply(Select.field(Task::getDescription), () -> description)
                .supply(Select.field(Task::getTitle), () -> title)
                .ignore(Select.field(Task::getCreatedAt))
                .ignore(Select.field(Task::getId))
                .create();
        taskRepository.save(task);
        var request = get("/tasks/" + task.getId());

        var expectedTask = mockMvc.perform(request)
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        assertThatJson(expectedTask).and(
                a -> a.node("title").isEqualTo(title),
                a -> a.node("description").isEqualTo(description));
    }
}
// END

