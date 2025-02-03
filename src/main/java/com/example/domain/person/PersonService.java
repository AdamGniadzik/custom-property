package com.example.domain.person;

import com.example.db.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public Person createNewPerson() {
        return personRepository.createPerson();
    }
}
