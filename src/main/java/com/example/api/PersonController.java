package com.example.api;

import com.example.domain.customproperty.CustomPropertyService;
import com.example.domain.person.Person;
import com.example.domain.person.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/person")
public class PersonController extends AbstractCustomPropertyValueController {

    private final PersonService personService;
    private final PersonMapper personMapper;

    @Autowired
    public PersonController(CustomPropertyService customPropertyService, CustomPropertyMapper mapper, PersonService personService, PersonMapper personMapper) {
        super(Person.class, customPropertyService, mapper);
        this.personMapper = personMapper;
        this.personService = personService;
    }

    @PostMapping("/")
    @ResponseBody
    public PersonDto createNewPerson() {
        return personMapper.mapPerson(personService.createNewPerson());
    }

}
