package com.example.db.exceptions;

import org.springframework.http.HttpStatus;

public class CustomPropertyValueNotExistsException extends DomainException {
    public CustomPropertyValueNotExistsException(String code, String entityName) {
        super("Custom property value for entity: " + entityName + " with: " + code +" does not exist!", HttpStatus.BAD_REQUEST);
    }
}
