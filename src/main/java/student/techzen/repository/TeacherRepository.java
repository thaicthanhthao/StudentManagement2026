package student.techzen.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import student.techzen.dto.teacher.TeacherProjectorNative;
import student.techzen.entity.Teacher;

import java.util.UUID;

public interface TeacherRepository extends JpaRepository<Teacher, UUID> {
    @Query(value = "SELECT t.person_id as id, p.full_name as fullName, u.status as status, " +
            "u.email as email, p.dob as dob, p.gender as gender, p.phone as phone, " +
            "p.address as address, t.teacher_code as teacherCode, t.specialization as specialization " +
            "FROM teachers t " +
            "INNER JOIN people p ON t.person_id = p.id " +
            "INNER JOIN users u ON p.user_id = u.id " +
            "WHERE (:fullName IS NULL OR LOWER(p.full_name) LIKE LOWER(CONCAT('%', :fullName, '%'))) " +
            "AND (:teacherCode IS NULL OR LOWER(t.teacher_code) LIKE LOWER(CONCAT('%', :teacherCode, '%')))",
            countQuery = "SELECT COUNT(*) FROM teachers t " +
                    "INNER JOIN people p ON t.person_id = p.id " +
                    "WHERE (:fullName IS NULL OR LOWER(p.full_name) LIKE LOWER(CONCAT('%', :fullName, '%'))) " +
                    "AND (:teacherCode IS NULL OR LOWER(t.teacher_code) LIKE LOWER(CONCAT('%', :teacherCode, '%')))",
            nativeQuery = true)
    Page<TeacherProjectorNative> getByAllNative(Pageable pageable,
                                                @Param("fullName") String fullName,
                                                @Param("teacherCode") String teacherCode);

}
