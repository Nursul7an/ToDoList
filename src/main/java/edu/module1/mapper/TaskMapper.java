package edu.module1.mapper;

import edu.module1.model.dto.TaskDto;
import edu.module1.model.entity.Task;
import edu.module1.model.entity.User;
import edu.module1.model.enums.Status;
import edu.module1.model.wrapper.TaskEditRequest;
import edu.module1.model.wrapper.TaskRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class TaskMapper {
    private final UserMapper userMapper;
    public Task toTask(User user, String fileName, TaskRequest taskRequest) {
        return Task.builder()
                .description(taskRequest.getDescription())
                .title(taskRequest.getTitle())
                .owner(user)
                .time(LocalDateTime.now())
                .fileName(fileName)
                .status(Status.NOT_FINISHED)
                .build();
    }

    public TaskDto toDto(Task newTask) {
        return TaskDto.builder()
                .id(newTask.getId())
                .ownerDto(userMapper.toDto(newTask.getOwner()))
                .description(newTask.getDescription())
                .time(newTask.getTime())
                .fileName(newTask.getFileName())
                .title(newTask.getTitle())
                .status(newTask.getStatus())
                .build();
    }

    public Task mapToTask(Task task, TaskEditRequest taskEditForm, String fileName) {
        if (taskEditForm.getTitle() != null)
            task.setTitle(taskEditForm.getTitle());
        if (taskEditForm.getDescription() != null)
            task.setDescription(taskEditForm.getDescription());
        if (taskEditForm.getStatus() != null)
            task.setStatus(taskEditForm.getStatus());
        if (fileName != null)
            task.setFileName(fileName);
        return task;
    }
}
