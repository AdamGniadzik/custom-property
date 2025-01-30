package com.example.api;

import com.example.domain.customproperty.CustomProperty;
import com.example.domain.customproperty.CustomPropertyBinding;
import com.example.domain.customproperty.CustomPropertyValue;
import org.springframework.stereotype.Component;

@Component
class CustomPropertyMapper {
    CustomPropertyDto mapCustomProperty(CustomProperty customProperty){
        return new CustomPropertyDto(customProperty.code(), customProperty.type().name());
    }

    CustomPropertyValueDto mapCustomPropertyValue(CustomPropertyValue value){
        return new CustomPropertyValueDto(value.getObjectId(), value.getCustomPropertyCode(), value.getLongValue(),
                value.getBooleanValue(), value.getStringValue(), value.getIntegerValue(), value.getDoubleValue());
    }

    CustomPropertyBindingDto mapCustomPropertyBindingDto(CustomPropertyBinding binding){
        return new CustomPropertyBindingDto(binding.customPropertyCode(), binding.entityClassName(), binding.createdAt(),
                binding.updatedAt(), binding.enabled());
    }
}
