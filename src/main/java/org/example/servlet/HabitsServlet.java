package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.DTO.HabitDTO;
import org.example.Factory;
import org.example.annotations.Logging;
import org.example.controller.HabitController;
import org.example.mapper.HabitMapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.NoSuchElementException;

@Logging
@WebServlet("/habits")
public class HabitsServlet extends HttpServlet {

    private HabitController habitController;
    private final HabitMapper habitMapper = HabitMapper.INSTANCE.INSTANCE;

    public HabitsServlet() {
        habitController = Factory.getHabitController();
    }

    /**
     * Метод получения списка привычек пользователя
     * Пример запроса: http://localhost:8080/habits?person_id=1
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter printWriter = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try {
            long personId = Long.parseLong(req.getParameter("person_id"));

            List<HabitDTO> habitDTOList = habitMapper.habitsToHabitDTOList(habitController.getHabitsByPersonId(personId));

            ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String jsonObject = objectWriter.writeValueAsString(habitDTOList);
            resp.setStatus(HttpServletResponse.SC_OK);
            printWriter.print(jsonObject);
        } catch (NoSuchElementException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    public void setHabitController(HabitController habitController) {
        this.habitController = habitController;
    }
}
