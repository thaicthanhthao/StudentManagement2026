package student.techzen.dto.enrollment;

import java.math.BigDecimal;
import java.rmi.server.UID;
import java.util.UUID;

public interface EnrollmentSummaryProjection {
    UUID getEnrollmentId();

    String getStudentCode();

    String getStudentName();

    String getClassCode();

    String getSubjectName();

    String getStatus();

    BigDecimal getTotalScore();

    String getLetterGrade();
}
