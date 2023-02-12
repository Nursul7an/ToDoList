package edu.module1.service.impls;

import edu.module1.exception.NotAllowedException;
import edu.module1.exception.NotFoundException;
import edu.module1.mapper.TaskMapper;
import edu.module1.model.dto.TaskDto;
import edu.module1.model.entity.Task;
import edu.module1.model.entity.User;
import edu.module1.model.enums.Status;
import edu.module1.model.wrapper.TaskEditRequest;
import edu.module1.model.wrapper.TaskRequest;
import edu.module1.repository.TaskRepo;
import edu.module1.service.TaskService;
import edu.module1.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepo taskRepo;
    private final FileUtil fileUtil;
    private final TaskMapper taskMapper;

    @Override
    public TaskDto create(TaskRequest taskRequest, Authentication auth) {
        long count = taskRepo.count();
        String fileName = FileUtil.writeFile(taskRequest.getFile(), count + 1, auth.getName());
        User user = (User) auth.getPrincipal();
        Task task = taskMapper.toTask(user,fileName, taskRequest);
        Task newTask = taskRepo.save(task);
        return taskMapper.toDto(newTask);
    }

    @Override
    public ResponseEntity<?> finish(Long id, Authentication authentication) {
        Task task = taskRepo.findById(id)
                .orElseThrow(()-> new NotFoundException("No such a task found with "+ id));
        User user = (User) authentication.getPrincipal();

        if (!user.getId().equals(task.getOwner().getId()))
            throw new NotAllowedException("You are allowed to use this task");
        return task.getStatus().finish(task,taskRepo,taskMapper);
    }

    @Override
    public ResponseEntity<?> delete(Long id, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Task task = taskRepo.findById(id)
                .orElseThrow(()-> new NotFoundException("No such a task found with "+ id));

        if (!user.getId().equals(task.getOwner().getId()))
            throw new NotAllowedException("You are allowed to use this task");
        return task.getStatus().delete(task,taskRepo,taskMapper);
    }

    @Override
    public ResponseEntity<?> clear(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        var taskList = taskRepo.findAllByOwnerAndStatus(user, Status.TRASH);
        taskRepo.deleteAll(taskList);
        return ResponseEntity.ok("You successfully deleted all trash");
    }

    @Override
    public ResponseEntity<?> edit(TaskEditRequest taskEditRequest, Authentication authentication) {
        User owner = (User) authentication.getPrincipal();
        Long taskId = taskEditRequest.getId();
        if (!taskRepo.existsById(taskId) || !taskRepo.existsByIdAndOwner(taskId, owner))
            return ResponseEntity.badRequest().body("Task with ID " + taskId + " doesn't exists or it's not yours!");
        return editAndSaveTask(taskEditRequest, authentication);
    }
    private ResponseEntity<TaskDto> editAndSaveTask(TaskEditRequest taskEditForm, Authentication auth) {
        Task task = taskRepo.findById(taskEditForm.getId()).get();
        String fileName = null;
        if (taskEditForm.getFile() != null)
            fileName = FileUtil.writeFile(taskEditForm.getFile(), taskEditForm.getId(), auth.getName());
        task = taskMapper.mapToTask(task, taskEditForm, fileName);
        Task editedTask = taskRepo.save(task);
        return ResponseEntity.ok(taskMapper.toDto(editedTask));
    }
}
