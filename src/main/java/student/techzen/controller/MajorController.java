package student.techzen.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import student.techzen.dto.ApiResponse;
import student.techzen.dto.PageResponse;
import student.techzen.dto.major.MajorCreateRequest;
import student.techzen.dto.major.MajorDetailResponse;
import student.techzen.dto.major.MajorListItemResponse;
import student.techzen.dto.major.MajorUpdateRequest;
import student.techzen.service.MajorService;
import student.techzen.dto.major.MajorResponse;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/majors")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MajorController {

    MajorService majorService;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<MajorListItemResponse>>> getAllMajors(
            @org.springdoc.core.annotations.ParameterObject @org.springframework.data.web.PageableDefault(size = 10) Pageable pageable,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String code
    ) {
        PageResponse<MajorListItemResponse> response = majorService.getAll(pageable, name, code);

        return ResponseEntity.ok(ApiResponse.<PageResponse<MajorListItemResponse>>builder()
                .success(true)
                .data(response)
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MajorDetailResponse>> getDetail(@PathVariable UUID id) {
        MajorDetailResponse response = majorService.getDetail(id);

        return ResponseEntity.ok(ApiResponse.<MajorDetailResponse>builder()
                .success(true)
                .data(response)
                .build());
    }

    @GetMapping("/by-major-code")
    public ResponseEntity<ApiResponse<MajorDetailResponse>> getByCode(@RequestParam String code) {
        MajorDetailResponse response = majorService.getMajorByCode(code);

        return ResponseEntity.ok(ApiResponse.<MajorDetailResponse>builder()
                .success(true)
                .data(response)
                .build());
    }

    // dùng để lấy hàng loạt (bulk) thông tin rút gọn của các Chuyên ngành (Major) cùng một lúc dựa trên một danh sách các ID được truyền lên
    @PostMapping("/bulk-by-ids")
    public ResponseEntity<List<MajorListItemResponse>> getBulkByIds(@RequestBody List<UUID> ids) {
        List<MajorListItemResponse> response = majorService.getMajorsBulkByIds(ids);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<MajorResponse>> createMajor(@Valid @RequestBody MajorCreateRequest majorCreateRequest) {

        MajorResponse majorResponse = majorService.createMajor(majorCreateRequest);

        return ResponseEntity.status(201).body(ApiResponse.<MajorResponse>builder()
                .success(true)
                .data(majorResponse)
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MajorResponse>> updateMajor(
            @PathVariable UUID id,
            @Valid @RequestBody MajorUpdateRequest majorUpdateRequest) {

        MajorResponse majorResponse = majorService.updateMajor(id, majorUpdateRequest);

        return ResponseEntity.ok(ApiResponse.<MajorResponse>builder()
                .success(true)
                .data(majorResponse)
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMajor(@PathVariable UUID id) {
        majorService.deleteMajor(id);
        return ResponseEntity.ok(ApiResponse.<MajorCreateRequest.MajorMessageResponse>builder()
                .success(true)
                .data(MajorCreateRequest.MajorMessageResponse.builder()
                        .message("Delete major success")
                        .build())
                .build());
    }
}
