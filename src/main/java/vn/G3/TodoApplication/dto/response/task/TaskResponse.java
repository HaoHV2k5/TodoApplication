package vn.G3.TodoApplication.dto.response.task;

import java.time.LocalTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.G3.TodoApplication.dto.request.task.Prioritize;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskResponse {
    String id;
    String title;
    String detailInfo;
    Prioritize prioritize;
    LocalTime time;
    String username;
}
