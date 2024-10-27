package org.example.mapper;

import org.example.DTO.HabitDTO;
import org.example.model.Habit;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface HabitMapper {

    HabitMapper INSTANCE = Mappers.getMapper(HabitMapper.class);

    HabitDTO habitToHabitDTO(Habit habit);
    Habit habitDTOToHabit(HabitDTO habitDTO);
    List<HabitDTO> habitsToHabitDTOList(List<Habit> habits);
}
