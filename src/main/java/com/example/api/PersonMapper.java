package com.example.api;

import com.example.domain.person.Person;
import org.springframework.stereotype.Component;

@Component
public class PersonMapper {

    PersonDto mapPerson(Person person) {
        return new PersonDto(person.getId(), person.getCreatedAt(), person.getUpdatedAt());
    }
}
