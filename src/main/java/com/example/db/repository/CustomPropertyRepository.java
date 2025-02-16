package com.example.db.repository;

import com.example.db.ObjectNotFoundException;
import com.example.db.exceptions.CustomPropertyNotExistsException;
import com.example.db.exceptions.DuplicatedCustomPropertyBindingException;
import com.example.db.exceptions.DuplicatedCustomPropertyCodeException;
import com.example.db.generated.Tables;
import com.example.db.generated.tables.records.CustomPropertyBindingsRecord;
import com.example.db.generated.tables.records.CustomPropertyRecord;
import com.example.domain.DomainCustomizableEntity;
import com.example.domain.customproperty.CustomProperty;
import com.example.domain.customproperty.CustomPropertyBinding;
import com.example.domain.customproperty.CustomPropertyType;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@AllArgsConstructor
public class CustomPropertyRepository {


    private final DSLContext dslContext;

    public CustomProperty createCustomProperty(String code, String type) {
        if (dslContext.fetchExists(Tables.CUSTOM_PROPERTY, Tables.CUSTOM_PROPERTY.CODE.eq(code))) {
            throw new DuplicatedCustomPropertyCodeException(code);
        }
        CustomPropertyType cpType = CustomPropertyType.valueOf(type);
        return mapCustomProperty(dslContext.insertInto(Tables.CUSTOM_PROPERTY).set(Tables.CUSTOM_PROPERTY.CODE, code)
                .set(Tables.CUSTOM_PROPERTY.TYPE, cpType.name()).returning().fetchSingle()
                .into(CustomPropertyRecord.class));
    }

    public CustomPropertyBinding createCustomPropertyBinding(CustomProperty cp, Class<? extends DomainCustomizableEntity> entity, Boolean enabled) {
        if (dslContext.fetchExists(Tables.CUSTOM_PROPERTY_BINDINGS,
                Tables.CUSTOM_PROPERTY_BINDINGS.CLASS_NAME.eq(entity.getSimpleName()))) {
            throw new DuplicatedCustomPropertyBindingException(cp.code(), entity.getSimpleName());
        }
        return mapCustomPropertyBinding(dslContext.insertInto(Tables.CUSTOM_PROPERTY_BINDINGS)
                .set(Tables.CUSTOM_PROPERTY_BINDINGS.CLASS_NAME, entity.getSimpleName())
                .set(Tables.CUSTOM_PROPERTY_BINDINGS.CUSTOM_PROPERTY_ID, cp.id())
                .set(Tables.CUSTOM_PROPERTY_BINDINGS.CREATED_AT, LocalDateTime.now())
                .set(Tables.CUSTOM_PROPERTY_BINDINGS.UPDATED_AT, LocalDateTime.now())
                .set(Tables.CUSTOM_PROPERTY_BINDINGS.ENABLED, enabled).returning().fetchSingle()
                .into(CustomPropertyBindingsRecord.class), cp.code());
    }

    public CustomPropertyBinding updateCustomPropertyBinding(CustomProperty cp, Class<? extends DomainCustomizableEntity> entity, Boolean enabled) {
        return mapCustomPropertyBinding(dslContext.update(Tables.CUSTOM_PROPERTY_BINDINGS)
                .set(Tables.CUSTOM_PROPERTY_BINDINGS.ENABLED, enabled)
                .set(Tables.CUSTOM_PROPERTY_BINDINGS.UPDATED_AT, LocalDateTime.now())
                .where(Tables.CUSTOM_PROPERTY_BINDINGS.CUSTOM_PROPERTY_ID.eq(cp.id())
                        .and(Tables.CUSTOM_PROPERTY_BINDINGS.CLASS_NAME.eq(entity.getSimpleName())))
                .returning().fetchSingle().into(CustomPropertyBindingsRecord.class), cp.code());
    }


    public List<CustomProperty> getCustomPropertyList() {
        return dslContext.select().from(Tables.CUSTOM_PROPERTY).fetchInto(Tables.CUSTOM_PROPERTY)
                .map(this::mapCustomProperty);
    }

    @Cacheable("cp")
    public CustomProperty getCustomProperty(String code) {
        return dslContext.select().from(Tables.CUSTOM_PROPERTY)
                .where(Tables.CUSTOM_PROPERTY.CODE.eq(code)).fetchOptional()
                .map(r -> mapCustomProperty(r.into(CustomPropertyRecord.class)))
                .orElseThrow(() -> new CustomPropertyNotExistsException(code));
    }


    private CustomProperty mapCustomProperty(CustomPropertyRecord record) {
        return new CustomProperty(record.getId(), record.getCode(), CustomPropertyType.valueOf(record.getType()));
    }

    private CustomPropertyBinding mapCustomPropertyBinding(CustomPropertyBindingsRecord record, String code) {
        return new CustomPropertyBinding(record.getCustomPropertyId(), code, record.getClassName(),
                record.getEnabled(), record.getCreatedAt(), record.getUpdatedAt());
    }

}
