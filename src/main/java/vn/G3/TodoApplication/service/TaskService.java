package vn.G3.TodoApplication.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.G3.TodoApplication.dto.request.task.TaskCreateRequest;
import vn.G3.TodoApplication.dto.request.task.TaskUpdateRequest;
import vn.G3.TodoApplication.dto.response.task.TaskResponse;
import vn.G3.TodoApplication.entity.Category;
import vn.G3.TodoApplication.entity.Task;
import vn.G3.TodoApplication.entity.User;
import vn.G3.TodoApplication.exception.AppException;
import vn.G3.TodoApplication.exception.ErrorCode;
import vn.G3.TodoApplication.mapper.task.TaskMapper;
import vn.G3.TodoApplication.repository.CategoryRepository;
import vn.G3.TodoApplication.repository.TaskRepository;
import vn.G3.TodoApplication.repository.UserRepository;
import vn.G3.TodoApplication.security.JwtUtils;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskService {
    TaskRepository taskRepository;
    UserRepository userRepository;
    TaskMapper taskMapper;
    CategoryRepository categoryRepository;

    public List<TaskResponse> getTasks(Authentication authentication) {

        List<Task> list = this.taskRepository.findByUser_Username(authentication.getName());
        return this.taskMapper.toListTaskResponses(list);
    }

    public TaskResponse createTask(TaskCreateRequest request, String username) {
        Task task = this.taskMapper.toTask(request);
        var user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOTFOUND));
        var category = this.categoryRepository.findByCategoryName(request.getCategoryName())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOTFOUND));
        task.setUser(user);
        task.setCategory(category);
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
