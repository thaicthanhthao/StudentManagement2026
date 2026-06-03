package student.techzen.dto.teacher;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import student.techzen.entity.Person;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeacherReponse {

    UUID person_id;

    String teacherCode;

    String specialization;

}
