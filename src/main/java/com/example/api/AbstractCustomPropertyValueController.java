package com.example.api;

import com.example.api.request.CreateCustomPropertyValueRequest;
import com.example.domain.DomainCustomizableEntity;
import com.example.domain.customproperty.CustomPropertyService;
import org.springframework.web.bind.annotation.*;

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
        return mapper.mapCustomPropertyValue(customPropertyService
                .getCustomPropertyValueByCodeAndObjectId(typeParameterClass, id, code));
    }

    @RequestMapping(value = "/{id}/cp", method = RequestMethod.GET)
    @ResponseBody
    private List<CustomPropertyValueDto> getCustomPropertyValues(@PathVariable Long id) {
        return customPropertyService.getCustomPropertyValuesByObjectId(typeParameterClass, id)
                .stream().map(mapper::mapCustomPropertyValue).toList();
    }

    @RequestMapping(value = "/{id}/cp", method = RequestMethod.POST)
    @ResponseBody
    private CustomPropertyValueDto createCustomPropertyValue(@PathVariable Long id, @RequestBody CreateCustomPropertyValueRequest request) {
        return mapper.mapCustomPropertyValue(customPropertyService
                .createCustomPropertyValue(typeParameterClass, id, request.code(), request.value()));
    }

    @RequestMapping(value = "/{id}/cp", method = RequestMethod.PUT)
    @ResponseBody
    private CustomPropertyValueDto createOrUpdateCustomPropertyValue(@PathVariable Long id, @RequestBody CreateCustomPropertyValueRequest request) {
        return mapper.mapCustomPropertyValue(customPropertyService
                .createOrUpdateCustomPropertyValue(typeParameterClass, id, request.code(), request.value()));
    }
}
