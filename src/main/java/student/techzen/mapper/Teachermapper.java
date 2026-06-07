package student.techzen.mapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import student.techzen.dto.teacher.TeacherDetailResponse;
import student.techzen.entity.Person;
import student.techzen.entity.Teacher;
import student.techzen.entity.User;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Teachermapper {

    public TeacherDetailResponse mapToTeacherDetailResponse(Teacher teacher){

        Person person = teacher.getPerson();
        User user = person.getUser();

        return TeacherDetailResponse.builder()
                .id(teacher.getPerson_id())
                .fullName(person.getFullName())
                .status(user.getStatus())
                .email(user.getEmail())
                .dob(person.getDob())
                .gender(person.getGender())
                .phone(person.getPhone())
                .address(person.getAddress())
                .teacherCode(teacher.getTeacherCode())
                .specialization(teacher.getSpecialization())
                .build();
    }
}
