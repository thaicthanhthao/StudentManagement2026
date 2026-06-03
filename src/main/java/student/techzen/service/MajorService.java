package student.techzen.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;
import org.springframework.stereotype.Service;
import student.techzen.dto.major.MajorCreateRequest;
import student.techzen.dto.major.MajorDetailResponse;
import student.techzen.dto.major.MajorListItemResponse;
import student.techzen.dto.major.MajorUpdateRequest;
import student.techzen.entity.Major;
import student.techzen.repository.MajorRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MajorService {

    MajorRepository majorRepository;

    public MajorDetailResponse getDetail(UUID id) {
        Major major = majorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Major not found with ID: " + id));

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
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chuyên ngành với mã: " + code));

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
    public MajorDetailResponse createMajor(MajorCreateRequest request) {
        Major major = Major.builder()
                .id(UUID.randomUUID())
                .majorName(request.getName())
                .majorCode(request.getCode())
                .build();

        major = majorRepository.save(major);

        return MajorDetailResponse.builder()
                .id(major.getId())
                .name(major.getMajorName())
                .code(major.getMajorCode())
                .createdAt(major.getCreatedAt())
                .updatedAt(major.getUpdatedAt())
                .build();
    }

    @Transactional
    public MajorDetailResponse updateMajor(UUID id, MajorUpdateRequest request) {
        Major major = majorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chuyên ngành với ID: " + id));

        major.setMajorName(request.getName());
        major.setMajorCode(request.getCode());

        major = majorRepository.save(major);

        return MajorDetailResponse.builder()
                .id(major.getId())
                .name(major.getMajorName())
                .code(major.getMajorCode())
                .createdAt(major.getCreatedAt())
                .updatedAt(major.getUpdatedAt())
                .build();
    }

    @Transactional
    public void deleteMajor(UUID id) {
        if (!majorRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy chuyên ngành với ID: " + id);
        }

        try {
            majorRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Không thể xóa chuyên ngành này vì đang có sinh viên thuộc ngành này!");
        }
    }
}
