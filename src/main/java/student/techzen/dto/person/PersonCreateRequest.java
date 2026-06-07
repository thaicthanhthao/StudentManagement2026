package student.techzen.dto.person;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PersonCreateRequest {
    String fullName;

    LocalDate dob;

    String gender;

    String phone;

    String address;

    UUID userId;
}
