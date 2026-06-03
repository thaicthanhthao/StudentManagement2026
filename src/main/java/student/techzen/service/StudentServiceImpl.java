package student.techzen.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import student.techzen.dto.PageResponse;
import student.techzen.dto.student.*;
import student.techzen.entity.Person;
import student.techzen.entity.Student;
import student.techzen.mapper.StudentMapper;
import student.techzen.repository.StudentRepository;

import java.util.UUID;
@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepo;
    private final StudentMapper studentMapper;

//    @Override
//    public PageResponse<StudentListItemResponse> getAllStudents(Pageable pageable) {
//        Page<Student> pageData = studentRepo.findAll(pageable);
//
//        return new PageResponse<>(pageData.map(studentMapper::toListItemResponse));
//    }
//
//    @Override
//    public StudentDetailResponse getStudentById(UUID id) {
//        Student student = studentRepo.findById(id).orElseThrow(() -> new ResponseStatusException(
//                                        HttpStatus.NOT_FOUND,
//                                        "Student not found"
//                                ));
//
//        return studentMapper.toDetailResponse(
//                student
//        );
//    }
//
//    @Override
//    public StudentDetailResponse createStudent(StudentCreateRequest request) {
//        return null;
//    }
//
//
//    @Override
//    public StudentDetailResponse updateStudent(UUID id, StudentUpdateRequest request) {
//        return null;
//    }
//
//    @Override
//    public void deleteStudent(UUID id) {
//        Student student = studentRepo.findById(id).orElseThrow(() -> new ResponseStatusException(
//                                        HttpStatus.NOT_FOUND,
//                                        "Student not found"
//        ));
//
//        studentRepo.delete(student);
//    }
//
//    @Override
//    public PageResponse<StudentListItemResponse> searchStudents(String keyword, Pageable pageable) {
//        return null;
//    }

    @Override
    public PageResponse<StudentResponse>
    getAllStudents(Pageable pageable) {

        Page<Student> pageData =
                studentRepo.findAll(pageable);

        return new PageResponse<>(
                pageData.map(
                        studentMapper::toResponse
                )
        );
    }

    @Override
    public StudentResponse getStudentById(
            UUID id
    ) {

        Student student =
                studentRepo.findById(id)
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Student not found"
                                ));

        return studentMapper.toResponse(student);
    }

    @Override
    public StudentResponse createStudent(
            StudentCreateRequest request
    ) {

        if (studentRepo.existsByStudentCode(
                request.getStudentCode())) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Student code already exists"
            );
        }

        Student student =
                Student.builder()
                        .studentCode(
                                request.getStudentCode()
                        )
                        .enrollmentYear(
                                request.getEnrollmentYear()
                        )
                        .currentGpa(
                                request.getCurrentGpa()
                        )
                        .build();

        return studentMapper.toResponse(
                studentRepo.save(student)
        );
    }

    @Override
    public StudentResponse updateStudent(
            UUID id,
            StudentUpdateRequest request
    ) {

        Student student =
                studentRepo.findById(id)
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Student not found"
                                ));

        student.setStudentCode(
                request.getStudentCode()
        );

        student.setEnrollmentYear(
                request.getEnrollmentYear()
        );

        student.setCurrentGpa(
                request.getCurrentGpa()
        );

        return studentMapper.toResponse(
                studentRepo.save(student)
        );
    }

    @Override
    public void deleteStudent(
            UUID id
    ) {

        Student student =
                studentRepo.findById(id)
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Student not found"
                                ));

        studentRepo.delete(student);
    }

    @Override
    public PageResponse<StudentResponse>
    searchStudents(
            String keyword,
            Pageable pageable
    ) {

        Page<Student> pageData =
                studentRepo
                        .searchByStudentCode(
                                keyword,
                                pageable
                        );

        return new PageResponse<>(
                pageData.map(
                        studentMapper::toResponse
                )
        );
    }
}
