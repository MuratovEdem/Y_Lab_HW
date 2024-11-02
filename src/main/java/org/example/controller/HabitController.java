package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.DTO.HabitDTO;
import org.example.annotations.Logging;
import org.example.mapper.HabitMapper;
import org.example.model.Habit;
import org.example.service.HabitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Logging
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
            description = "Получение списка привычек по идентификатору пользователя"
    )
    @GetMapping("/person/{person_id}")
    public ResponseEntity<List<HabitDTO>> getHabitsByPersonId(@PathVariable(name = "person_id") @Parameter(description = "Идентификатор пользователя") Long personId) {
        return ResponseEntity.ok(habitMapper.habitsToHabitDTOList(habitService.getHabitsByPersonId(personId)));
    }

    @Operation (
            summary = "Get habit",
            description = "Получение привычки по идентификатору"
    )
    @GetMapping("/{id}")
    public ResponseEntity<HabitDTO> getById(@PathVariable(name = "id") @Parameter(description = "Идентификатор привычки") Long id) {
        Optional<Habit> optionalHabit = habitService.getById(id);

        if (optionalHabit.isPresent()) {
            Habit habit = optionalHabit.get();
            return ResponseEntity.status(HttpStatus.OK).body(habitMapper.habitToHabitDTO(habit));
        }

        return ResponseEntity.notFound().build();
    }

    @Operation (
            summary = "Edit habit",
            description = "Изменение данных привычки"
    )
    @PutMapping
    public ResponseEntity<Void> update(@RequestBody @Parameter(description = "Привычка с измененными данными") HabitDTO habitDTO) {
        habitService.update(habitMapper.habitDTOToHabit(habitDTO));

        return ResponseEntity.ok().build();
    }

    @Operation (
            summary = "Create habit",
            description = "Создание привычки по идентификатору пользователя"
    )
    @PostMapping("/person/{person_id}")
    public ResponseEntity<HabitDTO> createByPersonId(@PathVariable(name = "person_id") @Parameter(description = "Идентификатор пользователя") Long personId,
                                                     @RequestBody @Parameter(description = "Данные привычки для создания") HabitDTO habitDTO) {
        Habit habit = habitMapper.habitDTOToHabit(habitDTO);
        HabitDTO createdHabit = habitMapper.habitToHabitDTO(habitService.createByPersonId(personId, habit));

        return ResponseEntity.status(HttpStatus.CREATED).body(createdHabit);
    }

    @Operation (
            summary = "Remove habit",
            description = "Удаление привычки по идентификатору"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeById(@PathVariable(name = "id") @Parameter(description = "Идентификатор привычки") Long id) {
        habitService.removeById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation (
            summary = "MarkCompletion habit",
            description = "Отметить выполнение привычки"
    )
    @PutMapping("/mark_completion")
    public ResponseEntity<Void> markCompletion(@RequestBody @Parameter(description = "Данные привычки") HabitDTO habitDTO) {
        Habit habit = habitMapper.habitDTOToHabit(habitDTO);
        habitService.markCompletion(habit);

        return ResponseEntity.ok().build();
    }
}

