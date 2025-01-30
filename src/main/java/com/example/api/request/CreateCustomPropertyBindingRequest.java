package com.example.api.request;

public record CreateCustomPropertyBindingRequest(String code, String entityName, boolean enabled) {
}
