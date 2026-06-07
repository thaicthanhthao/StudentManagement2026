package student.techzen.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import student.techzen.entity.Student;

import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {
    @Query("""
                SELECT s
                FROM Student s
                WHERE LOWER(s.studentCode)
                      LIKE LOWER(CONCAT('%', :keyword, '%'))
            """)
    Page<Student> searchByStudentCode(
            @Param("keyword") String keyword,
            Pageable pageable
    );

    boolean existsByStudentCode(String studentCode);
    boolean existsByPersonId(UUID id);

}
