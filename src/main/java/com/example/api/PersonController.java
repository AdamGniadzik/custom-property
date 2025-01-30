package com.example.api;

import com.example.domain.customproperty.CustomPropertyService;
import com.example.domain.person.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/person")
public class PersonController extends AbstractCustomPropertyValueController {
    @Autowired
    public PersonController(CustomPropertyService customPropertyService, CustomPropertyMapper mapper) {
        super(Person.class, customPropertyService, mapper);
    }

}
