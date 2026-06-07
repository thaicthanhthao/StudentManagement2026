package student.techzen.dto.major;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MajorResponse {
    UUID id;
    String majorName;;
    String majorCode;
    Instant createdAt;
    Instant updatedAt;
}
