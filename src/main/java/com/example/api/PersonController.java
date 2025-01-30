package com.example.api;

import com.example.domain.customproperty.CustomPropertyService;
import com.example.domain.person.Person;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/person")
public class PersonController extends AbstractCustomPropertyValueController {
    public PersonController(CustomPropertyService customPropertyService) {
        super(Person.class, customPropertyService);
    }

}
