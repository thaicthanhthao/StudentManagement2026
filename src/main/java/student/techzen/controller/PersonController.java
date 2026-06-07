package student.techzen.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import student.techzen.dto.ApiResponse;
import student.techzen.dto.PageResponse;
import student.techzen.dto.person.*;
import student.techzen.service.PersonService;

import java.util.UUID;

@RestController
@RequestMapping("/api/persons")
@RequiredArgsConstructor
public class PersonController {

        private final PersonService personService;

    @GetMapping
    public ApiResponse<PageResponse<PersonListItemResponse>> getAllPersons(Pageable pageable) {

        return ApiResponse.<PageResponse<PersonListItemResponse>>builder()
                .success(true)
                .data(personService.getAllPersons(pageable))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<PersonDetailResponse> getPersonById(@PathVariable UUID id) {

        return ApiResponse.<PersonDetailResponse>builder()
                .success(true)
                .data(personService.getPersonById(id))
                .build();
    }

    @PostMapping
    public ApiResponse<PersonDetailResponse> createPerson(@Valid @RequestBody PersonCreateRequest request) {

        return ApiResponse.<PersonDetailResponse>builder()
                .success(true)
                .data(personService.createPerson(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<PersonDetailResponse> updatePerson(@PathVariable UUID id,
                                                          @Valid @RequestBody PersonUpdateRequest request) {

        return ApiResponse.<PersonDetailResponse>builder()
                .success(true)
                .data(personService.updatePerson(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deletePerson(@PathVariable UUID id) {

        personService.deletePerson(id);

        return ApiResponse.<String>builder()
                .success(true)
                .data("Delete successfully")
                .build();
    }

    @GetMapping("/search")
    public ApiResponse<PageResponse<PersonListItemResponse>> searchPersons(@RequestParam String keyword, Pageable pageable) {

        return ApiResponse.<PageResponse<PersonListItemResponse >>builder()
                .success(true)
                .data(personService.searchPersons(keyword, pageable))
                .build();
    }

}