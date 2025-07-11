package vn.G3.TodoApplication.controller;

import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.ServletException;
import vn.G3.TodoApplication.dto.request.task.TaskCreateRequest;

import vn.G3.TodoApplication.dto.request.task.TaskUpdateRequest;
import vn.G3.TodoApplication.dto.response.ApiResponse;
import vn.G3.TodoApplication.dto.response.task.TaskResponse;

import vn.G3.TodoApplication.service.TaskService;

import java.io.IOException;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class TaskController {
    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    public ApiResponse<List<TaskResponse>> getTask(Authentication authentication) {
        List<TaskResponse> list = this.taskService.getTasks(authentication);
        ApiResponse<List<TaskResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("get tasks success");
        apiResponse.setFiel(list);
        return apiResponse;
    }

    @PostMapping("/tasks")

    public ApiResponse<TaskResponse> createTask(@RequestBody TaskCreateRequest request, Authentication authentication)
            throws ServletException, IOException {
        String username = authentication.getName();
        TaskResponse taskResponse = this.taskService.createTask(request, username);
        ApiResponse<TaskResponse> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("tao task thanh cong");
        apiResponse.setFiel(taskResponse);
        return apiResponse;
    }

    @PatchMapping("/tasks/{id}")
    public ApiResponse<TaskResponse> updateTask(@PathVariable String id, @RequestBody TaskUpdateRequest request) {
        TaskResponse taskResponse = this.taskService.updateTask(request, id);
        ApiResponse<TaskResponse> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("update success");
        apiResponse.setFiel(taskResponse);
        return apiResponse;
    }

}
