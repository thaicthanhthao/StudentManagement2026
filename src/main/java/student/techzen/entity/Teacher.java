package student.techzen.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "teachers")
public class Teacher {
    @Id
    @Column(name = "person_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID person_id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "person_id")
    Person person;

    @Column(name = "teacher_code", unique = true)
    String teacherCode;

    String specialization;

    @Column(name = "created_at", insertable = false, updatable = false)
    Instant createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    Instant updatedAt;
}
