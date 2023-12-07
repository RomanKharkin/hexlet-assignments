package exercise.controller;

import java.util.List;

import exercise.dto.TaskCreateDTO;
import exercise.dto.TaskDTO;
import exercise.dto.TaskUpdateDTO;
import exercise.mapper.TaskMapper;
import exercise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import exercise.exception.ResourceNotFoundException;
import exercise.repository.TaskRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/tasks")
public class TasksController {
    // BEGIN
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskMapper taskMapper;

    @GetMapping(path = "")
    public List<TaskDTO> index() {
        var tasks = taskRepository.findAll();
        return tasks.stream()
                .map((task) -> taskMapper.map(task))
                .toList();
    }

    @GetMapping(path = "/{id}")
    public TaskDTO show(@PathVariable long id) {
        var task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id " + id + " not found"));
        return taskMapper.map(task);
    }

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDTO create(@RequestBody TaskCreateDTO taskData) {
        var task = taskMapper.map(taskData);
        taskRepository.save(task);
        return taskMapper.map(task);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    TaskDTO update(@RequestBody TaskUpdateDTO taskData, @PathVariable Long id) {
        var task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found"));
        taskMapper.update(taskData, task);
        if (taskData.getAssigneeId() != null) {
            var userOptional = userRepository.findById(taskData.getAssigneeId());
            if(userOptional.isPresent())  {
                task.setAssignee(userOptional.get());
            }
        }
        var updatedTask = taskRepository.save(task);
        return taskMapper.map(updatedTask);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    void delete(@PathVariable long id) {
        var task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found"));
        taskRepository.delete(task);
    }
    // END
}
