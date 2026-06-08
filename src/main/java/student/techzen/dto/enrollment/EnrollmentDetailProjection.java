package student.techzen.dto.enrollment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


public interface EnrollmentDetailProjection {

    UUID getEnrollmentId();

    LocalDateTime getEnrollmentDate();

    String getStatus();

    String getStudentCode();

    String getStudentName();

    String getMajorName();

    String getClassCode();

    String getClassName();

    String getSubjectCode();

    String getSubjectName();

    Integer getCredits();

    BigDecimal getAttendanceScore();

    BigDecimal getAssignmentScore();

    BigDecimal getMidtermScore();

    BigDecimal getFinalExamScore();

    BigDecimal getTotalScore();

    String getLetterGrade();

    BigDecimal getGradePoint();
}