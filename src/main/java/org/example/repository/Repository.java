package org.example.repository;

import org.example.model.Habit;
import org.example.model.Person;

import java.util.List;

public class Repository {
    List<Person> persons;

    public Repository(List<Person> persons) {
        this.persons = persons;
    }

    public Habit saveHabitByPersonId(Habit habit, int personId) {
        persons.get(personId).addHabit(habit);
        return habit;
    }

    public List<Habit> getHabitsByPersonId(int personId) {
        return persons.get(personId).getHabits();
    }

    public void removeHabitByPersonId(Habit habit, int personId) {
        persons.get(personId).removeHabit(habit);
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void editName(int personId, String newName) {
        persons.get(personId).setName(newName);
    }

    public void editEmail(int personId, String newEmail) {
        persons.get(personId).setEmail(newEmail);
    }

    public void editPassword(int personId, String newPassword) {
        persons.get(personId).setPassword(newPassword);
    }

    public int savePerson(Person person) {
        persons.add(person);
        return persons.indexOf(person);
    }

    public Person getPersonById(int personId) {
        return persons.get(personId);
    }

    public void removeByPersonId(int personId) {
        persons.remove(personId);
    }

    public boolean removePerson(Person person) {
        return persons.remove(person);
    }
}
