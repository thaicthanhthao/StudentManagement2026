package student.techzen.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import student.techzen.dto.subject.SubjectCreateRequest;
import student.techzen.dto.subject.SubjectDetailResponse;
import student.techzen.dto.subject.SubjectListItemResponse;
import student.techzen.dto.subject.SubjectUpdateRequest;
import student.techzen.entity.Subject;
import student.techzen.repository.SubjectRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;

    public SubjectDetailResponse createSubject(SubjectCreateRequest request) {
        if (subjectRepository.existsBySubjectCode(request.getSubjectCode())) {
            throw new RuntimeException("Mã môn học đã tồn tại!");
        }
        Subject subject = Subject.builder()
                .id(UUID.randomUUID())
                .subjectCode(request.getSubjectCode())
                .subjectName(request.getSubjectName())
                .credits(request.getCredits())
                .build();
        subject = subjectRepository.save(subject);
        return SubjectDetailResponse.builder()
                .id(subject.getId())
                .subjectCode(subject.getSubjectCode())
                .subjectName(subject.getSubjectName())
                .credits(subject.getCredits())
                .build();
    }

    public List<SubjectListItemResponse> getAllSubjects() {
        return subjectRepository.findAll().stream()
                .map(subject -> SubjectListItemResponse.builder()
                        .id(subject.getId())
                        .subjectCode(subject.getSubjectCode())
                        .subjectName(subject.getSubjectName())
                        .build())
                .collect(Collectors.toList());
    }

    public SubjectDetailResponse getSubjectById(UUID id) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(" Không tìm thấy môn học với ID: " + id));
        return SubjectDetailResponse.builder()
                .id(subject.getId())
                .subjectCode(subject.getSubjectCode())
                .subjectName(subject.getSubjectName())
                .credits(subject.getCredits())
                .build();
    }

    public SubjectDetailResponse updateSubject(UUID id, SubjectUpdateRequest request) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(" Không tìm thấy môn học "));
        subject.setSubjectCode(request.getSubjectCode());
        subject.setSubjectName(request.getSubjectName());
        subject.setCredits(request.getCredits());
        subject = subjectRepository.save(subject);
        return SubjectDetailResponse.builder()
                .id(subject.getId())
                .subjectCode(subject.getSubjectCode())
                .subjectName(subject.getSubjectName())
                .credits(subject.getCredits())
                .build();
    }

    public void deleteSubject(UUID id) {
        if (!subjectRepository.existsById(id)) {
            throw new RuntimeException(" Không tìm thấy môn học để xóa ");
        }
        subjectRepository.deleteById(id);
    }
}

