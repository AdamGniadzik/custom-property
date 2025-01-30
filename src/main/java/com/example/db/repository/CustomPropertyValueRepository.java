package com.example.db.repository;

import com.example.db.CustomPropertyValueRecord;
import com.example.db.CustomizableEntityRecord;
import com.example.db.generated.Tables;
import com.example.db.generated.tables.ItemCustomProperty;
import com.example.db.generated.tables.PersonCustomProperty;
import com.example.db.generated.tables.records.CustomPropertyRecord;
import com.example.db.generated.tables.records.ItemRecord;
import com.example.db.generated.tables.records.PersonRecord;
import com.example.domain.DomainCustomizableEntity;
import com.example.domain.customproperty.CustomPropertyValue;
import com.example.domain.item.Item;
import com.example.domain.person.Person;
import lombok.AllArgsConstructor;
import org.jooq.*;
import org.jooq.Record;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.name;

@AllArgsConstructor
@Repository
public class CustomPropertyValueRepository {
    private static final Map<Class<? extends Record>, TableImpl<? extends CustomPropertyValueRecord>> classMapping = createMapping();
    private static final String LONG_VALUE_COL = "long_value";
    private static final String STRING_VALUE_COL = "string_value";
    private static final String DOUBLE_VALUE_COL = "double_value";
    private static final String INTEGER_VALUE_COL = "integer_value";
    private static final String BOOLEAN_VALUE_COL = "long_value";
    private static final String CUSTOM_PROPERTY_ID_COL = "custom_property_id";
    private static final String OBJECT_ID_COL = "object_id";
    private final DSLContext dslContext;

    private static Map<Class<? extends Record>, TableImpl<? extends CustomPropertyValueRecord>> createMapping() {
        Map<Class<? extends Record>, TableImpl<? extends CustomPropertyValueRecord>> map = new HashMap<>();
        map.put(ItemRecord.class, ItemCustomProperty.ITEM_CUSTOM_PROPERTY);
        map.put(PersonRecord.class, PersonCustomProperty.PERSON_CUSTOM_PROPERTY);
        return map;
    }

    public void createCustomPropertyValue(Class<? extends Record> recordClass, Long objectId, String customPropertyCode, Object value) {
        CustomPropertyRecord cp = dslContext.select().from(Tables.CUSTOM_PROPERTY)
                .where(Tables.CUSTOM_PROPERTY.CODE.eq(customPropertyCode))
                .fetchSingle().into(Tables.CUSTOM_PROPERTY);
        var table = classMapping.get(recordClass);
        var step = dslContext.insertInto(table)
                .set(table.fieldsRow().field(CUSTOM_PROPERTY_ID_COL, Long.class), cp.getId())
                .set(table.fieldsRow().field(OBJECT_ID_COL, Long.class), objectId);
        step = switch (cp.getType()) {
            case "LONG" -> step.set(table.fieldsRow().field(LONG_VALUE_COL, Long.class), (Long) value);
            case "DECIMAL" -> step.set(table.fieldsRow().field(DOUBLE_VALUE_COL, Double.class), (Double) value);
            case "BOOLEAN" -> step.set(table.fieldsRow().field(BOOLEAN_VALUE_COL, Boolean.class), (Boolean) value);
            case "STRING" -> step.set(table.fieldsRow().field(STRING_VALUE_COL, String.class), (String) value);
            case "INTEGER" -> step.set(table.fieldsRow().field(INTEGER_VALUE_COL, Integer.class), (Integer) value);
            default -> throw new RuntimeException("Invalid custom property type");
        };
        step.execute();
    }

    public CustomPropertyValue getCustomPropertyValueByCode(Class<? extends DomainCustomizableEntity> recordClass, Long objectId, String customPropertyCode) {
        var table = classMapping.get(getEntityRecordByDomainClassName(recordClass));
        return dslContext.select().from(table.join(Tables.CUSTOM_PROPERTY)
                        .on(Objects.requireNonNull(table.field(getCpIdField(table))).eq(Tables.CUSTOM_PROPERTY.ID)))
                .where(Tables.CUSTOM_PROPERTY.CODE.eq(customPropertyCode))
                .and(Objects.requireNonNull(table.field(getObjectIdField(table))).eq(objectId))
                .fetch().map(this::mapCustomPropertyValue).stream().findFirst().orElseThrow();
    }

    public List<CustomPropertyValue> getCustomPropertyValue(Class<? extends DomainCustomizableEntity> recordClass, Long objectId) {
        var table = classMapping.get(getEntityRecordByDomainClassName(recordClass));
        return dslContext.select().from(table.join(Tables.CUSTOM_PROPERTY)
                        .on(Objects.requireNonNull(table.field(getCpIdField(table))).eq(Tables.CUSTOM_PROPERTY.ID)))
                .where(Objects.requireNonNull(table.field(getObjectIdField(table))).eq(objectId))
                .fetch().map(this::mapCustomPropertyValue).stream().toList();
    }

    private Field<Long> getCpIdField(TableImpl<? extends CustomPropertyValueRecord> table) {
        return field(name(table.getName(), CUSTOM_PROPERTY_ID_COL), Long.class);
    }

    private Field<Long> getObjectIdField(TableImpl<? extends CustomPropertyValueRecord> table) {
        return field(name(table.getName(), OBJECT_ID_COL), Long.class);
    }

    private CustomPropertyValue mapCustomPropertyValue(Record record) {
        return CustomPropertyValue.builder()
                .customPropertyCode(record.get(Tables.CUSTOM_PROPERTY.CODE))
                .doubleValue((Double) record.get(DOUBLE_VALUE_COL))
                .stringValue((String) record.get(STRING_VALUE_COL))
                .integerValue((Integer) record.get(INTEGER_VALUE_COL))
                .booleanValue((Boolean) record.get(BOOLEAN_VALUE_COL))
                .objectId((Long) record.get(OBJECT_ID_COL))
                .longValue((Long) record.get(LONG_VALUE_COL)).build();
    }

    private Class<? extends CustomizableEntityRecord> getEntityRecordByDomainClassName(Class<? extends DomainCustomizableEntity> clazz) {
        if (clazz.equals(Item.class)) {
            return ItemRecord.class;
        }
        if (clazz.equals(Person.class)) {
            return PersonRecord.class;
        }
        throw new UnsupportedOperationException(clazz.getName() + " is not supporting custom properties");
    }
}
