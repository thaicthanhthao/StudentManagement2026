package student.techzen.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import student.techzen.dto.Class.ClassResponseInEnrollment;
import student.techzen.dto.PageResponse;
import student.techzen.dto.Subject.SubjectResponseInEnrollment;
import student.techzen.dto.enrollment.EnrollmentDetailProjection;
import student.techzen.dto.enrollment.EnrollmentDetailResponse;
import student.techzen.dto.enrollment.EnrollmentSummaryProjection;
import student.techzen.dto.enrollment.EnrollmentSummaryResponse;
import student.techzen.dto.student.StudentResponseInEnrollment;
import student.techzen.repository.EnrollmentRepository;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class EnrollmentService {

    EnrollmentRepository enrollmentRepository;

     public EnrollmentDetailResponse getByIdDetail(UUID id){

         EnrollmentDetailProjection data = enrollmentRepository.findEnrollmentDetailNative(id)
                 .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Enrollement not found" + id));

         EnrollmentDetailResponse response = EnrollmentDetailResponse.builder()
                 .id(data.getEnrollmentId())
                 .enrollmentDate(data.getEnrollmentDate())
                 .status(data.getStatus())

                 .studentInfo(
                         StudentResponseInEnrollment.builder()
                                 .studentCode(data.getStudentCode())
                                 .fullName(data.getStudentName())
                                 .majorName(data.getMajorName())
                                 .build()
                 )

                 .classInfo(
                         ClassResponseInEnrollment.builder()
                                 .classCode(data.getClassCode())
                                 .className(data.getClassName())
                                 .build()
                 )

                 .subject(
                         SubjectResponseInEnrollment.builder()
                                 .subjectCode(data.getSubjectCode())
                                 .subjectName(data.getSubjectName())
                                 .credits(data.getCredits())
                                 .build()
                 )
                 .attendanceScore(data.getAttendanceScore())
                 .assignmentScore(data.getAssignmentScore())
                 .midtermScore(data.getMidtermScore())
                 .finalExamScore(data.getFinalExamScore())
                 .totalScore(data.getTotalScore())
                 .letterGrade(data.getLetterGrade())
                 .gradePoint(data.getGradePoint())
                 .build();
         return response;
     }

     public PageResponse<EnrollmentSummaryResponse> gerSearchByAll(
             Pageable pageable,
             String studentCode,
             String classCode,
             String subjectName,
             BigDecimal  fromTotalScore,
             BigDecimal  toTotalScore
     ){
         Page<EnrollmentSummaryProjection> dataPage = enrollmentRepository.findAllEnrollmentSummary(
                 pageable,studentCode,classCode,subjectName,fromTotalScore,toTotalScore);
         Page<EnrollmentSummaryResponse> responses = dataPage.map(data ->
             EnrollmentSummaryResponse.builder()
                     .enrollmentId(data.getEnrollmentId())
                     .studentCode(data.getStudentCode())
                     .subjectName(data.getSubjectName())
                     .classCode(data.getClassCode())
                     .subjectName(data.getSubjectName())
                     .status(data.getStatus())
                     .totalScore(data.getTotalScore())
                     .letterGrade(data.getLetterGrade())
                     .build()
         );
         return new PageResponse<>(responses);
     }
}
