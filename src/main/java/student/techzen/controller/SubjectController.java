package student.techzen.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import student.techzen.dto.subject.SubjectCreateRequest;
import student.techzen.dto.subject.SubjectDetailResponse;
import student.techzen.dto.subject.SubjectListItemResponse;
import student.techzen.dto.subject.SubjectUpdateRequest;
import student.techzen.service.SubjectService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${api.prefix}/subject")
@RequiredArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;

    @PostMapping
    public ResponseEntity<SubjectDetailResponse> createSubject(@RequestBody SubjectCreateRequest request) {
        return ResponseEntity.ok(subjectService.createSubject(request));
    }

    @GetMapping
    public ResponseEntity<List<SubjectListItemResponse>> getAllSubjects() {
        return ResponseEntity.ok(subjectService.getAllSubjects());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectDetailResponse> getSubjectById(@PathVariable UUID id) {
        return ResponseEntity.ok(subjectService.getSubjectById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubjectDetailResponse> updateSubject(@PathVariable UUID id, @RequestBody SubjectUpdateRequest request) {
        return ResponseEntity.ok(subjectService.updateSubject(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSubject(@PathVariable UUID id) {
        subjectService.deleteSubject(id);
        return ResponseEntity.ok(" Xóa môn học thành công ");
    }
}
