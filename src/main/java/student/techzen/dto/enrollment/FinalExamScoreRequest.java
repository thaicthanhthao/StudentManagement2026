package student.techzen.dto.enrollment;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FinalExamScoreRequest {
    @DecimalMin("0.00")
    @DecimalMax("10.00")
    BigDecimal finalExamScore;
}

