package com.example.domain.customproperty;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CustomPropertyValue {
    private Long customPropertyId;
    private Long objectId;
    private Long longValue;
    private Boolean booleanValue;
    private String stringValue;
    private Integer integerValue;
    private Double doubleValue;

}
