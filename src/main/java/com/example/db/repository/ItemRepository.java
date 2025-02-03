package com.example.db.repository;

import com.example.db.DatabaseConflictException;
import com.example.db.generated.Tables;
import com.example.db.generated.tables.records.ItemRecord;
import com.example.domain.item.Item;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
@AllArgsConstructor
public class ItemRepository {

    private final DSLContext dslContext;


    public Item createItem() {
        return dslContext.insertInto(Tables.ITEM)
                .set(Tables.ITEM.CREATED_AT, LocalDateTime.now())
                .set(Tables.ITEM.UPDATED_AT, LocalDateTime.now())
                .returning()
                .fetchInto(ItemRecord.class).stream().map(this::mapItem).findFirst().orElseThrow(DatabaseConflictException::new);
    }


    private Item mapItem(ItemRecord itemRecord) {
        return Item.builder()
                .id(itemRecord.getId())
                .createdAt(itemRecord.getCreatedAt())
                .updatedAt(itemRecord.getUpdatedAt())
                .build();
    }
}
