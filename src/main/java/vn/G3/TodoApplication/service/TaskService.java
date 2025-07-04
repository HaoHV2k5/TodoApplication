package vn.G3.TodoApplication.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import vn.G3.TodoApplication.dto.request.task.TaskCreateRequest;
import vn.G3.TodoApplication.dto.request.task.TaskUpdateRequest;
import vn.G3.TodoApplication.dto.response.task.TaskResponse;
import vn.G3.TodoApplication.entity.Task;
import vn.G3.TodoApplication.entity.User;
import vn.G3.TodoApplication.exception.AppException;
import vn.G3.TodoApplication.exception.ErrorCode;
import vn.G3.TodoApplication.mapper.task.TaskMapper;
import vn.G3.TodoApplication.repository.TaskRepository;
import vn.G3.TodoApplication.repository.UserRepository;
import vn.G3.TodoApplication.security.JwtUtils;

@Service
public class TaskService {
    private TaskRepository taskRepository;
    private UserRepository userRepository;
    private TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.taskMapper = taskMapper;
    }

    public List<TaskResponse> getTasks(String username) {

        List<Task> list = this.taskRepository.findByUser_Username(username);
        return this.taskMapper.toListTaskResponses(list);
    }

    public TaskResponse createTask(TaskCreateRequest request, String username) {
        Task task = this.taskMapper.toTask(request);
        var user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOTFOUND));
        task.setUser(user);
        this.taskRepository.save(task);

        return this.taskMapper.toTaskResponse(task);
    }

    public TaskResponse updateTask(TaskUpdateRequest request, String id) {
        Task task = this.taskRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        this.taskMapper.updateTaskFromTaskUpdateRequest(request, task);
        this.taskRepository.save(task);
        return this.taskMapper.toTaskResponse(task);
    }
}
