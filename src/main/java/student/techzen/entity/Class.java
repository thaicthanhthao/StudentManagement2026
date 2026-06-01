package student.techzen.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "classes")
public class Class {
    @Id
    UUID id;

    @Column(name = "class_code", unique = true)
    String classCode;

    @Column(name = "class_name", nullable = false)
    String className;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    Subject subject;

    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "person_id", nullable = false)
    Teacher teacher;

    String semester;

    @Column(name = "academic_year")
    String academicYear;

    String room;

    @Column(name = "day_of_week")
    Integer dayOfWeek;

    @Column(name = "start_time")
    LocalTime startTime;

    @Column(name = "end_time")
    LocalTime endTime;

    @Column(name = "max_students")
    Integer maxStudents;

    String status;

    @Column(name = "created_at", insertable = false, updatable = false)
    Instant createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    Instant updatedAt;
}