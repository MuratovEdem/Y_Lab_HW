package org.example.controller;

import org.example.model.Habit;
import org.example.service.HabitService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doNothing;

@WebMvcTest(HabitController.class)
public class HabitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HabitService habitService;

    @DisplayName("Тест на получение списка Habit у Person по id")
    @Test
    void getHabitsByPersonIdTest() throws Exception {
        Habit habit = new Habit("name", "descr", 2);
        habit.setId(1);
        Habit habit1 = new Habit("name1", "descr1", 3);
        habit1.setId(2);

        List<Habit> habitList = new ArrayList<>();
        habitList.add(habit);
        habitList.add(habit1);

        doReturn(habitList).when(habitService).getHabitsByPersonId(anyLong());

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/habits/person/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @DisplayName("Тест на успешное получение Habit по id")
    @Test
    void getByIdAllOkTest() throws Exception {
        Habit habit = new Habit("name", "descr", 2);
        habit.setId(1);

        doReturn(Optional.of(habit)).when(habitService).getById(anyLong());

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/habits/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @DisplayName("Тест на некорретный id для получения Habit по id")
    @Test
    void getByIdNotFoundTest() throws Exception {
        doReturn(Optional.empty()).when(habitService).getById(anyLong());

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/habits/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @DisplayName("Тест на изменение данных")
    @Test
    void updateTest() throws Exception {
        doNothing().when(habitService).update(any());

        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/habits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1, \"name\": \"name\", \"description\":\"desc\"," +
                                "\"executionFrequency\":2, \"numberExecutions\":1, \"currentStreak\":1," +
                                "\"personId\":1}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @DisplayName("Тест на успешное создание новой Habit")
    @Test
    void createByPersonIdValidDataTest() throws Exception {
        Habit habit = new Habit("name", "descr", 2);
        habit.setId(1);

        doReturn(habit).when(habitService).createByPersonId(anyLong(), any());

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/habits/person/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1, \"name\": \"name\", \"description\":\"desc\"," +
                                "\"executionFrequency\":2, \"numberExecutions\":1, \"currentStreak\":1," +
                                "\"personId\":1}"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @DisplayName("Тест на некорректные входные данные при создании Habit")
    @Test
    void createByPersonIdInValidDataTest() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/habits/person/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1, \"name\": null, \"description\":\"desc\"," +
                                "\"executionFrequency\":2, \"numberExecutions\":1, \"currentStreak\":1," +
                                "\"personId\":1}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @DisplayName("Тест на удаление Habit по id")
    @Test
    void removeByIdTest() throws Exception {
        doNothing().when(habitService).removeById(anyLong());

        this.mockMvc
                .perform(MockMvcRequestBuilders.delete("/habits/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @DisplayName("Тест на успешную отметку о выполнении Habit")
    @Test
    void markCompletionTrueTest() throws Exception {
        doReturn(true).when(habitService).markCompletion(any());

        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/habits/mark_completion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1, \"name\": \"name\", \"description\":\"desc\"," +
                                "\"executionFrequency\":2, \"numberExecutions\":1, \"currentStreak\":1," +
                                "\"personId\":1}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @DisplayName("Тест на не успешную отметку о выполнении Habit")
    @Test
    void markCompletionFalseTest() throws Exception {
        doReturn(false).when(habitService).markCompletion(any());

        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/habits/mark_completion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1, \"name\": \"name\", \"description\":\"desc\"," +
                                "\"executionFrequency\":2, \"numberExecutions\":1, \"currentStreak\":1," +
                                "\"personId\":1}"))
                .andExpect(MockMvcResultMatchers.status().isNotModified());
    }
}
