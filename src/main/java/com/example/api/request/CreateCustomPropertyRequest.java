package com.example.api.request;

public record CreateCustomPropertyRequest(String code, CustomPropertyType type) {

    public enum CustomPropertyType {
        LONG,
        INTEGER,
        STRING,
        BOOLEAN,
        DOUBLE
    }

}


