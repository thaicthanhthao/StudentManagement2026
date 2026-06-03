package student.techzen.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import student.techzen.dto.ApiResponse;
import student.techzen.dto.teacher.TeacherCreateRequest;
import student.techzen.dto.teacher.TeacherReponse;
import student.techzen.service.TeacherService;

@RestController
@RequestMapping("${api.prefix}/teachers")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TeacherController {

    TeacherService teacherService;

    @PostMapping
    public ResponseEntity<ApiResponse<TeacherReponse>> createTeacher(
            @Valid @RequestBody TeacherCreateRequest teacherCreateRequest)
    {
        TeacherReponse teacherReponse = teacherService.createTeacher(teacherCreateRequest);
        return ResponseEntity.status(201).body(ApiResponse.<TeacherReponse>builder()
                        .success(true)
                        .data(teacherReponse)
                        .build());
    }

    @GetMapping ResponseEntity<?> getByAll(
            @ParameterObject @PageableDefault(size = 10) Pageable pageable,
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) String teacherCode
    ){

        return null;
    }
}
