package edu.module1.repository;

import edu.module1.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    boolean existsUserByEmail(String email);
    Optional<User> findUserByEmail(String email);
}
