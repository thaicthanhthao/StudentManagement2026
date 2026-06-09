package student.techzen.service;

import jakarta.transaction.Transactional;
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
import student.techzen.dto.enrollment.*;
import student.techzen.dto.student.StudentResponseInEnrollment;
import student.techzen.entity.Clazz;
import student.techzen.entity.Enrollment;
import student.techzen.entity.Student;
import student.techzen.repository.ClazzRepository;
import student.techzen.repository.EnrollmentRepository;
import student.techzen.repository.StudentRepository;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class EnrollmentService {

    EnrollmentRepository enrollmentRepository;
    StudentRepository studentRepository;
    ClazzRepository clazzRepository;

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

     @Transactional
     public EnrollmentResponse createEnrollStudent(EnrollmentRequest request){

         UUID studentId = request.getStudentId();
         UUID classId = request.getClassId();

         Student student = studentRepository.findById(studentId)
                 .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found" + request.getStudentId()));

         Clazz clazz = clazzRepository.findById(classId)
                 .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Class not found" + request.getClassId()));

         if (!clazz.getStatus().equals("OPEN")) {
             throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lớp học phần này không còn mở đăng ký.");
         }

         //Kiểm tra: Chưa đăng ký trước đó
         if (enrollmentRepository.existsByStudentPersonIdAndClazzId(studentId, classId)) {
             throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sinh viên đã đăng ký lớp học phần này trước đó.");
         }

         //Kiểm tra: Lớp học đầy chỗ
         if (enrollmentRepository.countByClazzId(classId) >= clazz.getMaxStudents()) {
             throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lớp học phần đã vượt quá sức chứa.");
         }

         // kiểm tra lớp học đã đăng ký (enrollment) : ENROLLED chưa

         Enrollment enrollment = Enrollment.builder()
                 .student(student)
                 .clazz(clazz)
                 .enrollmentDate(Instant.now())
                 .status("ENROLLED")
                 .build();

         Enrollment saved = enrollmentRepository.save(enrollment);

         return EnrollmentResponse.builder()
                 .enrollmentId(saved.getId())
                 .studentId(student.getPerson_id())
                 .fullName(student.getPerson().getFullName())
                 .majorName(student.getMajor() != null ? student.getMajor().getMajorName() : null)
                 .classId(clazz.getId())
                 .classCode(clazz.getClassCode())
                 .className(clazz.getClassName())
                 .enrollmentDate(saved.getEnrollmentDate())
                 .status(saved.getStatus())
                 .createdAt(Instant.now())
                 .updatedAt(Instant.now())
                 .build();
     }

     public  ProgressGradeResponse updateProgressGrade(UUID id,ProgressGradeRequest request){

         Enrollment enrollment = enrollmentRepository.findById(id)
                 .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Enrollment not found" + id));

         if (!enrollment.getStatus().equals("ENROLLED")){
             throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lớp tham gia này hông thể nhập điểm.");
         }

         enrollment.setAttendanceScore(request.getAttendanceScore());
         enrollment.setAssignmentScore(request.getAssignmentScore());
         enrollment.setMidtermScore(request.getMidtermScore());
         enrollmentRepository.save(enrollment);

         Enrollment enrollmentSaved = enrollmentRepository.save(enrollment);

         return ProgressGradeResponse.builder()
                 .id(enrollmentSaved.getId())
                 .fullName(enrollmentSaved.getStudent().getPerson().getFullName())
                 .majorName(enrollmentSaved.getStudent().getMajor().getMajorName())
                 .attendanceScore(enrollmentSaved.getAttendanceScore())
                 .assignmentScore(enrollmentSaved.getAssignmentScore())
                 .midtermScore(enrollmentSaved.getMidtermScore())
                 .createdAt(enrollmentSaved.getCreatedAt())
                 .updatedAt(enrollmentSaved.getUpdatedAt())
                 .build();
     }


    public  FinalExamScoreResponse updateExamScore(UUID id,FinalExamScoreRequest request){

        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Enrollment not found " + id));

        if (!enrollment.getStatus().equals("COMPLETED")){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lớp tham gia này chưa kết thúc, không thể nhập điểm thi.");
        }

        enrollment.setFinalExamScore(request.getFinalExamScore());
        enrollmentRepository.save(enrollment);

        Enrollment enrollmentSaved = enrollmentRepository.save(enrollment);

        return FinalExamScoreResponse.builder()
                .id(enrollmentSaved.getId())
                .fullName(enrollmentSaved.getStudent().getPerson().getFullName())
                .majorName(enrollmentSaved.getStudent().getMajor().getMajorName())
                .finalExamScore(enrollmentSaved.getFinalExamScore())
                .createdAt(enrollmentSaved.getCreatedAt())
                .updatedAt(enrollmentSaved.getUpdatedAt())
                .build();
    }
}
