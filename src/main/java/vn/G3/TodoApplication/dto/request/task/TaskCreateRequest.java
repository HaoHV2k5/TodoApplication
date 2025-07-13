package vn.G3.TodoApplication.dto.request.task;

import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskCreateRequest {
    @Column(nullable = false)
    String title;
    String detailInfo;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    Prioritize prioritize;
    @Column(nullable = false)
    LocalTime time;
    @Column(nullable = false)
    String categoryName;
}
