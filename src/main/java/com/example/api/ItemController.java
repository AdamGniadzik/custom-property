package com.example.api;

import com.example.domain.customproperty.CustomPropertyService;
import com.example.domain.item.Item;
import com.example.domain.item.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/item")
public class ItemController extends AbstractCustomPropertyValueController {

    private final ItemService itemService;
    private final ItemMapper itemMapper;

    @Autowired
    public ItemController(CustomPropertyService customPropertyService, CustomPropertyMapper mapper,
                          ItemService itemService, ItemMapper itemMapper) {
        super(Item.class, customPropertyService, mapper);
        this.itemService = itemService;
        this.itemMapper = itemMapper;
    }


    @PostMapping("/")
    @ResponseBody
    public ItemDto createNewItem() {
        return itemMapper.mapItem(itemService.createNewItem());
    }

}
