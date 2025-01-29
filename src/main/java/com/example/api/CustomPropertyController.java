package com.example.api;

import com.example.db.generated.tables.records.ItemRecord;
import com.example.db.generated.tables.records.PersonRecord;
import com.example.db.repository.CustomPropertyValueRepository;
import com.example.domain.customproperty.CustomPropertyService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
@AllArgsConstructor
public class CustomPropertyController {

    private final CustomPropertyValueRepository customPropertyValueRepository;
    private final CustomPropertyService customPropertyService;

    @GetMapping("/")
    public void insertTestValue() {
        customPropertyValueRepository.createCustomPropertyValue(ItemRecord.class, 1l, "CODE_SIZE", "Value");
        customPropertyValueRepository.createCustomPropertyValue(ItemRecord.class, 1l, "ADDITIONAL_SIZE", 1);
        customPropertyValueRepository.createCustomPropertyValue(PersonRecord.class, 1l, "CODE_SIZE", "Value");
    }

    @GetMapping("/value")
    public String testGet() {
        var list = customPropertyService.getCustomPropertyValueByCodeAndObjectId(1L, "CODE_SIZE");
        return Integer.valueOf(list.size()).toString();
    }
}
