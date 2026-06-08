package student.techzen.dto.Subject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubjectResponseInEnrollment {

    private String subjectCode;

    private String subjectName;

    private Integer credits;

}
