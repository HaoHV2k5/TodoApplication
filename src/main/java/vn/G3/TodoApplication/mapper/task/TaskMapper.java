package vn.G3.TodoApplication.mapper.task;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import vn.G3.TodoApplication.dto.request.task.TaskCreateRequest;
import vn.G3.TodoApplication.dto.request.task.TaskUpdateRequest;
import vn.G3.TodoApplication.dto.response.task.TaskResponse;
import vn.G3.TodoApplication.entity.Task;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    Task toTask(TaskCreateRequest taskCreateRequest);

    Task toTask(TaskUpdateRequest taskUpdateRequest);

    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "category.categoryName", target = "categoryName")
    TaskResponse toTaskResponse(Task task);

    List<TaskResponse> toListTaskResponses(List<Task> list);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTaskFromTaskUpdateRequest(TaskUpdateRequest taskUpdateRequest, @MappingTarget Task task);
}
