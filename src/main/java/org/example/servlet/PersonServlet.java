package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.DTO.PersonDTO;
import org.example.Factory;
import org.example.annotations.Logging;
import org.example.controller.PersonController;
import org.example.mapper.PersonMapper;
import org.example.model.Person;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

@Logging
@WebServlet("/person")
public class PersonServlet extends HttpServlet {

    private PersonController personController;
    private final PersonMapper personMapper = PersonMapper.INSTANCE;

    public PersonServlet() {
        personController = Factory.getPersonController();
    }

    /**
     * Метод получения пользователя по id
     * Пример запроса: http://localhost:8080/person?id=1
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter printWriter = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        long id = Long.parseLong(req.getParameter("id"));

        Optional<Person> personOpt = personController.getPersonById(id);

        if (personOpt.isPresent()) {
            PersonDTO personDTO = personMapper.personToPersonDTO(personOpt.get());

            ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String jsonObject = objectWriter.writeValueAsString(personDTO);

            resp.setStatus(HttpServletResponse.SC_OK);
            printWriter.print(jsonObject);
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * Метод обновления данных пользователя
     * Ручка: http://localhost:8080/person
     * Пример тела запроса:
     * {
     *     "id": 2,
     *     "email": "newJsonEmail",
     *     "name": "newJsonName"
     * }
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException{
        ObjectMapper objectMapper = new ObjectMapper();

        try (BufferedReader reader = req.getReader()) {
            StringBuilder body = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                body.append(line);
            }

            String requestBody = body.toString();
            PersonDTO personDTO = objectMapper.readValue(requestBody, PersonDTO.class);

            long id = personDTO.getId();

            if (personDTO.getName() != null) {
                personController.editName(id, personDTO.getName());
            }
            if (personDTO.getEmail() != null) {
                personController.editEmail(id, personDTO.getEmail());
            }

            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (IOException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    /**
     * Метод создания нового пользователя
     * Ручка: http://localhost:8080/person
     * Пример тела запроса:
     * {
     *     "email": "jsonEmail",
     *     "name": "jsonName",
     *     "admin": false,
     *     "banned": false
     * }
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException{
        ObjectMapper objectMapper = new ObjectMapper();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try (BufferedReader reader = req.getReader()) {
            StringBuilder body = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                body.append(line);
            }

            String requestBody = body.toString();
            PersonDTO personDTO = objectMapper.readValue(requestBody, PersonDTO.class);

            Person person = personMapper.personDTOToPerson(personDTO);

            Person createdPerson = personController.create(person);

            PersonDTO createdPersonDTO = personMapper.personToPersonDTO(createdPerson);

            PrintWriter printWriter = resp.getWriter();
            ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

            String jsonObject = objectWriter.writeValueAsString(createdPersonDTO);

            resp.setStatus(HttpServletResponse.SC_CREATED);
            printWriter.print(jsonObject);
        } catch (IOException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    /**
     * Метод для удаления пользователя
     * Пример запроса: http://localhost:8080/person?id=1
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("id"));

        personController.removeById(id);
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    public void setPersonController(PersonController personController) {
        this.personController = personController;
    }
}
