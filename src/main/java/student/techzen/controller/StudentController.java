package student.techzen.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import student.techzen.dto.ApiResponse;
import student.techzen.dto.PageResponse;
import student.techzen.dto.student.*;
import student.techzen.service.StudentService;

import java.util.UUID;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

//    @GetMapping
//    public ApiResponse<PageResponse<StudentListItemResponse>>
//    getAllStudents(Pageable pageable) {
//
//        return ApiResponse.<PageResponse<StudentListItemResponse>>builder()
//                .success(true)
//                .data(studentService.getAllStudents(pageable))
//                .build();
//    }
//
//    @GetMapping("/{id}")
//    public ApiResponse<StudentDetailResponse>
//    getStudentById(@PathVariable UUID id) {
//
//        return ApiResponse.<StudentDetailResponse>builder()
//                .success(true)
//                .data(studentService.getStudentById(id))
//                .build();
//    }
//
//    @DeleteMapping("/{id}")
//    public ApiResponse<String> deleteStudent(@PathVariable UUID id
//    ) {
//        studentService.deleteStudent(id);
//
//        return ApiResponse.<String>builder()
//                .success(true)
//                .data("Delete successfully")
//                .build();
//    }

    @GetMapping
    public ApiResponse<PageResponse<StudentResponse>>
    getAllStudents(
            Pageable pageable
    ) {

        return ApiResponse
                .<PageResponse<StudentResponse>>
                        builder()
                .success(true)
                .data(
                        studentService
                                .getAllStudents(pageable)
                )
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<StudentResponse>
    getStudentById(
            @PathVariable UUID id
    ) {

        return ApiResponse
                .<StudentResponse>builder()
                .success(true)
                .data(
                        studentService
                                .getStudentById(id)
                )
                .build();
    }

    @PostMapping
    public ApiResponse<StudentResponse>
    createStudent(
            @Valid
            @RequestBody
            StudentCreateRequest request
    ) {

        return ApiResponse
                .<StudentResponse>builder()
                .success(true)
                .data(
                        studentService
                                .createStudent(request)
                )
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<StudentResponse>
    updateStudent(
            @PathVariable UUID id,

            @Valid
            @RequestBody
            StudentUpdateRequest request
    ) {

        return ApiResponse
                .<StudentResponse>builder()
                .success(true)
                .data(
                        studentService
                                .updateStudent(
                                        id,
                                        request
                                )
                )
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String>
    deleteStudent(
            @PathVariable UUID id
    ) {

        studentService.deleteStudent(id);

        return ApiResponse.<String>builder()
                .success(true)
                .data("Delete successfully")
                .build();
    }

    @GetMapping("/search")
    public ApiResponse<PageResponse<StudentResponse>>
    searchStudents(
            @RequestParam String keyword,
            Pageable pageable
    ) {

        return ApiResponse
                .<PageResponse<StudentResponse>>
                        builder()
                .success(true)
                .data(
                        studentService
                                .searchStudents(
                                        keyword,
                                        pageable
                                )
                )
                .build();
    }
}
