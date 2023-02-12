package edu.module1.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.module1.model.entity.User;
import edu.module1.model.enums.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskDto {
    Long id;
    String title;
    String description;
    private String fileName;
    private LocalDateTime time;

    private UserDto ownerDto;
    private Status status;
}
