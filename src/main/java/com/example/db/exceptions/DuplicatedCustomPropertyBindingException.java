package com.example.db.exceptions;

import org.springframework.http.HttpStatus;

public class DuplicatedCustomPropertyBindingException extends DomainException{
    public DuplicatedCustomPropertyBindingException(String code, String entityName) {
        super("Custom property with: " + code +" is already bound to " + entityName, HttpStatus.BAD_REQUEST);
    }
}
