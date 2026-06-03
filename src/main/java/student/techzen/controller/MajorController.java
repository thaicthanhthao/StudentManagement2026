package student.techzen.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import student.techzen.dto.major.MajorCreateRequest;
import student.techzen.dto.major.MajorDetailResponse;
import student.techzen.dto.major.MajorListItemResponse;
import student.techzen.dto.major.MajorUpdateRequest;
import student.techzen.service.MajorService;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/majors")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MajorController {

    MajorService majorService;

    @GetMapping("/{id}")
    public ResponseEntity<MajorDetailResponse> getDetail(@PathVariable UUID id) {
        MajorDetailResponse response = majorService.getDetail(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-major-code")
    public ResponseEntity<MajorDetailResponse> getByCode(@RequestParam String code) {
        MajorDetailResponse response = majorService.getMajorByCode(code);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/bulk-by-ids")
    public ResponseEntity<List<MajorListItemResponse>> getBulkByIds(@RequestBody List<UUID> ids) {
        List<MajorListItemResponse> response = majorService.getMajorsBulkByIds(ids);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<MajorDetailResponse> create(@Valid @RequestBody MajorCreateRequest request) {

        MajorDetailResponse response = majorService.createMajor(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MajorDetailResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody MajorUpdateRequest request) {

        MajorDetailResponse response = majorService.updateMajor(id, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMajor(@PathVariable UUID id) {
        majorService.deleteMajor(id);
        return ResponseEntity.noContent().build();
    }
}
