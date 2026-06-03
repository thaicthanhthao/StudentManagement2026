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
public class Student {
    @Id
    @Column(name = "person_id")
    UUID person_id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "person_id")
    Person person;

    @Column(name = "student_code")
    String studentCode;

    @Column(name = "enrollment_year")
    Integer enrollmentYear;

    @ManyToOne
    @JoinColumn(name = "major_id")
    Major major;

    @Column(name = "current_gpa", precision = 3, scale = 2)
    BigDecimal currentGpa;

    @Column(name = "created_at", insertable = false, updatable = false)
    Instant createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    Instant updatedAt;
}
