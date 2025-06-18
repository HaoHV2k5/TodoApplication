package vn.G3.TodoApplication.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import vn.G3.TodoApplication.dto.request.task.TaskCreateRequest;
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
    @Autowired
    private JwtUtils jwtUtils;

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
                .orElseThrow(() -> new AppException(ErrorCode.EXiST_USER));
        task.setUser(user);
        this.taskRepository.save(task);

        return this.taskMapper.toTaskResponse(task);
    }
}
