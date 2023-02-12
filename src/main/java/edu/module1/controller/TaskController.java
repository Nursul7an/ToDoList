package edu.module1.controller;

import edu.module1.model.dto.TaskDto;
import edu.module1.model.wrapper.TaskEditRequest;
import edu.module1.model.wrapper.TaskRequest;
import edu.module1.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    @PostMapping("/create")
    public ResponseEntity<TaskDto> creatTask(@Valid @ModelAttribute TaskRequest taskRequest, Authentication auth){
        return ResponseEntity.ok(taskService.create(taskRequest, auth));
    }
    @PostMapping("/finish")
    public ResponseEntity<?> finishTask(@RequestParam Long id, Authentication authentication){
        taskService.finish(id,authentication);
        return ResponseEntity.ok("You successfully finished your task");
    }
    @PostMapping("delete")
    public ResponseEntity<?> deleteTask(@RequestParam Long id, Authentication authentication){
        return taskService.delete(id, authentication);
    }
    @DeleteMapping("/clear/trash")
    public ResponseEntity<?> clearTrash(Authentication authentication){
        return taskService.clear(authentication);
    }
    @PutMapping("/edit")
    public ResponseEntity<?> editTask(@Valid @RequestBody TaskEditRequest taskEditRequest,
                                            Authentication authentication){
        return taskService.edit(taskEditRequest,authentication);
    }
}
