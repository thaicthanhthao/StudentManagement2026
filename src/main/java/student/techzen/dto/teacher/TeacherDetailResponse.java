package student.techzen.dto.teacher;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeacherDetailResponse {
    private UUID id;

    private String fullName;

    private String status;

    private String email;

    private LocalDate dob; // Ngày sinh

    private String gender;

    private String phone;

    private String address;

    private String teacherCode;

    private String specialization;
}
