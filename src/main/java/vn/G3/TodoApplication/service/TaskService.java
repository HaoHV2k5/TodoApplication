package vn.G3.TodoApplication.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.G3.TodoApplication.entity.Task;
import vn.G3.TodoApplication.exception.AppException;
import vn.G3.TodoApplication.exception.ErrorCode;
import vn.G3.TodoApplication.repository.TaskRepository;
import vn.G3.TodoApplication.repository.UserRepository;

@Service
public class TaskService {
    private TaskRepository taskRepository;
    private UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public List<Task> getTasks(String username) {
        var user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_INVALID));
        List<Task> list = user.getTasks();
        return list;
    }
}
