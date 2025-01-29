package com.example.domain.customproperty;


import com.example.api.CustomPropertyType;

enum CustomPropertyCodes {
    CODE_SIZE(CustomPropertyType.INTEGER_VALUE);

    final CustomPropertyType type;

    CustomPropertyCodes(CustomPropertyType type) {
        this.type = type;
    }
}
