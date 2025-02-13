package com.example.db.exceptions;

import org.springframework.http.HttpStatus;

public class CustomPropertyBindingNotExistsOrIsDisabled extends DomainException {
    public CustomPropertyBindingNotExistsOrIsDisabled(String code, String entityName) {
        super("Custom property with code: "+ code +" is not bound to \"" + entityName + "\" entity.", HttpStatus.BAD_REQUEST);
    }
}
