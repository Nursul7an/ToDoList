package edu.module1.model.wrapper;

import edu.module1.model.enums.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskEditRequest {
    private Long id;
    private String title;
    private String description;
    private MultipartFile file;
    private Status status;
}
