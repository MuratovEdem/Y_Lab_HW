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

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.NoSuchElementException;

@Logging
@WebServlet("/persons")
public class PersonsServlet extends HttpServlet {

    private PersonController personController;
    private final PersonMapper personMapper = PersonMapper.INSTANCE;

    public PersonsServlet() {
        personController = Factory.getPersonController();
    }

    /**
     * Метод получения всех пользователей
     * http://localhost:8080/persons
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter printWriter = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            List<PersonDTO> personDTOList = personMapper.personsToPersonDTOList(personController.getPersons());

            ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String jsonObject = objectWriter.writeValueAsString(personDTOList);

            resp.setStatus(HttpServletResponse.SC_OK);
            printWriter.print(jsonObject);
        } catch (NoSuchElementException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    public void setPersonController(PersonController personController) {
        this.personController = personController;
    }
}
