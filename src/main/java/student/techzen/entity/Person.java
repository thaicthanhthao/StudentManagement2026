package student.techzen.entity;

import lombok.*;
import jakarta.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Builder
@Entity
@Table(name = "people")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    @Id
    UUID id;
    String full_name;
    LocalDate dob;
    String gender;
    String phone;
    String address;

    @OneToOne
    @JoinColumn(name = "user_id")
    User user;

    @Column(name = "created_at", insertable = false, updatable = false)
    Instant createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    Instant updatedAt;

}
