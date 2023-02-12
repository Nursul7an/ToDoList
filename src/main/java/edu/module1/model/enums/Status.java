package edu.module1.model.enums;

import edu.module1.mapper.TaskMapper;
import edu.module1.model.entity.Task;
import edu.module1.repository.TaskRepo;
import org.springframework.http.ResponseEntity;

public enum Status {
    NOT_FINISHED{
        @Override
        public ResponseEntity<?> finish(Task task, TaskRepo taskRepo, TaskMapper taskMapper) {
            task.setStatus(FINISHED);
            Task updatedTask = taskRepo.save(task);
            return ResponseEntity.ok(taskMapper.toDto(updatedTask));
        }

        @Override
        public ResponseEntity<?> delete(Task task, TaskRepo taskRepo, TaskMapper taskMapper) {
            task.setStatus(TRASH);
            Task deletedTask = taskRepo.save(task);
            return ResponseEntity.ok(taskMapper.toDto(deletedTask));
        }
    },
    FINISHED{
        @Override
        public ResponseEntity<?> finish(Task task, TaskRepo taskRepository, TaskMapper taskMapper) {
            return ResponseEntity.badRequest().body("Task with ID " + task.getId() + " is already FINISHED!");
        }

        @Override
        public ResponseEntity<?> delete(Task task, TaskRepo taskRepository, TaskMapper taskMapper) {
            task.setStatus(TRASH);
            Task deletedTask = taskRepository.save(task);
            return ResponseEntity.ok(taskMapper.toDto(deletedTask));
        }

    },
    TRASH{
        @Override
        public ResponseEntity<?> finish(Task task, TaskRepo taskRepository, TaskMapper taskMapper) {
            return ResponseEntity.badRequest().body("Task with ID " + task.getId() + " is in TRASH!");
        }

        @Override
        public ResponseEntity<?> delete(Task task, TaskRepo taskRepository, TaskMapper taskMapper) {
            taskRepository.delete(task);
            return ResponseEntity.ok("Task with ID " + task.getId() + " has been DELETED!");
        }

    };

    public abstract ResponseEntity<?> finish(Task task, TaskRepo taskRepo, TaskMapper taskMapper);
    public abstract ResponseEntity<?> delete(Task task, TaskRepo taskRepo, TaskMapper taskMapper);
}
