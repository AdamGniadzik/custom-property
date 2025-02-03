package com.example.api;

import com.example.domain.item.Item;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {

    ItemDto mapItem(Item item) {
        return new ItemDto(item.getId(), item.getCreatedAt(), item.getUpdatedAt());
    }
}
