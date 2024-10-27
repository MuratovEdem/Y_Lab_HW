package org.example.mapper;

import org.example.DTO.PersonDTO;
import org.example.model.Person;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PersonMapper {

    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    PersonDTO personToPersonDTO(Person person);
    Person personDTOToPerson(PersonDTO personDTO);
    List<PersonDTO> personsToPersonDTOList(List<Person> persons);
}
