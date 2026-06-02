package student.techzen.entity;

import lombok.*;
import jakarta.persistence.*;
import org.springframework.web.bind.annotation.Mapping;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    UUID user_id;
    String userName;
    String email;
    String passwordHash;
    String role;
    String status;

    @Column(name = "created_at", insertable = false, updatable = false)
    Instant createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    Instant updatedAt;
}