package student.techzen.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import student.techzen.dto.major.*;
import student.techzen.entity.Major;
import student.techzen.repository.MajorRepository;
import student.techzen.repository.StudentRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MajorService {

    MajorRepository majorRepository;
    StudentRepository studentRepository;
    public MajorDetailResponse getDetail(UUID id) {
        Major major = majorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Major not found: " + id));

        return MajorDetailResponse.builder()
                .id(major.getId())
                .name(major.getMajorName())
                .code(major.getMajorCode())
                .createdAt(major.getCreatedAt())
                .updatedAt(major.getUpdatedAt())
                .build();
    }




    public MajorDetailResponse getMajorByCode(String code) {
        Major major = majorRepository.findByMajorCode(code)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Major not found: " + code));

        return MajorDetailResponse.builder()
                .id(major.getId())
                .name(major.getMajorName())
                .code(major.getMajorCode())
                .createdAt(major.getCreatedAt())
                .updatedAt(major.getUpdatedAt())
                .build();
    }

    public List<MajorListItemResponse> getMajorsBulkByIds(List<UUID> ids) {
        List<Major> majors = majorRepository.findAllById(ids);

        return majors.stream()
                .map(major -> MajorListItemResponse.builder()
                        .id(major.getId())
                        .name(major.getMajorName())
                        .code(major.getMajorCode())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public MajorResponse createMajor(MajorCreateRequest request) {
        Major major = Major.builder()
                .id(UUID.randomUUID())
                .majorName(request.getName())
                .majorCode(request.getCode())
                .build();

        major = majorRepository.save(major);

        return MajorResponse.builder()
                .id(major.getId())
                .majorName(major.getMajorName())
                .majorCode(major.getMajorCode())
                .createdAt(major.getCreatedAt())
                .updatedAt(major.getUpdatedAt())
                .build();
    }

    @Transactional
    public MajorResponse updateMajor(UUID id, MajorUpdateRequest majorUpdateRequest) {
        Major major = majorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Major not found: " + id));

        major.setMajorName(majorUpdateRequest.getName());
        major.setMajorCode(majorUpdateRequest.getCode());

        major = majorRepository.save(major);

        return MajorResponse.builder()
                .id(major.getId())
                .majorName(major.getMajorName())
                .majorCode(major.getMajorCode())
                .createdAt(major.getCreatedAt())
                .updatedAt(major.getUpdatedAt())
                .build();
    }

    @Transactional
    public void deleteMajor(UUID id) {
        Major major = majorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Major not found: " + id));

        boolean hasStudents = studentRepository.existsByMajorId(id);

        if (hasStudents) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Cannot be deleted! There are currently students enrolled in this major.");
        }

        majorRepository.delete(major);
    }
}
