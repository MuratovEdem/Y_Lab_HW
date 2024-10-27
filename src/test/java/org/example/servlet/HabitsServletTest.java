package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.controller.HabitController;
import org.example.model.Habit;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class HabitsServletTest {

    private HabitController habitController = mock(HabitController.class);

    @Test
    void doGetTest() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        Habit habit = new Habit("name", "descr", 2);
        habit.setId(1);
        Habit habit1 = new Habit("name1", "descr1", 3);
        habit1.setId(2);

        List<Habit> habitList = new ArrayList<>();
        habitList.add(habit);
        habitList.add(habit1);

        String expectedId = "1";

        when(request.getParameter("person_id")).thenReturn(expectedId);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        doReturn(habitList).when(habitController).getHabitsByPersonId(Long.parseLong(expectedId));

        HabitsServlet habitsServlet = new HabitsServlet();
        habitsServlet.setHabitController(habitController);

        habitsServlet.doGet(request, response);

        assertTrue(stringWriter.toString().contains("\"id\" : 1,"));
        assertTrue(stringWriter.toString().contains("\"id\" : 2,"));
    }
}
