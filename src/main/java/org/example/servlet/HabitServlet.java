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
import org.example.model.Habit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@Logging
@WebServlet("/habit")
public class HabitServlet extends HttpServlet {

    private HabitController habitController;
    private final HabitMapper habitMapper = HabitMapper.INSTANCE.INSTANCE;

    public HabitServlet() {
        habitController = Factory.getHabitController();
    }

    /**
     * Метод для получения привычки
     * Пример запроса: http://localhost:8080/habit?id=2
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter printWriter = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        long id = Long.parseLong(req.getParameter("id"));

        Optional<Habit> habitOpt = habitController.getById(id);
        if (habitOpt.isPresent()) {
            HabitDTO habitDTO = habitMapper.habitToHabitDTO(habitOpt.get());

            ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String jsonObject = objectWriter.writeValueAsString(habitDTO);

            resp.setStatus(HttpServletResponse.SC_OK);
            printWriter.print(jsonObject);
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * Метод создания новой привычки у пользователя
     * Ручка: http://localhost:8080/habit
     * Пример тела запроса:
     * {
     *     "name": "jsonHabit",
     *     "description": "jsonDescription",
     *     "executionFrequency": 3,
     *     "numberExecutions": 0,
     *     "currentStreak": 0,
     *     "personId": 3
     * }
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
            HabitDTO habitDTO = objectMapper.readValue(requestBody, HabitDTO.class);

            long personId = habitDTO.getPersonId();

            Habit habit = habitMapper.habitDTOToHabit(habitDTO);
            Habit createdHabit = habitController.createByPersonId(personId, habit);
            HabitDTO createdHabitDTO = habitMapper.habitToHabitDTO(createdHabit);

            PrintWriter printWriter = resp.getWriter();
            ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

            String jsonObject = objectWriter.writeValueAsString(createdHabitDTO);

            resp.setStatus(HttpServletResponse.SC_CREATED);
            printWriter.print(jsonObject);
        } catch (IOException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    /**
     * Метод для изменения данных привычки
     * Ручка запроса: http://localhost:8080/habit
     * Пример тела запроса:
     * {
     *     "id": 2
     *     "name": "jsonHabit",
     *     "description": "jsonDescription",
     *     "executionFrequency": 3,
     *     "numberExecutions": 0,
     *     "currentStreak": 0,
     *     "personId": 3
     * }
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        try (BufferedReader reader = req.getReader()) {
            StringBuilder body = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                body.append(line);
            }

            String requestBody = body.toString();
            HabitDTO habitDTO = objectMapper.readValue(requestBody, HabitDTO.class);

            Habit habit = habitMapper.habitDTOToHabit(habitDTO);

            habitController.update(habit);

            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (IOException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    /**
     * Метод для удаления привычки
     * Пример запроса: http://localhost:8080/habit?id=1
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("id"));

        habitController.removeById(id);
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    public void setHabitController(HabitController habitController) {
        this.habitController = habitController;
    }
}
