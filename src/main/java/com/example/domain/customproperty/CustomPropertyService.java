package com.example.domain.customproperty;

import com.example.db.repository.CustomPropertyRepository;
import com.example.db.repository.CustomPropertyValueRepository;
import com.example.domain.DomainCustomizableEntity;
import com.example.domain.item.Item;
import com.example.domain.person.Person;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CustomPropertyService {

    private final CustomPropertyValueRepository customPropertyValueRepository;
    private final CustomPropertyRepository customPropertyRepository;

    public CustomPropertyValue getCustomPropertyValueByCodeAndObjectId(Class<? extends DomainCustomizableEntity> clazz, Long objectId, String code) {
        return customPropertyValueRepository.getCustomPropertyValueByCode(clazz, objectId, code);
    }

    public List<CustomPropertyValue> getCustomPropertyValueByObjectId(Class<? extends DomainCustomizableEntity> clazz, Long objectId) {
        return customPropertyValueRepository.getCustomPropertyValue(clazz, objectId);
    }

    public CustomProperty createCustomProperty(String code, String type) {
        return customPropertyRepository.createCustomProperty(code, type);
    }

    public CustomPropertyBinding createCustomPropertyBinding(String code,  String className, boolean enabled) {
        return customPropertyRepository.createCustomPropertyBinding(code, getDomainCustomizableEntityName(className), enabled);
    }

    private Class<? extends DomainCustomizableEntity> getDomainCustomizableEntityName(String className) {
        return switch (className) {
            case "Item" -> Item.class;
            case "Person" -> Person.class;
            default -> null;
        };
    }

}
