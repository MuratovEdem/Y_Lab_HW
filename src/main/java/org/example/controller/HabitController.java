package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.example.DTO.HabitDTO;
import org.example.mapper.HabitMapper;
import org.example.model.Habit;
import org.example.service.HabitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;
import java.util.Optional;

@Validated
@RestController
@RequestMapping("/habits")
@Tag(name = "Habits", description = "API для работы с привычками")
public class HabitController {

    private final HabitMapper habitMapper = HabitMapper.INSTANCE.INSTANCE;
    private final HabitService habitService;

    public HabitController(HabitService habitService) {
        this.habitService = habitService;
    }

    @Operation (
            summary = "Get Person's habits",
            description = "Получение списка привычек по идентификатору пользователя" +
                    "Пример запроса: http://localhost:8080/habits/person/2"
    )
    @GetMapping("/person/{person_id}")
    public ResponseEntity<List<HabitDTO>> getHabitsByPersonId(@PathVariable(name = "person_id") @Min(1) @Parameter(description = "Идентификатор пользователя") Long personId) {
        return ResponseEntity.ok(habitMapper.habitsToHabitDTOList(habitService.getHabitsByPersonId(personId)));
    }

    @Operation (
            summary = "Get habit",
            description = "Получение привычки по идентификатору" +
                    "Пример запроса: http://localhost:8080/habits/1"
    )
    @GetMapping("/{id}")
    public ResponseEntity<HabitDTO> getById(@PathVariable(name = "id") @Min(1) @Parameter(description = "Идентификатор привычки") Long id) {
        Optional<Habit> optionalHabit = habitService.getById(id);

        if (optionalHabit.isPresent()) {
            Habit habit = optionalHabit.get();
            return ResponseEntity.status(HttpStatus.OK).body(habitMapper.habitToHabitDTO(habit));
        }

        return ResponseEntity.notFound().build();
    }

    @Operation (
            summary = "Edit habit",
            description = "Изменение данных привычки" +
                    "Пример тела запроса:" +
                    "{\n" +
                    "     \"id\": 2\n" +
                    "     \"name\": \"jsonHabit\",\n" +
                    "     \"description\": \"jsonDescription\",\n" +
                    "     \"executionFrequency\": 3,\n" +
                    "     \"numberExecutions\": 0,\n" +
                    "     \"currentStreak\": 0,\n" +
                    "     \"personId\": 3\n" +
                    "}"
    )
    @PutMapping
    public ResponseEntity<Void> update(@Valid @RequestBody @Parameter(description = "Привычка с измененными данными") HabitDTO habitDTO) {
        habitService.update(habitMapper.habitDTOToHabit(habitDTO));

        return ResponseEntity.ok().build();
    }

    @Operation (
            summary = "Create habit",
            description = "Создание привычки по идентификатору пользователя" +
                    "Пример тела запроса:" +
                    "{\n" +
                    "     \"id\": 2\n" +
                    "     \"name\": \"jsonHabit\",\n" +
                    "     \"description\": \"jsonDescription\",\n" +
                    "     \"executionFrequency\": 3,\n" +
                    "     \"numberExecutions\": 0,\n" +
                    "     \"currentStreak\": 0,\n" +
                    "     \"personId\": 3\n" +
                    "}"
    )
    @PostMapping("/person/{person_id}")
    public ResponseEntity<HabitDTO> createByPersonId(@PathVariable(name = "person_id") @Min(1) @Parameter(description = "Идентификатор пользователя") Long personId,
                                                     @Valid @RequestBody @Parameter(description = "Данные привычки для создания") HabitDTO habitDTO) {
        Habit habit = habitMapper.habitDTOToHabit(habitDTO);
        HabitDTO createdHabit = habitMapper.habitToHabitDTO(habitService.createByPersonId(personId, habit));

        return ResponseEntity.status(HttpStatus.CREATED).body(createdHabit);
    }

    @Operation (
            summary = "Remove habit",
            description = "Удаление привычки по идентификатору" +
                    "Пример запроса: http://localhost:8080/habits/1"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeById(@PathVariable(name = "id") @Min(1) @Parameter(description = "Идентификатор привычки") Long id) {
        habitService.removeById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation (
            summary = "MarkCompletion habit",
            description = "Отметить выполнение привычки" +
                    "\"Пример тела запроса:\"" +
                    "{\n" +
                    "     \"id\": 2\n" +
                    "     \"name\": \"jsonHabit\",\n" +
                    "     \"description\": \"jsonDescription\",\n" +
                    "     \"executionFrequency\": 3,\n" +
                    "     \"numberExecutions\": 0,\n" +
                    "     \"currentStreak\": 0,\n" +
                    "     \"personId\": 3\n" +
                    "}"
    )
    @PutMapping("/mark_completion")
    public ResponseEntity<Void> markCompletion(@Valid @RequestBody @Parameter(description = "Данные привычки") HabitDTO habitDTO) {
        Habit habit = habitMapper.habitDTOToHabit(habitDTO);

        if (habitService.markCompletion(habit)) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
    }
}

