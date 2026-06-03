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
public class StudentUpdateRequest {
    UUID majorId;

    String studentCode;

    Integer enrollmentYear;

    BigDecimal currentGpa;
}
