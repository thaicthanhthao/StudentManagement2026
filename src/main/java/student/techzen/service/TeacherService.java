package student.techzen.service;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import student.techzen.dto.PageResponse;
import student.techzen.dto.teacher.*;
import student.techzen.entity.Person;
import student.techzen.entity.Teacher;
import student.techzen.entity.User;
import student.techzen.repository.PersonRepository;
import student.techzen.repository.TeacherRepository;
import student.techzen.repository.UserRepository;
import student.techzen.mapper.Teachermapper;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class TeacherService {
    TeacherRepository teacherRepository;
    UserRepository userRepository;
    PersonRepository personRepository;
    Teachermapper teacherMapper;

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

    public TeacherDetailResponse getById(UUID id){
        Teacher teacher = teacherRepository.findDetailById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Teacher not found: " + id));

        return teacherMapper.mapToTeacherDetailResponse(teacher);
    }

    @Transactional
    public TeacherDetailResponse updateTeacher(UUID id, TeacherUpdateRequest updateRequest) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Teacher not found: " + id));

        Person person = teacher.getPerson();
        User user = person.getUser();

        person.setFullName(updateRequest.getFullName());
        person.setPhone(updateRequest.getPhone());
        person.setAddress(updateRequest.getAddress());
        user.setEmail(updateRequest.getEmail());
        teacher.setTeacherCode(updateRequest.getTeacherCode());
        teacher.setSpecialization(updateRequest.getSpecialization());

        userRepository.save(user);
        personRepository.save(person);
        teacherRepository.save(teacher);

        return teacherMapper.mapToTeacherDetailResponse(teacher);
    }

    public void  deleteTeacher(UUID id){
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Teacher not found: " + id));

        Person person = teacher.getPerson();
        User user = person.getUser();


        teacherRepository.delete(teacher);
        personRepository.delete(person);
        userRepository.delete(user);

    }
}
