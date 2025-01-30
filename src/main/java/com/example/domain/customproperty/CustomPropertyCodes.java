package com.example.domain.customproperty;

enum CustomPropertyCodes {
    CODE_SIZE(CustomPropertyType.INTEGER);

    final CustomPropertyType type;

    CustomPropertyCodes(CustomPropertyType type) {
        this.type = type;
    }
}
