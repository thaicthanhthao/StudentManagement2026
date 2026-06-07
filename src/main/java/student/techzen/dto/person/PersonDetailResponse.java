package student.techzen.dto.person;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PersonDetailResponse {
    UUID id;
    String fullName;
    LocalDate dob;
    String gender;
    String phone;
    String address;

    UUID userId;
    String username;
    String email;

    Instant createdAt;
    Instant updatedAt;
}
