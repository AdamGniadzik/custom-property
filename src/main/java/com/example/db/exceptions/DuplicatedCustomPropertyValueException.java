package com.example.db.exceptions;

import org.springframework.http.HttpStatus;

public class DuplicatedCustomPropertyValueException extends DomainException {
    public DuplicatedCustomPropertyValueException(String code) {
        super("Custom property " + code + " already exists on selected object. Value can be updated only bu update operation", HttpStatus.BAD_REQUEST);
    }
}
