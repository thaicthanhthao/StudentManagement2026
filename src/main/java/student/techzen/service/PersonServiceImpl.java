package student.techzen.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import student.techzen.dto.PageResponse;
import student.techzen.dto.person.PersonCreateRequest;
import student.techzen.dto.person.PersonDetailResponse;
import student.techzen.dto.person.PersonListItemResponse;
import student.techzen.dto.person.PersonUpdateRequest;
import student.techzen.entity.Person;
import student.techzen.entity.User;
import student.techzen.mapper.PersonMapper;
import student.techzen.repository.PersonRepository;
import student.techzen.repository.StudentRepository;
import student.techzen.repository.TeacherRepository;
import student.techzen.repository.UserRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepo;
    private final UserRepository userRepo;
    private final PersonMapper personMapper;
    private final StudentRepository studentRepo;
    private final TeacherRepository teacherRepo;

    @Override
    public PageResponse<PersonListItemResponse> getAllPersons(Pageable pageable) {

        Page<Person> pageData = personRepo.findAll(pageable);

        return new PageResponse<>(pageData.map(personMapper::toListItemResponse));
    }

    @Override
    public PersonDetailResponse getPersonById(UUID id) {

        Person person = personRepo.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Person not found"
                        ));

        return personMapper.toDetailResponse(person);
    }

    @Override
    public PersonDetailResponse createPerson(PersonCreateRequest request) {

        User user = userRepo.findById(request.getUserId())
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User not found"
                        ));

        Person person = Person.builder()
                .fullName(request.getFullName())
                .dob(request.getDob())
                .gender(request.getGender())
                .phone(request.getPhone())
                .address(request.getAddress())
                .user(user)
                .build();

        person = personRepo.save(person);

        return personMapper.toDetailResponse(person);
    }

    @Override
    public PersonDetailResponse updatePerson(UUID id, PersonUpdateRequest request) {

        Person person = personRepo.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Person not found"
                        ));

        if (request.getUserId() != null) {

            User user = userRepo.findById(request.getUserId())
                    .orElseThrow(() ->
                            new ResponseStatusException(
                                    HttpStatus.NOT_FOUND,
                                    "User not found"
                            ));

            person.setUser(user);
        }

        person.setFullName(request.getFullName());

        person.setDob(request.getDob());

        person.setGender(request.getGender());

        person.setPhone(request.getPhone());

        person.setAddress(request.getAddress());

        person = personRepo.save(person);

        return personMapper.toDetailResponse(person);
    }

    @Override
    public PageResponse<PersonListItemResponse> searchPersons(String keyword, Pageable pageable) {

        Page<Person> pageData = personRepo.searchByFullName(keyword, pageable);

        return new PageResponse<>(pageData.map(personMapper::toListItemResponse));
    }

    @Override
    public void deletePerson(UUID id) {

        Person person = personRepo.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Person not found"
                        ));

        if (studentRepo.existsByPersonId(id)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Person is assigned to a student"
            );
        }

        if (teacherRepo.existsByPersonId(id)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Person is assigned to a teacher"
            );
        }

        personRepo.delete(person);
    }
}
