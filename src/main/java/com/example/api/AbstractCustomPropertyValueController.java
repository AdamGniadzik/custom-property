package com.example.api;
import com.example.domain.DomainCustomizableEntity;
import com.example.domain.customproperty.CustomPropertyService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
public abstract class AbstractCustomPropertyValueController {

    protected final CustomPropertyService customPropertyService;
    private final Class<? extends DomainCustomizableEntity> typeParameterClass;
    private final CustomPropertyMapper mapper;

    protected AbstractCustomPropertyValueController(Class<? extends DomainCustomizableEntity> typeParameterClass,
                                                    CustomPropertyService customPropertyService,
                                                    CustomPropertyMapper mapper) {
        this.typeParameterClass = typeParameterClass;
        this.customPropertyService = customPropertyService;
        this.mapper = mapper;
    }

    @RequestMapping(value = "/{id}/cp/{code}", method = RequestMethod.GET)
    @ResponseBody
    private CustomPropertyValueDto getCustomPropertyValueByCode(@PathVariable Long id, @PathVariable String code) {
        return mapper.mapCustomPropertyValue(customPropertyService.getCustomPropertyValueByCodeAndObjectId(typeParameterClass, id, code));
    }

    @RequestMapping(value = "/{id}/cp", method = RequestMethod.GET)
    @ResponseBody
    private List<CustomPropertyValueDto> getCustomPropertyValueByCode(@PathVariable Long id) {
        return customPropertyService.getCustomPropertyValueByObjectId(typeParameterClass, id)
                .stream().map(mapper::mapCustomPropertyValue).toList();
    }
}
