package student.techzen.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import student.techzen.dto.major.MajorProjectorNative;
import student.techzen.entity.Major;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MajorRepository extends JpaRepository<Major, UUID> {

    Optional<Major> findByMajorCode(String majorCode);

    boolean existsById(UUID id);

    @Query(value = "SELECT m.id as id, m.major_code as code, m.major_name as name " +
            "FROM majors m " +
            "WHERE (:name IS NULL OR LOWER(m.major_name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
            "AND (:majorCode IS NULL OR LOWER(m.major_code) LIKE LOWER(CONCAT('%', :majorCode, '%')))",
            countQuery = "SELECT COUNT(*) FROM majors m " +
                    "WHERE (:name IS NULL OR LOWER(m.major_name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
                    "AND (:majorCode IS NULL OR LOWER(m.major_code) LIKE LOWER(CONCAT('%', :majorCode, '%')))",
            nativeQuery = true)
    Page<MajorProjectorNative> getByAllNative(Pageable pageable,
                                              @Param("name") String majorName,
                                              @Param("majorCode") String majorCode);
}