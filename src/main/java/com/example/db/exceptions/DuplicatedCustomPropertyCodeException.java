package com.example.db.exceptions;

public class DuplicatedCustomPropertyCodeException extends DomainException{
    public DuplicatedCustomPropertyCodeException(String code) {
        super("Custom property with: " + code +" already exists!");
    }
}
