package edu.module1.service;

import edu.module1.model.dto.TaskDto;
import edu.module1.model.wrapper.TaskEditRequest;
import edu.module1.model.wrapper.TaskRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface TaskService {
    TaskDto create(TaskRequest taskRequest, Authentication authentication);

    ResponseEntity<?> finish(Long id, Authentication authentication);

    ResponseEntity<?> delete(Long id, Authentication authentication);

    ResponseEntity<?> clear(Authentication authentication);

    ResponseEntity<?> edit(TaskEditRequest taskEditRequest, Authentication authentication);
}
