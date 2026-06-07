package student.techzen.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import student.techzen.dto.ApiResponse;
import student.techzen.dto.PageResponse;
import student.techzen.dto.teacher.*;
import student.techzen.service.TeacherService;

import java.util.UUID;

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
        PageResponse<TeacherDetailResponse> response = teacherService.getByAll(pageable, fullName, teacherCode);

        return ResponseEntity.ok(ApiResponse.builder()
                        .success(true)
                        .data(response)
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TeacherDetailResponse>> getById(@PathVariable UUID id){
        TeacherDetailResponse response = teacherService.getById(id);

        return ResponseEntity.ok(ApiResponse.<TeacherDetailResponse>builder()
                .success(true)
                .data(response)
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTeacher(@PathVariable UUID id,@Valid @RequestBody TeacherUpdateRequest request){

        TeacherDetailResponse response = teacherService.updateTeacher(id,request);

        return  ResponseEntity.ok(ApiResponse.<TeacherDetailResponse>builder()
                .success(true)
                .data(response)
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTeacher(@PathVariable UUID id){

        teacherService.deleteTeacher(id);

        return  ResponseEntity.ok(ApiResponse.<TeacherMessageResponse>builder()
                        .success(true)
                        .data(TeacherMessageResponse.builder()
                                .message("Delete teacher success")
                                .build())
                        .build());
    }

}
