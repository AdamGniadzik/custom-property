package com.example.db.repository;

import com.example.db.CustomPropertyValueRecord;
import com.example.db.generated.Tables;
import com.example.db.generated.tables.ItemCustomProperty;
import com.example.db.generated.tables.PersonCustomProperty;
import com.example.db.generated.tables.records.CustomPropertyRecord;
import com.example.db.generated.tables.records.ItemRecord;
import com.example.db.generated.tables.records.PersonRecord;
import lombok.AllArgsConstructor;
import org.jooq.*;
import org.jooq.Record;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.name;


@AllArgsConstructor
@Repository
public class CustomPropertyValueRepository {

    private static final Map<Class<? extends Record>, TableImpl<? extends CustomPropertyValueRecord>> classMapping = createMapping();
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
                .set(table.fieldsRow().field("custom_property_id", Long.class), cp.getId())
                .set(table.fieldsRow().field("object_id", Long.class), objectId);
        step = switch (cp.getType()) {
            case "LONG" -> step.set(table.fieldsRow().field("long_value", Long.class), (Long) value);
            case "DECIMAL" -> step.set(table.fieldsRow().field("double_value", Double.class), (Double) value);
            case "BOOLEAN" -> step.set(table.fieldsRow().field("boolean_value", Boolean.class), (Boolean) value);
            case "STRING" -> step.set(table.fieldsRow().field("string_value", String.class), (String) value);
            case "INTEGER" -> step.set(table.fieldsRow().field("integer_value", Integer.class), (Integer) value);
            default -> throw new RuntimeException("Invalid custom property type");
        };
        step.execute();
    }


    public Result<Record> getCustomPropertyValueByCode(Class<? extends Record> recordClass, Long objectId, String customPropertyCode) {
        var table = classMapping.get(recordClass);
        return dslContext.select().from(table.join(Tables.CUSTOM_PROPERTY)
                .on(table.field(getCpIdField(table)).eq(Tables.CUSTOM_PROPERTY.ID)))
                .where(Tables.CUSTOM_PROPERTY.CODE.eq(customPropertyCode))
                .and(table.field(getObjectIdField(table)).eq(objectId))
                .fetch();
    }

    private Field<Long> getCpIdField(TableImpl<? extends CustomPropertyValueRecord> table) {
        return field(name(table.getName(), "custom_property_id"), Long.class);
    }

    private Field<Long> getObjectIdField(TableImpl<? extends CustomPropertyValueRecord> table) {
        return field(name(table.getName(), "object_id"), Long.class);
    }

}
