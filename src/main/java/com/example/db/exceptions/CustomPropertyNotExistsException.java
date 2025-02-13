package com.example.db.exceptions;

import org.springframework.http.HttpStatus;

public class CustomPropertyNotExistsException extends DomainException {
    public CustomPropertyNotExistsException(String code) {
        super("Custom property with: " + code +" does not exist!", HttpStatus.BAD_REQUEST);
    }
}
