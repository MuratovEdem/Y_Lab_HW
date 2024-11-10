package org.example.controller;

import org.example.model.Person;
import org.example.service.PersonService;
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

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doNothing;

@WebMvcTest(PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    @DisplayName("Тест на получение списка всех Person")
    @Test
    void getPersonsTest() throws Exception {
        Person person = new Person("jsonEmail", "pass", "jsonName");
        person.setId(1);
        Person person1 = new Person("jsonEmail2", "pass2", "jsonName2");
        person1.setId(2);

        List<Person> personList = new ArrayList<>();
        personList.add(person);
        personList.add(person1);

        doReturn(personList).when(personService).getPersons();

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/persons"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(person.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].password").value(person.getPassword()));
    }

    @DisplayName("Тест на успешное получение Person по id")
    @Test
    void getPersonByIdAllOkTest() throws Exception {
        Person person = new Person("jsonEmail", "pass", "jsonName");
        person.setId(1);

        doReturn(Optional.of(person)).when(personService).getById(anyLong());

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/persons/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(person.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value(person.getPassword()));
    }

    @DisplayName("Тест на некорретный id для получения Person по id")
    @Test
    void getPersonByIdNotFoundTest() throws Exception {
        doReturn(Optional.empty()).when(personService).getById(anyLong());

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/persons/2"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @DisplayName("Тест на изменение данных")
    @Test
    void editPersonDataTest() throws Exception {
        doNothing().when(personService).editEmail(anyLong(), any());
        doNothing().when(personService).editName(anyLong(), any());

        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1, \"name\": \"newname\", \"email\":\"newEmail.com\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @DisplayName("Тест на успешное создание нового Person")
    @Test
    void createValidDataTest() throws Exception {
        Person person = new Person("email", "pass", "name");
        person.setId(1);

        doReturn(person).when(personService).create(any());

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1, \"name\": \"name\", \"password\": \"pass\", \"email\":\"email\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @DisplayName("Тест на некорректные входные данные при создании Person")
    @Test
    void createInvalidDataTest() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1, \"name\": null, \"password\": \"pass\", \"email\":\"email\"}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @DisplayName("Тест на удаление Person по id")
    @Test
    void removeTest() throws Exception {
        doNothing().when(personService).removeById(anyLong());

        this.mockMvc
                .perform(MockMvcRequestBuilders.delete("/persons/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
