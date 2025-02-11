package com.example.db.exceptions;

public class DuplicatedCustomPropertyBindingException extends DomainException{
    public DuplicatedCustomPropertyBindingException(String code, String entityName) {
        super("Custom property with: " + code +" is already bound to " + entityName);
    }
}
