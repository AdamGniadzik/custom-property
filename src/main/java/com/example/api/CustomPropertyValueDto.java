package com.example.api;

public record CustomPropertyValueDto(Long objectId, String customPropertyCode, Long longValue, Boolean booleanValue,
                                     String stringValue, Integer integerValue, Double doubleValue) {
}
