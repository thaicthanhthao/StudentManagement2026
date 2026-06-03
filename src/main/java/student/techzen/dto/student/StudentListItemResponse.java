package student.techzen.dto.student;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentListItemResponse {
    UUID personId;

    String studentCode;

    String fullName;

    String majorName;

    BigDecimal currentGpa;
}
