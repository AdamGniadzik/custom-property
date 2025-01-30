package com.example.db.repository;

import com.example.db.generated.Tables;
import com.example.db.generated.tables.records.CustomPropertyBindingsRecord;
import com.example.db.generated.tables.records.CustomPropertyRecord;
import com.example.domain.DomainCustomizableEntity;
import com.example.domain.customproperty.CustomProperty;
import com.example.domain.customproperty.CustomPropertyBinding;
import com.example.domain.customproperty.CustomPropertyType;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@AllArgsConstructor
public class CustomPropertyRepository {


    private final DSLContext dslContext;

    public CustomProperty createCustomProperty(String code, String type) {
        CustomPropertyType cpType = CustomPropertyType.valueOf(type);
        return mapCustomProperty(dslContext.insertInto(Tables.CUSTOM_PROPERTY).set(Tables.CUSTOM_PROPERTY.CODE, code)
                .set(Tables.CUSTOM_PROPERTY.TYPE, cpType.name()).returning().fetchSingle()
                .into(CustomPropertyRecord.class));
    }

    public CustomPropertyBinding createCustomPropertyBinding(String code, Class<? extends DomainCustomizableEntity> entity, Boolean enabled) {
        CustomPropertyRecord cp = fetchCustomPropertyRecordByCode(code);
        return mapCustomPropertyBinding(dslContext.insertInto(Tables.CUSTOM_PROPERTY_BINDINGS)
                .set(Tables.CUSTOM_PROPERTY_BINDINGS.CUSTOM_PROPERTY_ID, cp.getId())
                .set(Tables.CUSTOM_PROPERTY_BINDINGS.CREATED_AT, LocalDateTime.now())
                .set(Tables.CUSTOM_PROPERTY_BINDINGS.UPDATED_AT, LocalDateTime.now())
                .set(Tables.CUSTOM_PROPERTY_BINDINGS.ENABLED, enabled)
                .set(Tables.CUSTOM_PROPERTY.TYPE, entity.getName()).returning().fetchSingle()
                .into(CustomPropertyBindingsRecord.class), cp.getCode());
    }


    public List<CustomProperty> getCustomPropertyList() {
        return dslContext.select().from(Tables.CUSTOM_PROPERTY).fetchInto(Tables.CUSTOM_PROPERTY)
                .map(this::mapCustomProperty);
    }

    private CustomPropertyRecord fetchCustomPropertyRecordByCode(String code) {
        return dslContext.select().from(Tables.CUSTOM_PROPERTY).where(Tables.CUSTOM_PROPERTY.CODE.eq(code))
                .fetchSingle().into(Tables.CUSTOM_PROPERTY);
    }


    private CustomProperty mapCustomProperty(CustomPropertyRecord record) {
        return new CustomProperty(record.getCode(), CustomPropertyType.valueOf(record.getType()));
    }

    private CustomPropertyBinding mapCustomPropertyBinding(CustomPropertyBindingsRecord record, String code) {
        return new CustomPropertyBinding(record.getCustomPropertyId(), code, record.getClassName(),
                record.getEnabled(), record.getCreatedAt(), record.getUpdatedAt());
    }

}
