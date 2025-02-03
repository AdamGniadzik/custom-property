package com.example.db.repository;

import com.example.db.DatabaseConflictException;
import com.example.db.generated.Tables;
import com.example.db.generated.tables.records.PersonRecord;
import com.example.domain.person.Person;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
@AllArgsConstructor
public class PersonRepository {

    private final DSLContext dslContext;

    public Person createPerson() {
        return dslContext.insertInto(Tables.PERSON)
                .set(Tables.PERSON.CREATED_AT, LocalDateTime.now())
                .set(Tables.PERSON.UPDATED_AT, LocalDateTime.now())
                .returning()
                .fetchInto(PersonRecord.class).stream().map(this::mapPerson).findFirst().orElseThrow(DatabaseConflictException::new);
    }


    private Person mapPerson(PersonRecord PersonRecord) {
        return Person.builder()
                .id(PersonRecord.getId())
                .createdAt(PersonRecord.getCreatedAt())
                .updatedAt(PersonRecord.getUpdatedAt())
                .build();
    }
}
