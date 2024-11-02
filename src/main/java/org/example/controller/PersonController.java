package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.DTO.PersonDTO;
import org.example.annotations.Logging;
import org.example.mapper.PersonMapper;
import org.example.model.Person;
import org.example.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Logging
@RestController
@RequestMapping("/persons")
@Tag(name = "Persons", description = "API для работы с пользователями")
public class PersonController {

    private final PersonService personService;
    private final PersonMapper personMapper = PersonMapper.INSTANCE;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @Operation(
            summary = "Get Persons",
            description = "Получение списка пользователей"
    )
    @GetMapping
    public ResponseEntity<List<PersonDTO>> getPersons() {
        return ResponseEntity.ok(personMapper.personsToPersonDTOList(personService.getPersons()));
    }

    @Operation(
            summary = "Get Person",
            description = "Получение пользователя по идентификатору"
    )
    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> getPersonById(@PathVariable(name = "id") @Parameter(description = "Идентификатор пользователя") Long id) {
        Optional<Person> personOpt = personService.getById(id);

        if (personOpt.isPresent()) {
            Person person = personOpt.get();
            return ResponseEntity.status(HttpStatus.OK).body(personMapper.personToPersonDTO(person));
        }

        return ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Edit Person",
            description = "Изменение данных пользователя"
    )
    @PutMapping
    public ResponseEntity<Void> editPersonData(@RequestBody @Parameter(description = "Данные для изменения") PersonDTO personDTO) {
        personService.editName(personDTO.getId(), personDTO.getName());
        personService.editEmail(personDTO.getId(), personDTO.getEmail());

        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Create Person",
            description = "Создание пользователя"
    )
    @PostMapping
    public ResponseEntity<PersonDTO> create(@RequestBody @Parameter(description = "Данные пользователя") PersonDTO personDTO) {
        Person createdPerson = personService.create(personMapper.personDTOToPerson(personDTO));
        PersonDTO createdPersonDTO = personMapper.personToPersonDTO(createdPerson);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdPersonDTO);
    }

    @Operation(
            summary = "Delete Person",
            description = "Удаление пользователя по идентификатору"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeById(@PathVariable(name = "id") @Parameter(description = "Идентификатор пользователя") Long id) {
        personService.removeById(id);
        return ResponseEntity.noContent().build();
    }
}
