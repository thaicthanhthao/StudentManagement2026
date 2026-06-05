package student.techzen.dto.subject;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubjectUpdateRequest {
    String subjectCode;
    String subjectName;
    Integer credits;
}
