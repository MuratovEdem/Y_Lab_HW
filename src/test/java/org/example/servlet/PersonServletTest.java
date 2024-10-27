package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.controller.PersonController;
import org.example.model.Person;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.StringReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class PersonServletTest {

    private PersonController personController = mock(PersonController.class);

    @Test
    void doGetTest() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        String expectedId = "1";

        when(request.getParameter("id")).thenReturn(expectedId);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        Person person = new Person("jsonEmail", "pass", "jsonName");
        person.setId(Long.parseLong(expectedId));
        Optional<Person> personOptional = Optional.of(person);

        doReturn(personOptional).when(personController).getPersonById(Long.parseLong(expectedId));

        PersonServlet personServlet = new PersonServlet();
        personServlet.setPersonController(personController);

        personServlet.doGet(request, response);

        assertTrue(stringWriter.toString().contains("\"id\" : 1,"));
    }

    @Test
    void doPutTest() throws ServletException, IOException {
        HttpServletRequest request = spy(HttpServletRequest.class);
        HttpServletResponse response = spy(HttpServletResponse.class);

        String requestBody = "{\n" +
                "  \"id\" : 1,\n" +
                "  \"email\" : \"jsonEmail\",\n" +
                "  \"password\" : \"pass\",\n" +
                "  \"banned\" : false,\n" +
                "  \"admin\" : false\n" +
                "}";

        PersonServlet personServlet = new PersonServlet();
        personServlet.setPersonController(personController);

        try (BufferedReader bufferedReader = new BufferedReader(new StringReader(requestBody))) {
            when(request.getReader()).thenReturn(bufferedReader);

            doNothing().when(personController).editEmail(anyLong(), any());

            personServlet.doPut(request, response);

            verify(personController, Mockito.times(1)).editEmail(anyLong(), any());
            verify(personController, Mockito.never()).editName(anyLong(), any());
        }
    }

    @Test
    void doPostTest() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        String requestBody = "{\n" +
                "  \"id\" : 1,\n" +
                "  \"email\" : \"jsonEmail\",\n" +
                "  \"name\" : \"jsonName\",\n" +
                "  \"password\" : \"pass\",\n" +
                "  \"banned\" : false,\n" +
                "  \"admin\" : false\n" +
                "}";

        Person person = new Person("jsonEmail", "pass", "jsonName");
        person.setId(1);

        try (BufferedReader bufferedReader = new BufferedReader(new StringReader(requestBody))) {
            when(request.getReader()).thenReturn(bufferedReader);
//            when(request.getParameter("username")).thenReturn("me");
//            when(request.getParameter("password")).thenReturn("secret");

            StringWriter stringWriter = new StringWriter();
            PrintWriter writer = new PrintWriter(stringWriter);
            when(response.getWriter()).thenReturn(writer);

            PersonServlet personServlet = new PersonServlet();
            personServlet.setPersonController(personController);

//            when(personController.create(person)).thenReturn(person);
            doReturn(person).when(personController).create(Mockito.any());
            personServlet.doPost(request, response);

//            assertEquals("application/json", response.getContentType());
//        verify(request, atLeast(1)).getParameter("username");
//            writer.append(requestBody);

            assertTrue(stringWriter.toString().contains("\"id\" : 1,"));
        }
    }

    @Test
    void doDeleteTest() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        String expectedId = "1";

        when(request.getParameter("id")).thenReturn(expectedId);

        PersonServlet personServlet = new PersonServlet();
        personServlet.setPersonController(personController);

        doNothing().when(personController).removeById(Long.parseLong(expectedId));

        personServlet.doDelete(request, response);

        verify(personController, Mockito.times(1)).removeById(Long.parseLong(expectedId));
    }
}
