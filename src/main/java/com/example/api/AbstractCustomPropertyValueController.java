package com.example.api;
import com.example.domain.DomainCustomizableEntity;
import com.example.domain.customproperty.CustomPropertyService;
import com.example.domain.customproperty.CustomPropertyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
public abstract class AbstractCustomPropertyValueController {

    protected final CustomPropertyService customPropertyService;
    private final Class<? extends DomainCustomizableEntity> typeParameterClass;

    protected AbstractCustomPropertyValueController(Class<? extends DomainCustomizableEntity> typeParameterClass,
                                                 @Autowired CustomPropertyService customPropertyService) {
        this.typeParameterClass = typeParameterClass;
        this.customPropertyService = customPropertyService;
    }

    @RequestMapping(value = "/{id}/cp/{code}", method = RequestMethod.GET)
    @ResponseBody
    private CustomPropertyValue getCustomPropertyValueByCode(@PathVariable Long id, @PathVariable String code) {
        return customPropertyService.getCustomPropertyValueByCodeAndObjectId(typeParameterClass, id, code);
    }

    @RequestMapping(value = "/{id}/cp", method = RequestMethod.GET)
    @ResponseBody
    private List<CustomPropertyValue> getCustomPropertyValueByCode(@PathVariable Long id) {
        return customPropertyService.getCustomPropertyValueByObjectId(typeParameterClass, id);
    }
}
