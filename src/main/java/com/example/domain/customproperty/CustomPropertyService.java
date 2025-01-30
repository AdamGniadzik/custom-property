package com.example.domain.customproperty;

import com.example.db.repository.CustomPropertyValueRepository;
import com.example.domain.DomainCustomizableEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CustomPropertyService {

    private final CustomPropertyValueRepository customPropertyValueRepository;

    public CustomPropertyValue getCustomPropertyValueByCodeAndObjectId(Class<? extends DomainCustomizableEntity> clazz, Long objectId, String code) {
        return customPropertyValueRepository.getCustomPropertyValueByCode(clazz, objectId, code);
    }

    public List<CustomPropertyValue> getCustomPropertyValueByObjectId(Class<? extends DomainCustomizableEntity> clazz, Long objectId) {
        return customPropertyValueRepository.getCustomPropertyValue(clazz, objectId);
    }

}
