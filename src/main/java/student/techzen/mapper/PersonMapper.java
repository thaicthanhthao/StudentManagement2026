package student.techzen.mapper;

import org.springframework.stereotype.Component;
import student.techzen.dto.person.PersonDetailResponse;
import student.techzen.dto.person.PersonListItemResponse;
import student.techzen.entity.Person;

@Component
public class PersonMapper {

    public PersonListItemResponse toListItemResponse(Person person) {

        return PersonListItemResponse.builder()
                .id(person.getId())
                .fullName(person.getFullName())
                .gender(person.getGender())
                .phone(person.getPhone())
                .build();
    }

    public PersonDetailResponse toDetailResponse(Person person) {

        return PersonDetailResponse.builder()
                .id(person.getId())
                .fullName(person.getFullName())
                .dob(person.getDob())
                .gender(person.getGender())
                .phone(person.getPhone())
                .address(person.getAddress())
                .userId(person.getUser().getId())
                .username(person.getUser().getUsername())
                .email(person.getUser().getEmail())
                .createdAt(person.getCreatedAt())
                .updatedAt(person.getUpdatedAt())
                .build();
    }
}