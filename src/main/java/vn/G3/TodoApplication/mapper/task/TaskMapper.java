package vn.G3.TodoApplication.mapper.task;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import vn.G3.TodoApplication.dto.request.task.TaskCreateRequest;
import vn.G3.TodoApplication.dto.response.task.TaskResponse;
import vn.G3.TodoApplication.entity.Task;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    Task toTask(TaskCreateRequest taskCreateRequest);

    @Mapping(source = "user.username", target = "username")
    TaskResponse toTaskResponse(Task task);

    List<TaskResponse> toListTaskResponses(List<Task> list);
}
