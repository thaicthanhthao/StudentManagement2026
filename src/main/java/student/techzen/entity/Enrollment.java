package student.techzen.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;

import java.time.Instant;
import java.util.UUID;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "students")
public class Enrollment {
    @Id
    UUID enrollment_id;

    @ManyToOne
    @JoinColumn(name = "person_id")
    Person person;

    @ManyToOne
    @JoinColumn(name = "class_id")
    Class class_id;

    @Column(name = "enrollment_date", insertable = false, updatable = false)
    Instant enrollmentDate;

    String status;

    @Column(name = "attendance_score", precision = 5, scale = 2)
    BigDecimal attendanceScore;

    @Column(name = "assignment_score", precision = 5, scale = 2)
    BigDecimal assignmentScore;

    @Column(name = "midterm_score", precision = 5, scale = 2)
    BigDecimal midtermScore;

    @Column(name = "final_exam_score", precision = 5, scale = 2)
    BigDecimal finalExamScore;

    @Column(name = "total_score", precision = 5, scale = 2)
    BigDecimal totalScore;

    String letterGrade;

    @Column(name = "created_at", insertable = false, updatable = false)
    Instant createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    Instant updatedAt;
}

