package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.controller.PersonController;
import org.example.model.Person;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class PersonsServletTest {

    private PersonController personController = mock(PersonController.class);

    @Test
    void doGetTest() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        Person person = new Person("jsonEmail", "pass", "jsonName");
        person.setId(1);
        Person person1 = new Person("jsonEmail2", "pass2", "jsonName2");
        person1.setId(2);

        List<Person> personList = new ArrayList<>();
        personList.add(person);
        personList.add(person1);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        doReturn(personList).when(personController).getPersons();

        PersonsServlet personsServlet = new PersonsServlet();
        personsServlet.setPersonController(personController);

        personsServlet.doGet(request, response);

        assertTrue(stringWriter.toString().contains("\"id\" : 1,"));
        assertTrue(stringWriter.toString().contains("\"id\" : 2,"));
    }
}
