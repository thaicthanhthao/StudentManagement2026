package student.techzen.dto.person;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PersonListItemResponse {
    UUID id;
    String fullName;
    String gender;
    String phone;
}

