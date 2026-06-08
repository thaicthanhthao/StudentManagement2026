package student.techzen.dto.major;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MajorDetailResponse {
    UUID id;
    String code;
    String name;
    Instant createdAt;
    Instant updatedAt;
}

