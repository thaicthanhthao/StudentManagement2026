package student.techzen.mapper;

import org.springframework.stereotype.Component;
import student.techzen.dto.student.StudentDetailResponse;
import student.techzen.dto.student.StudentListItemResponse;
import student.techzen.entity.Student;

@Component
public class StudentMapper {

    public StudentListItemResponse toListItemResponse(
            Student student
    ) {

        return StudentListItemResponse.builder()
                .personId(student.getPerson_id())
                .studentCode(student.getStudentCode())
                .fullName(student.getPerson().getFullName())
                .majorName(student.getMajor().getMajorName())
                .currentGpa(student.getCurrentGpa())
                .build();
    }

    public StudentDetailResponse toDetailResponse(
            Student student
    ) {

        return StudentDetailResponse.builder()
                .personId(student.getPerson_id())
                .studentCode(student.getStudentCode())
                .fullName(student.getPerson().getFullName())
                .enrollmentYear(student.getEnrollmentYear())
                .majorId(student.getMajor().getId())
                .majorName(student.getMajor().getMajorName())
                .currentGpa(student.getCurrentGpa())
                .createdAt(student.getCreatedAt())
                .updatedAt(student.getUpdatedAt())
                .build();
    }
}