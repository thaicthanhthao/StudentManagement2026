package student.techzen.dto.enrollment;

import lombok.*;
import lombok.experimental.FieldDefaults;
import student.techzen.dto.Class.ClassResponseInEnrollment;
import student.techzen.dto.Subject.SubjectResponseInEnrollment;
import student.techzen.dto.student.StudentResponseInEnrollment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EnrollmentDetailResponse {

     UUID id;

     LocalDateTime enrollmentDate;

     String status;

     StudentResponseInEnrollment studentInfo;

     ClassResponseInEnrollment classInfo;

     SubjectResponseInEnrollment subject;

     private BigDecimal attendanceScore;

     private BigDecimal assignmentScore;

     private BigDecimal midtermScore;

     private BigDecimal finalExamScore;

     private BigDecimal totalScore;

     private String letterGrade;

     private BigDecimal gradePoint;
}
