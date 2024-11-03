package org.example.controller;

import org.example.DTO.HabitDTO;
import org.example.model.Habit;
import org.example.service.HabitService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.doNothing;

public class HabitControllerTest {

    private HabitService habitService = mock(HabitService.class);

    @Test
    void getHabitsByPersonIdTest() {
        Habit habit = new Habit("name", "descr", 2);
        habit.setId(1);
        Habit habit1 = new Habit("name1", "descr1", 3);
        habit1.setId(2);

        List<Habit> habitList = new ArrayList<>();
        habitList.add(habit);
        habitList.add(habit1);

        doReturn(habitList).when(habitService).getHabitsByPersonId(anyLong());

        HabitController habitController = new HabitController(habitService);

        ResponseEntity<List<HabitDTO>> habitsByPersonId = habitController.getHabitsByPersonId(1L);

        assertEquals(HttpStatus.OK, habitsByPersonId.getStatusCode());

        assertEquals(1, habitsByPersonId.getBody().get(0).getId());
        assertEquals(2, habitsByPersonId.getBody().get(1).getId());
    }

    @Test
    void getByIdTest() {
        Habit habit = new Habit("name", "descr", 2);
        habit.setId(1);

        doReturn(Optional.of(habit)).when(habitService).getById(anyLong());

        HabitController habitController = new HabitController(habitService);

        ResponseEntity<HabitDTO> byId = habitController.getById(1L);

        assertEquals(HttpStatus.OK, byId.getStatusCode());
        assertEquals(1, byId.getBody().getId());
    }

    @Test
    void updateTest() {
        HabitDTO habitDTO = new HabitDTO();
        habitDTO.setId(1);
        habitDTO.setName("name");
        habitDTO.setDescription("descr");
        habitDTO.setPersonId(1);

        doNothing().when(habitService).update(any());

        HabitController habitController = new HabitController(habitService);
        ResponseEntity<Void> update = habitController.update(habitDTO);

        assertEquals(HttpStatus.OK, update.getStatusCode());
    }

    @Test
    void createByPersonIdTest() {
        Habit habit = new Habit("name", "descr", 2);
        habit.setId(1);

        HabitDTO habitDTO = new HabitDTO();
        habitDTO.setId(1);
        habitDTO.setName("name");
        habitDTO.setDescription("descr");
        habitDTO.setPersonId(1);

        doReturn(habit).when(habitService).createByPersonId(anyLong(), any());

        HabitController habitController = new HabitController(habitService);
        ResponseEntity<HabitDTO> byPersonId = habitController.createByPersonId(1L, habitDTO);

        assertEquals(HttpStatus.CREATED, byPersonId.getStatusCode());
    }

    @Test
    void removeByIdTest() {
        doNothing().when(habitService).removeById(anyLong());

        HabitController habitController = new HabitController(habitService);

        ResponseEntity<Void> voidResponseEntity = habitController.removeById(1L);

        assertEquals(HttpStatus.NO_CONTENT, voidResponseEntity.getStatusCode());
    }

    @Test
    void markCompletionTest() {
        HabitDTO habitDTO = new HabitDTO();
        habitDTO.setId(1);
        habitDTO.setName("name");
        habitDTO.setDescription("descr");
        habitDTO.setPersonId(1);

        doReturn(true).when(habitService).markCompletion(any());

        HabitController habitController = new HabitController(habitService);
        ResponseEntity<Void> voidResponseEntity = habitController.markCompletion(habitDTO);

        assertEquals(HttpStatus.OK, voidResponseEntity.getStatusCode());
    }
}
