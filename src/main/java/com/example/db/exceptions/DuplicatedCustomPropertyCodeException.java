package com.example.db.exceptions;

import org.springframework.http.HttpStatus;

public class DuplicatedCustomPropertyCodeException extends DomainException{
    public DuplicatedCustomPropertyCodeException(String code) {
        super("Custom property with: " + code +" already exists!", HttpStatus.BAD_REQUEST);
    }
}
