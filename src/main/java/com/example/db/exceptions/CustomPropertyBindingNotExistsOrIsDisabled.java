package com.example.db.exceptions;

public class CustomPropertyBindingNotExistsOrIsDisabled extends DomainException {
    public CustomPropertyBindingNotExistsOrIsDisabled(String code, String entityName) {
        super("Custom property with code: "+ code +" is not bound to \"" + entityName + "\" entity.");
    }
}
