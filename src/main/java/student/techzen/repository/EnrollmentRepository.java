package student.techzen.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import student.techzen.dto.enrollment.EnrollmentDetailProjection;
import student.techzen.dto.enrollment.EnrollmentSummaryProjection;
import student.techzen.entity.Enrollment;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public interface EnrollmentRepository  extends JpaRepository<Enrollment, UUID> {

    @Query(value = """
        SELECT
            e.id AS enrollmentId,
            e.enrollment_date AS enrollmentDate,
            e.status AS status,

            s.student_code AS studentCode,
            p.full_name AS studentName,
            m.major_name AS majorName,

            c.class_code AS classCode,
            c.class_name AS className,

            sub.subject_code AS subjectCode,
            sub.subject_name AS subjectName,
            sub.credits AS credits,

            e.attendance_score AS attendanceScore,
            e.assignment_score AS assignmentScore,
            e.midterm_score AS midtermScore,
            e.final_exam_score AS finalExamScore,
            e.total_score AS totalScore,
            e.letter_grade AS letterGrade,
            e.grade_point AS gradePoint

        FROM enrollments e

        INNER JOIN students s
            ON e.student_id = s.person_id

        INNER JOIN people p
            ON s.person_id = p.id

        LEFT JOIN majors m
            ON s.major_id = m.id

        INNER JOIN classes c
            ON e.class_id = c.id

        INNER JOIN subjects sub
            ON c.subject_id = sub.id

        WHERE e.id = :enrollmentId
        """,
            nativeQuery = true)
    Optional<EnrollmentDetailProjection> findEnrollmentDetailNative(
            UUID enrollmentId
    );


    @Query(value = """
    SELECT
        e.id AS enrollmentId,

        s.student_code AS studentCode,

        p.full_name AS studentName,

        c.class_code AS classCode,

        sub.subject_name AS subjectName,

        e.status AS status,

        e.total_score AS totalScore,

        e.letter_grade AS letterGrade

    FROM enrollments e

    INNER JOIN students s
        ON e.student_id = s.person_id

    INNER JOIN people p
        ON s.person_id = p.id

    INNER JOIN classes c
        ON e.class_id = c.id

    INNER JOIN subjects sub
        ON c.subject_id = sub.id

    WHERE 1 = 1

      AND (
            :studentCode IS NULL
            OR LOWER(s.student_code)
                LIKE LOWER(CONCAT('%', :studentCode, '%'))
      )

      AND (
            :classCode IS NULL
            OR LOWER(c.class_code)
                LIKE LOWER(CONCAT('%', :classCode, '%'))
      )

      AND (
            :subjectName IS NULL
            OR LOWER(sub.subject_name)
                LIKE LOWER(CONCAT('%', :subjectName, '%'))
      )

      AND (
            :fromTotalScore IS NULL
            OR e.total_score >= :fromTotalScore
      )

      AND (
            :toTotalScore IS NULL
            OR e.total_score <= :toTotalScore
      )

    ORDER BY e.created_at DESC
    """,
            countQuery = """
    SELECT COUNT(*)

    FROM enrollments e

    INNER JOIN students s
        ON e.student_id = s.person_id

    INNER JOIN classes c
        ON e.class_id = c.id

    INNER JOIN subjects sub
        ON c.subject_id = sub.id

    WHERE 1 = 1

      AND (
            :studentCode IS NULL
            OR LOWER(s.student_code)
                LIKE LOWER(CONCAT('%', :studentCode, '%'))
      )

      AND (
            :classCode IS NULL
            OR LOWER(c.class_code)
                LIKE LOWER(CONCAT('%', :classCode, '%'))
      )

      AND (
            :subjectName IS NULL
            OR LOWER(sub.subject_name)
                LIKE LOWER(CONCAT('%', :subjectName, '%'))
      )

      AND (
            :fromTotalScore IS NULL
            OR e.total_score >= :fromTotalScore
      )

      AND (
            :toTotalScore IS NULL
            OR e.total_score <= :toTotalScore
      )
    """,
            nativeQuery = true)
    Page<EnrollmentSummaryProjection> findAllEnrollmentSummary(
            Pageable pageable,
            @Param("studentCode") String studentCode,
            @Param("classCode") String classCode,
            @Param("subjectName") String subjectName,
            @Param("fromTotalScore") BigDecimal fromTotalScore,
            @Param("toTotalScore") BigDecimal  toTotalScore
    );

    //Kiểm tra sinh viên đã đăng ký lớp này chưa
    boolean existsByStudentPersonIdAndClazzId(UUID studentId, UUID clazzId);

    //Đếm số lượng sinh viên hiện tại đã đăng ký vào lớp này
    long countByClazzId(UUID clazzId);
}
