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
    @Column(name = "id")
    private UUID id;

    @Column(name = "full_name")
    private String fullName;

    private LocalDate dob;

    private String gender;

    private String phone;

    private String address;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "created_at", insertable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private Instant updatedAt;

}
