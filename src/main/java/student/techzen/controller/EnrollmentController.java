package student.techzen.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import student.techzen.dto.ApiResponse;
import student.techzen.dto.PageResponse;
import student.techzen.dto.enrollment.EnrollmentSummaryResponse;
import student.techzen.service.EnrollmentService;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("${api.prefix}/enrollments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EnrollmentController {

    EnrollmentService enrollmentService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id){

        return ResponseEntity.ok(ApiResponse.builder()
                        .success(true)
                        .data(enrollmentService.getByIdDetail(id))
                .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<EnrollmentSummaryResponse>>> getAllEnrollments(
            @PageableDefault(page = 0,size = 10) Pageable pageable,
            @RequestParam(required = false) String studentCode,
            @RequestParam(required = false) String classCode,
            @RequestParam(required = false) String subjectName,
            @RequestParam(required = false) BigDecimal fromTotalScore,
            @RequestParam(required = false) BigDecimal toTotalScore

    ){
        return ResponseEntity.ok(ApiResponse.<PageResponse<EnrollmentSummaryResponse>>builder()
                .success(true)
                .data(enrollmentService.gerSearchByAll(pageable,studentCode,classCode,subjectName,fromTotalScore,toTotalScore))
                .build());
    }
}
