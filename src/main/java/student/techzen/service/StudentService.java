package student.techzen.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import student.techzen.dto.PageResponse;
import student.techzen.dto.student.*;

import java.util.UUID;

public interface StudentService {
    PageResponse<StudentListItemResponse>
    getAllStudents(Pageable pageable);

    StudentDetailResponse
    getStudentById(UUID id);

    StudentDetailResponse
    createStudent(StudentCreateRequest request);

    StudentDetailResponse
    updateStudent(
            UUID id,
            StudentUpdateRequest request
    );

    void deleteStudent(UUID id);

    PageResponse<StudentListItemResponse>
    searchStudents(
            String keyword,
            Pageable pageable
    );
}
