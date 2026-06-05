package student.techzen.service;

import org.springframework.data.domain.Pageable;
import student.techzen.dto.PageResponse;
import student.techzen.dto.person.*;

import java.util.UUID;

public interface PersonService {

    PageResponse<PersonListItemResponse> getAllPersons(Pageable pageable);

    PersonDetailResponse getPersonById(UUID id);

    PersonDetailResponse createPerson(PersonCreateRequest request);

    PersonDetailResponse updatePerson(UUID id, PersonUpdateRequest request);

    void deletePerson(UUID id);

    PageResponse<PersonListItemResponse> searchPersons(String keyword, Pageable pageable);
}