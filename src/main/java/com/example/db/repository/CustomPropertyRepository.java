package com.example.db.repository;

import com.example.db.generated.Tables;
import com.example.db.generated.tables.records.CustomPropertyRecord;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class CustomPropertyRepository {


    private final DSLContext dslContext;

    public CustomPropertyRecord createCustomProperty(String code, String type) {
        return dslContext.insertInto(Tables.CUSTOM_PROPERTY).set(Tables.CUSTOM_PROPERTY.CODE, code)
                .set(Tables.CUSTOM_PROPERTY.TYPE, type).returning().fetchSingle();
    }

    public List<CustomPropertyRecord> getCustomProperties () {
        return dslContext.select().from(Tables.CUSTOM_PROPERTY).fetchInto(Tables.CUSTOM_PROPERTY);
    }

}
