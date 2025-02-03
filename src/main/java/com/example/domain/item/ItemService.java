package com.example.domain.item;

import com.example.db.repository.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public Item createNewItem() {
        return itemRepository.createItem();
    }
}
