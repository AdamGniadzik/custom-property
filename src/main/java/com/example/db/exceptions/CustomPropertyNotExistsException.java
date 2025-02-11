package com.example.db.exceptions;

public class CustomPropertyNotExistsException extends DomainException {
    public CustomPropertyNotExistsException(String code) {
        super("Custom property with: " + code +" does not exist!");
    }
}
