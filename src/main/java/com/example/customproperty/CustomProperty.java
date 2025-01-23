package com.example.customproperty;

public class CustomProperty {
    private CustomPropertyValue value;
    private CustomPropertyType type;

    Object getValue() {
        return switch (type) {
            case LONG_VALUE -> value.getLongValue();
            case DOUBLE_VALUE -> value.getDoubleValue();
            case STRING_VALUE -> value.getStringValue();
            case BOOLEAN_VALUE -> value.getBooleanValue();
            case INTEGER_VALUE -> value.getIntegerValue();
        };
    }

}
