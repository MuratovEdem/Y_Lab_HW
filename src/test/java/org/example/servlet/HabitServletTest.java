//package org.example.servlet;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.example.controller.HabitController;
//import org.example.model.Habit;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//
//import java.io.*;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.*;
//
//public class HabitServletTest {
//
//    private HabitController habitController = mock(HabitController.class);
//
//    @Test
//    void doGetTest() throws IOException, ServletException {
//        HttpServletRequest request = mock(HttpServletRequest.class);
//        HttpServletResponse response = mock(HttpServletResponse.class);
//
//        String expectedId = "1";
//
//        when(request.getParameter("id")).thenReturn(expectedId);
//
//        StringWriter stringWriter = new StringWriter();
//        PrintWriter writer = new PrintWriter(stringWriter);
//        when(response.getWriter()).thenReturn(writer);
//
//        Habit habit = new Habit("name", "descr", 2);
//        habit.setId(Long.parseLong(expectedId));
//        Optional<Habit> habitOptional = Optional.of(habit);
//
//        doReturn(habitOptional).when(habitController).getById(Long.parseLong(expectedId));
//
//        HabitServlet habitServlet = new HabitServlet();
//        habitServlet.setHabitController(habitController);
//
//        habitServlet.doGet(request, response);
//
//        assertTrue(stringWriter.toString().contains("\"id\" : 1,"));
//    }
//
//    @Test
//    void doPostTest() throws ServletException, IOException {
//        HttpServletRequest request = mock(HttpServletRequest.class);
//        HttpServletResponse response = mock(HttpServletResponse.class);
//
//        String requestBody = "{\n" +
//                "   \"name\": \"jsonHabit\",\n" +
//                "   \"description\": \"jsonDescription\",\n" +
//                "   \"executionFrequency\": 3,\n" +
//                "   \"numberExecutions\": 0,\n" +
//                "   \"currentStreak\": 0,\n" +
//                "   \"personId\": 2\n" +
//                "}";
//
//        Habit habit = new Habit("name", "descr", 2);
//        habit.setId(1);
//        habit.setPersonId(2);
//
//        try (BufferedReader bufferedReader = new BufferedReader(new StringReader(requestBody))) {
//            when(request.getReader()).thenReturn(bufferedReader);
//
//            StringWriter stringWriter = new StringWriter();
//            PrintWriter writer = new PrintWriter(stringWriter);
//            when(response.getWriter()).thenReturn(writer);
//
//            HabitServlet habitServlet = new HabitServlet();
//            habitServlet.setHabitController(habitController);
//
//            doReturn(habit).when(habitController).createByPersonId(anyLong(), any());
//            habitServlet.doPost(request, response);
//
//            assertTrue(stringWriter.toString().contains("\"id\" : 1,"));
//        }
//    }
//
//    @Test
//    void doPutTest() throws ServletException, IOException {
//        HttpServletRequest request = spy(HttpServletRequest.class);
//        HttpServletResponse response = spy(HttpServletResponse.class);
//
//        String requestBody = "{\n" +
//                "  \"id\" : 1,\n" +
//                "   \"name\": \"jsonHabit\",\n" +
//                "   \"description\": \"jsonDescription\",\n" +
//                "   \"executionFrequency\": 3,\n" +
//                "   \"numberExecutions\": 0,\n" +
//                "   \"currentStreak\": 0,\n" +
//                "   \"personId\": 2\n" +
//                "}";
//
//        HabitServlet habitServlet = new HabitServlet();
//        habitServlet.setHabitController(habitController);
//
//        try (BufferedReader bufferedReader = new BufferedReader(new StringReader(requestBody))) {
//            when(request.getReader()).thenReturn(bufferedReader);
//
//            doNothing().when(habitController).update(any());
//
//            habitServlet.doPut(request, response);
//
//            verify(habitController, Mockito.times(1)).update(any());
//        }
//    }
//
//    @Test
//    void doDeleteTest() throws ServletException, IOException {
//        HttpServletRequest request = mock(HttpServletRequest.class);
//        HttpServletResponse response = mock(HttpServletResponse.class);
//
//        String expectedId = "1";
//
//        when(request.getParameter("id")).thenReturn(expectedId);
//
//        HabitServlet habitServlet = new HabitServlet();
//        habitServlet.setHabitController(habitController);
//
//        doNothing().when(habitController).removeById(Long.parseLong(expectedId));
//
//        habitServlet.doDelete(request, response);
//
//        verify(habitController, Mockito.times(1)).removeById(Long.parseLong(expectedId));
//    }
//}
