package com.example.customproperty;

public enum CustomPropertyCode {
    CODE_SIZE(CustomPropertyType.INTEGER_VALUE);

    final CustomPropertyType type;

    CustomPropertyCode(CustomPropertyType type) {
        this.type = type;
    }
}
