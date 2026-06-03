package student.techzen.dto.student;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentDetailResponse {

    UUID personId;

    String studentCode;

    String fullName;

    Integer enrollmentYear;

    UUID majorId;

    String majorName;

    BigDecimal currentGpa;

    Instant createdAt;

    Instant updatedAt;
}