package student.techzen.dto.teacher;

import java.time.LocalDate;
import java.util.UUID;

public interface TeacherProjectorNative {
    UUID getId();
    String getFullName();
    String getStatus();
    String getEmail();
    LocalDate getDob();
    String getGender();
    String getPhone();
    String getAddress();
    String getTeacherCode();
    String getSpecialization();
}
