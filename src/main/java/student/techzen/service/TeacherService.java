package student.techzen.service;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import student.techzen.dto.PageResponse;
import student.techzen.dto.teacher.TeacherCreateRequest;
import student.techzen.dto.teacher.TeacherDetailResponse;
import student.techzen.dto.teacher.TeacherProjectorNative;
import student.techzen.dto.teacher.TeacherReponse;
import student.techzen.entity.Person;
import student.techzen.entity.Teacher;
import student.techzen.entity.User;
import student.techzen.repositoty.PersonRepository;
import student.techzen.repositoty.TeacherRepository;
import student.techzen.repositoty.UserRepository;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class TeacherService {
    TeacherRepository teacherRepository;
    UserRepository userRepository;
    PersonRepository personRepository;

    @Transactional
    public TeacherReponse createTeacher(TeacherCreateRequest request){

        User user = User.builder()
                .username(request.getUsername())
                .passwordHash(request.getPassword())
                .role(request.getRole())
                .email(request.getEmail())
                .status(request.getStatus())
                .build();

        user = userRepository.save(user);

        Person person  = Person.builder()
                .user(user)
                .fullName(request.getFullName())
                .dob(request.getDob())
                .gender(request.getGender())
                .phone(request.getPhone())
                .address(request.getAddress())
                .build();
        person = personRepository.save(person);

        Teacher teacher = Teacher.builder()
                .person(person)
                .teacherCode(request.getTeacherCode())
                .specialization(request.getSpecialization())
                .build();
        teacher = teacherRepository.save(teacher);

        return TeacherReponse.builder()
                .person_id(teacher.getPerson_id())
                .teacherCode(teacher.getTeacherCode())
                .specialization(teacher.getSpecialization())
                .build();

    }

    public PageResponse<TeacherDetailResponse> getByAll(Pageable pageable,
                                                       String fullName,
                                                       String teacherCode
    ){
        Page<TeacherProjectorNative> projectorPage = teacherRepository.getByAllNative(pageable, fullName, teacherCode);

        Page<TeacherDetailResponse> responses = projectorPage.map(data ->
                TeacherDetailResponse.builder()
                        .id(data.getId())
                        .fullName(data.getFullName())
                        .status(data.getStatus())
                        .email(data.getEmail())
                        .dob(data.getDob())
                        .gender(data.getGender())
                        .phone(data.getPhone())
                        .address(data.getAddress())
                        .teacherCode(data.getTeacherCode())
                        .specialization(data.getSpecialization())
                        .build()
        );

        return new PageResponse<>(responses);
    }
}
