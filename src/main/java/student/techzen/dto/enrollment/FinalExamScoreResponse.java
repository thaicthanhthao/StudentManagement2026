package student.techzen.dto.enrollment;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FinalExamScoreResponse {

    UUID id;

    String fullName;

    String majorName;

    BigDecimal finalExamScore;

    Instant createdAt;
    Instant updatedAt;
}

