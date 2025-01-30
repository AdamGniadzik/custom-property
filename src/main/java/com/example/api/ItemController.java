package com.example.api;

import com.example.domain.customproperty.CustomPropertyService;
import com.example.domain.item.Item;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/item")
public class ItemController extends AbstractCustomPropertyValueController {

    public ItemController(CustomPropertyService customPropertyService) {
        super(Item.class, customPropertyService);
    }
}
