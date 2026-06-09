package student.techzen.dto.enrollment;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EnrollmentRequest {

    @NotNull
    UUID studentId;

    @NotNull
    UUID classId;
}
