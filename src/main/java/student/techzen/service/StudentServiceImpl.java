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
import student.techzen.entity.Major;
import student.techzen.entity.Person;
import student.techzen.entity.Student;
import student.techzen.mapper.StudentMapper;
import student.techzen.repository.MajorRepository;
import student.techzen.repository.PersonRepository;
import student.techzen.repository.StudentRepository;

import java.util.UUID;
@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepo;
    private final StudentMapper studentMapper;
    private final PersonRepository personRepo;
    private final MajorRepository majorRepo;

    @Override
    public PageResponse<StudentListItemResponse> getAllStudents(Pageable pageable) {

        Page<Student> pageData = studentRepo.findAll(pageable);

        return new PageResponse<>(pageData.map(studentMapper::toListItemResponse));
    }

    @Override
    public StudentDetailResponse getStudentById(UUID id) {

        Student student = studentRepo.findById(id)
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Student not found"
                                ));

        return studentMapper.toDetailResponse(student);
    }

    @Override
    public StudentDetailResponse createStudent(StudentCreateRequest request) {

        Person person = personRepo.findById(request.getPersonId())
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Person not found"
                        ));

        Major major = majorRepo.findById(request.getMajorId())
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Major not found"
                        ));

        Student student = Student.builder()
                        .person(person)
                        .major(major)
                        .studentCode(request.getStudentCode())
                        .enrollmentYear(request.getEnrollmentYear())
                        .currentGpa(request.getCurrentGpa())
                        .build();

        return studentMapper.toDetailResponse(studentRepo.save(student));
    }

    @Override
    public StudentDetailResponse updateStudent(UUID id, StudentUpdateRequest request) {

        Student student = studentRepo.findById(id)
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Student not found"
                                ));

        Major major = majorRepo.findById(request.getMajorId())
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Major not found"
                        ));

        student.setStudentCode(request.getStudentCode());

        student.setEnrollmentYear(request.getEnrollmentYear());

        student.setCurrentGpa(request.getCurrentGpa());

        student.setMajor(major);

        return studentMapper.toDetailResponse(studentRepo.save(student));
    }

    @Override
    public PageResponse<StudentListItemResponse> searchStudents(String keyword, Pageable pageable) {

        Page<Student> pageData = studentRepo.searchByStudentCode(keyword, pageable);

        return new PageResponse<>(pageData.map(studentMapper::toListItemResponse));
    }

    @Override
    public void deleteStudent(UUID id) {

        if (!studentRepo.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Student not found"
            );
        }

        studentRepo.deleteById(id);
    }


}
