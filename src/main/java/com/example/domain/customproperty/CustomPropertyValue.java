package com.example.domain.customproperty;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Getter
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomPropertyValue {
    private Long objectId;
    private String customPropertyCode;
    private Long longValue;
    private Boolean booleanValue;
    private String stringValue;
    private Integer integerValue;
    private Double doubleValue;
}
