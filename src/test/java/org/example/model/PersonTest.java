package org.example.model;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersonTest {

    @Mock
    Habit mockHabit;

    @Test
    void testAddHabit() {
        Person person = new Person("email", "pass", "name");
        List<Habit> habitsExpected = new ArrayList<>();
        habitsExpected.add(mockHabit);

        person.addHabit(mockHabit);

        assertEquals(habitsExpected, person.getHabits());
    }

    @Test
    void testRemoveHabit() {
        Person person = new Person("email", "pass", "name");
        Habit mockHabit2 = Mockito.mock(Habit.class);
        List<Habit> habitsExpected = new ArrayList<>();

        habitsExpected.add(mockHabit);
        habitsExpected.add(mockHabit2);
        habitsExpected.remove(mockHabit);

        person.addHabit(mockHabit);
        person.addHabit(mockHabit2);
        person.removeHabit(mockHabit);

        assertEquals(habitsExpected, person.getHabits());
    }

}
