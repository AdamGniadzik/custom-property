package com.example.api;

import com.example.domain.DomainCustomizableEntity;
import com.example.domain.customproperty.CustomPropertyService;
import com.example.domain.item.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/item")
public class ItemController extends AbstractCustomPropertyValueController {

    @Autowired
    public ItemController(CustomPropertyService customPropertyService, CustomPropertyMapper mapper) {
        super(Item.class, customPropertyService, mapper);
    }
}
