package com.example.domain.customproperty;

import com.example.db.generated.tables.records.ItemRecord;
import com.example.db.repository.CustomPropertyValueRepository;
import lombok.AllArgsConstructor;
import org.jooq.Record;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CustomPropertyService {

    private final CustomPropertyValueRepository customPropertyValueRepository;


    public List<CustomPropertyValue> getCustomPropertyValueByCodeAndObjectId(Long objectId, String code) {
        return customPropertyValueRepository.getCustomPropertyValueByCode(ItemRecord.class, objectId, code)
                .map(this::mapCustomPropertyValue).stream().toList();
    }


    private CustomPropertyValue mapCustomPropertyValue(Record record) {
        return CustomPropertyValue.builder()
                .customPropertyId((Long) record.get("custom_property_id"))
                .doubleValue((Double) record.get("double_value"))
                .stringValue((String) record.get("string_value"))
                .integerValue((Integer) record.get("integer_value"))
                .booleanValue((Boolean) record.get("boolean_value"))
                .objectId((Long) record.get("object_id"))
                .longValue((Long) record.get("long_value")).build();
    }
}
