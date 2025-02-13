package com.example.db.repository;

import com.example.db.*;
import com.example.db.exceptions.CustomPropertyBindingNotExistsOrIsDisabled;
import com.example.db.exceptions.CustomPropertyNotExistsException;
import com.example.db.exceptions.CustomPropertyValueNotExistsException;
import com.example.db.exceptions.DuplicatedCustomPropertyValueException;
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
import lombok.extern.slf4j.Slf4j;
import org.jooq.*;
import org.jooq.Record;
import org.jooq.exception.IntegrityConstraintViolationException;
import org.jooq.impl.TableImpl;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.jooq.impl.DSL.*;

@AllArgsConstructor
@Repository
@Slf4j
public class CustomPropertyValueRepository {
    private static final Map<Class<? extends Record>, TableImpl<? extends CustomPropertyValueRecord>> classMapping = createMapping();
    private static final String LONG_VALUE_COL = "long_value";
    private static final String STRING_VALUE_COL = "string_value";
    private static final String DOUBLE_VALUE_COL = "double_value";
    private static final String INTEGER_VALUE_COL = "integer_value";
    private static final String BOOLEAN_VALUE_COL = "boolean_value";
    private static final String CUSTOM_PROPERTY_ID_COL = "custom_property_id";
    private static final String CUSTOM_PROPERTY_CODE = "code";
    private static final String OBJECT_ID_COL = "object_id";

    private final DSLContext dslContext;

    private static Map<Class<? extends Record>, TableImpl<? extends CustomPropertyValueRecord>> createMapping() {
        Map<Class<? extends Record>, TableImpl<? extends CustomPropertyValueRecord>> map = new HashMap<>();
        map.put(ItemRecord.class, ItemCustomProperty.ITEM_CUSTOM_PROPERTY);
        map.put(PersonRecord.class, PersonCustomProperty.PERSON_CUSTOM_PROPERTY);
        return map;
    }

    public CustomPropertyValue createOrUpdateCustomPropertyValue(Class<? extends DomainCustomizableEntity> entityClass, Long objectId, String customPropertyCode, Object value) {
        CustomPropertyRecord cp = getCustomPropertyByCode(customPropertyCode);
        validateCustomPropertyBinding(cp, entityClass);
        var table = classMapping.get(getEntityRecordByDomainClassName(entityClass));
        var ifExist = dslContext.fetchExists(table, table.fieldsRow().field(CUSTOM_PROPERTY_ID_COL, Long.class)
                        .eq(cp.getId()),
                table.fieldsRow().field(OBJECT_ID_COL, Long.class).eq(cp.getId()));
        if (ifExist) {
            return updateCustomPropertyValue(table, objectId, cp, value);
        } else {
            return createCustomPropertyValue(table, objectId, cp, value);
        }
    }


    private CustomPropertyRecord getCustomPropertyByCode(String code) {
        Result<Record> records = dslContext.select().from(Tables.CUSTOM_PROPERTY)
                .where(Tables.CUSTOM_PROPERTY.CODE.eq(code)).fetch();
        if (records.size() == 0) {
            throw new CustomPropertyNotExistsException(code);
        }
        return records.into(Tables.CUSTOM_PROPERTY).get(0);
    }

    private void validateCustomPropertyBinding(CustomPropertyRecord cp, Class<? extends DomainCustomizableEntity> entityClass) {
        boolean bindingIsValid = dslContext.fetchExists(Tables.CUSTOM_PROPERTY_BINDINGS, Tables.CUSTOM_PROPERTY_BINDINGS.CUSTOM_PROPERTY_ID.eq(cp.getId())
                .and(Tables.CUSTOM_PROPERTY_BINDINGS.ENABLED.eq(true))
                .and(Tables.CUSTOM_PROPERTY_BINDINGS.CLASS_NAME.eq(entityClass.getSimpleName())));
        if (!bindingIsValid) {
            throw new CustomPropertyBindingNotExistsOrIsDisabled(cp.getCode(), entityClass.getSimpleName());
        }
    }

    public CustomPropertyValue createCustomPropertyValue(Class<? extends DomainCustomizableEntity> entityClass, Long objectId, String customPropertyCode, Object value) {
        CustomPropertyRecord cp = getCustomPropertyByCode(customPropertyCode);
        validateCustomPropertyBinding(cp, entityClass);
        var table = classMapping.get(getEntityRecordByDomainClassName(entityClass));
        return createCustomPropertyValue(table, objectId, cp, value);
    }

    private CustomPropertyValue updateCustomPropertyValue(Table<? extends CustomPropertyValueRecord> table, Long objectId, CustomPropertyRecord cp, Object value) {
        var firstStep = dslContext.update(table);
        var step = switch (cp.getType()) {
            case "LONG" -> firstStep.set(table.fieldsRow().field(LONG_VALUE_COL, Long.class), (Long) value);
            case "DECIMAL" -> firstStep.set(table.fieldsRow().field(DOUBLE_VALUE_COL, Double.class), (Double) value);
            case "BOOLEAN" -> firstStep.set(table.fieldsRow().field(BOOLEAN_VALUE_COL, Boolean.class), (Boolean) value);
            case "STRING" -> firstStep.set(table.fieldsRow().field(STRING_VALUE_COL, String.class), (String) value);
            case "INTEGER" -> firstStep.set(table.fieldsRow().field(INTEGER_VALUE_COL, Integer.class), (Integer) value);
            default -> throw new RuntimeException("Invalid custom property type");
        };
        return step.where(table.fieldsRow().field(CUSTOM_PROPERTY_ID_COL, Long.class).eq(cp.getId()))
                .and(table.fieldsRow().field(OBJECT_ID_COL, Long.class).eq(objectId)).
                returning().fetchSingle().map(record -> mapCustomPropertyValue(record, cp.getCode()));

    }

    private CustomPropertyValue createCustomPropertyValue(Table<? extends CustomPropertyValueRecord> table, Long objectId, CustomPropertyRecord cp, Object value) {
        try {
            var firstStep = dslContext.insertInto(table)
                    .set(table.fieldsRow().field(CUSTOM_PROPERTY_ID_COL, Long.class), cp.getId())
                    .set(table.fieldsRow().field(OBJECT_ID_COL, Long.class), objectId);
            var step = switch (cp.getType()) {
                case "LONG" -> firstStep.set(table.fieldsRow()
                        .field(LONG_VALUE_COL, Long.class), Long.parseLong(value.toString()));
                case "DECIMAL" ->
                        firstStep.set(table.fieldsRow().field(DOUBLE_VALUE_COL, Double.class), (Double) value);
                case "BOOLEAN" ->
                        firstStep.set(table.fieldsRow().field(BOOLEAN_VALUE_COL, Boolean.class), (Boolean) value);
                case "STRING" -> firstStep.set(table.fieldsRow().field(STRING_VALUE_COL, String.class), (String) value);
                case "INTEGER" -> firstStep.set(table.fieldsRow()
                        .field(INTEGER_VALUE_COL, Integer.class), Integer.parseInt(value.toString()));
                default -> throw new IllegalStateException("Unexpected value: " + cp.getType());
            };
            return step.returning().fetchSingle().map(record -> mapCustomPropertyValue(record, cp.getCode()));
        } catch (DuplicateKeyException e){
            log.error(e.getMessage());
            throw new DuplicatedCustomPropertyValueException(cp.getCode());
        }
    }

    public CustomPropertyValue getCustomPropertyValueByCode(Class<? extends DomainCustomizableEntity> clazz, Long objectId, String customPropertyCode) {
        var table = classMapping.get(getEntityRecordByDomainClassName(clazz));
        return dslContext.select(table, Tables.CUSTOM_PROPERTY.CODE).from(table.join(Tables.CUSTOM_PROPERTY)
                        .on(Objects.requireNonNull(table.field(getCpIdField(table))).eq(Tables.CUSTOM_PROPERTY.ID)))
                .where(Tables.CUSTOM_PROPERTY.CODE.eq(customPropertyCode))
                .and(Objects.requireNonNull(table.field(getObjectIdField(table))).eq(objectId))
                .fetch().map(cp -> mapCustomPropertyValue(cp, customPropertyCode)).stream().findFirst()
                .orElseThrow(() -> new CustomPropertyValueNotExistsException(customPropertyCode, clazz.getSimpleName()));
    }

    public List<CustomPropertyValue> getCustomPropertyValue(Class<? extends DomainCustomizableEntity> recordClass, Long objectId) {
        var table = classMapping.get(getEntityRecordByDomainClassName(recordClass));
        return dslContext.select().from(table.leftOuterJoin(Tables.CUSTOM_PROPERTY)
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
                .customPropertyCode((String) record.get(CUSTOM_PROPERTY_CODE))
                .doubleValue((Double) record.get(DOUBLE_VALUE_COL))
                .stringValue((String) record.get(STRING_VALUE_COL))
                .integerValue((Integer) record.get(INTEGER_VALUE_COL))
                .booleanValue((Boolean) record.get(BOOLEAN_VALUE_COL))
                .objectId((Long) record.get(OBJECT_ID_COL))
                .longValue((Long) record.get(LONG_VALUE_COL)).build();
    }

    private CustomPropertyValue mapCustomPropertyValue(Record record, String code) {
        return CustomPropertyValue.builder()
                .customPropertyCode(code)
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
