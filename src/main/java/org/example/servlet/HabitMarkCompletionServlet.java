package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.Factory;
import org.example.annotations.Logging;
import org.example.controller.HabitController;
import org.example.model.Habit;

import java.io.IOException;
import java.util.Optional;

@Logging
@WebServlet("/habit/mark_completion")
public class HabitMarkCompletionServlet extends HttpServlet {

    private HabitController habitController;

    public HabitMarkCompletionServlet() {
        habitController = Factory.getHabitController();
    }

    /**
     * Метод отмечающий выполнение привычки
     * Пример запроса: http://localhost:8080/habit/mark_completion?id=1
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("id"));

        Optional<Habit> habitOpt = habitController.getById(id);

        if (habitOpt.isPresent()) {
            habitController.markCompletion(habitOpt.get());

            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    public void setHabitController(HabitController habitController) {
        this.habitController = habitController;
    }
}
