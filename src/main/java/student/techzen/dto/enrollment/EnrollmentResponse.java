package student.techzen.dto.enrollment;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EnrollmentResponse {
     UUID enrollmentId;
     UUID studentId;
     String fullName;
     String majorName;
     UUID classId;
     String classCode;
     String className;
     Instant enrollmentDate;
     String status;
     Instant createdAt;
     Instant updatedAt;
}
