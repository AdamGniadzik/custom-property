package com.example.db.repository;

import com.example.db.generated.Tables;
import com.example.db.generated.enums.CustomPropertyEnum;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class CustomPropertyRepository {


    private final DSLContext dslContext;

    public void test() {
        dslContext.insertInto(Tables.CUSTOM_PROPERTY).set(Tables.CUSTOM_PROPERTY.ID, 10)
                .set(Tables.CUSTOM_PROPERTY.CODE, "TEST_CODE")
                .set(Tables.CUSTOM_PROPERTY.TYPE, CustomPropertyEnum.STRING)
                .execute();
    }

}
