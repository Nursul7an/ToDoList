package edu.module1.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.module1.model.enums.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tasks")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Task {
    @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String title;
    String description;
    @JsonProperty("file_name")
    private String fileName;
    private LocalDateTime time;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;
    @Enumerated(EnumType.STRING)
    private Status status;
}
