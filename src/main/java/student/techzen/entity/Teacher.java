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
@Table(name = "teacher")
public class Teacher {
    @Id
    @Column(name = "person_id")
    UUID person_id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "person_id")
    Person person;

    String teacherCode;
    String specialization;

    @Column(name = "created_at", insertable = false, updatable = false)
    Instant createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    Instant updatedAt;
}
