package edu.module1.repository;

import edu.module1.model.entity.Task;
import edu.module1.model.entity.User;
import edu.module1.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepo extends JpaRepository<Task, Long> {
    List<Task> findAllByOwnerAndStatus(User owner, Status status);

    boolean existsByIdAndOwner(Long taskId, User owner);
}
