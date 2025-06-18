package vn.G3.TodoApplication.controller;

import org.springframework.web.bind.annotation.RestController;

import vn.G3.TodoApplication.dto.request.task.TaskRequest;
import vn.G3.TodoApplication.entity.Task;
import vn.G3.TodoApplication.service.TaskService;

import java.util.List;

import org.springframework.http.ResponseEntity;
// import org.springframework.scheduling.config.Task;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class TaskController {
    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    public ResponseEntity<?> getTask(@RequestBody TaskRequest request) {
        List<Task> list = this.taskService.getTasks(request.getUsername());
        return ResponseEntity.ok().body(list);
    }

}
