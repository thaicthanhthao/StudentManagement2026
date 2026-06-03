package student.techzen.dto.teacher;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeacherCreateRequest {

    // === 1. THÔNG TIN ĐĂNG NHẬP (Bảng users) ===
    @NotBlank(message = "Username không được để trống")
    @Size(min = 4, max = 50, message = "Username phải từ 4 đến 50 ký tự")
    private String username;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 6, message = "Mật khẩu phải từ 6 ký tự trở lên")
    private String password;

    @NotBlank(message = "role không được để trống")
    private String role;

    @NotBlank(message = "status không được để trống")
    private String status;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    private String email;


    // === 2. THÔNG TIN CÁ NHÂN (Bảng people) ===
    @NotBlank(message = "Họ và tên không được để trống")
    private String fullName;

    private LocalDate dob; // Ngày sinh

    @NotBlank(message = "Giới tính không được để trống")
    private String gender;

    @NotBlank(message = "Số điện thoại không được để trống")
    private String phone;

    private String address;


    // === 3. THÔNG TIN CHUYÊN MÔN GIẢNG VIÊN (Bảng teachers) ===
    @NotBlank(message = "Mã giảng viên không được để trống")
    private String teacherCode;

    private String specialization; // Chuyên ngành giảng dạy (ví dụ: CNTT, Kinh tế...)

}
