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
//import java.io.IOException;
//import java.util.Optional;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//import static org.mockito.Mockito.verify;
//
//public class HabitMarkCompletionServletTest {
//
//    private HabitController habitController = mock(HabitController.class);
//
//    @Test
//    void doPutTest() throws ServletException, IOException {
//        HttpServletRequest request = spy(HttpServletRequest.class);
//        HttpServletResponse response = spy(HttpServletResponse.class);
//
//        String expectedId = "1";
//        when(request.getParameter("id")).thenReturn(expectedId);
//
//        Habit habit = new Habit("name", "descr", 2);
//        habit.setId(1);
//
//        Optional<Habit> habitOptional = Optional.of(habit);
//
//        doReturn(habitOptional).when(habitController).getById(Long.parseLong(expectedId));
//
//        doReturn(true).when(habitController).markCompletion(any());
//
//        HabitMarkCompletionServlet habitMarkCompletionServlet = new HabitMarkCompletionServlet();
//        habitMarkCompletionServlet.setHabitController(habitController);
//
//        habitMarkCompletionServlet.doPut(request, response);
//
//        verify(habitController, Mockito.times(1)).markCompletion(any());
//    }
//}
