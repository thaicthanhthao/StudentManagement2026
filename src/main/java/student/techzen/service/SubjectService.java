package student.techzen.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import student.techzen.dto.subject.SubjectCreateRequest;
import student.techzen.dto.subject.SubjectDetailResponse;
import student.techzen.entity.Subject;
import student.techzen.repository.SubjectRepository;

import java.util.UUID;

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
    }

