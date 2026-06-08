package student.techzen.dto.enrollment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentSummaryResponse {

    private UUID enrollmentId;

    private String studentCode;

    private String studentName;

    private String classCode;

    private String subjectName;

    private String status;

    private BigDecimal totalScore;

    private String letterGrade;
}
