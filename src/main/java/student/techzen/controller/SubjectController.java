package student.techzen.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import student.techzen.dto.subject.SubjectCreateRequest;
import student.techzen.dto.subject.SubjectDetailResponse;
import student.techzen.service.SubjectService;

@RestController
@RequestMapping("${api.prefix}/subject")
@RequiredArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;
    @PostMapping
    public ResponseEntity<SubjectDetailResponse> createSubject(@RequestBody SubjectCreateRequest request) {
        return ResponseEntity.ok(subjectService.createSubject(request));
    }
}
