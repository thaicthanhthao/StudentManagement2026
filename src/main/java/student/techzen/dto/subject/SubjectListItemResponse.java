package student.techzen.dto.subject;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@FieldDefaults (level = AccessLevel.PRIVATE)
@Builder
public class SubjectListItemResponse {
    UUID id;
    String subjectCode;
    String subjectName;
}
