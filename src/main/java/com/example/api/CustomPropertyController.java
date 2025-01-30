package com.example.api;

import com.example.api.request.CreateCustomPropertyBindingRequest;
import com.example.api.request.CreateCustomPropertyRequest;
import com.example.domain.customproperty.CustomPropertyService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
@AllArgsConstructor
public class CustomPropertyController {

    private final CustomPropertyService customPropertyService;
    private final CustomPropertyMapper mapper;

    @PostMapping("/")
    public CustomPropertyDto createCustomProperty(CreateCustomPropertyRequest request) {
        return mapper.mapCustomProperty(customPropertyService.createCustomProperty(request.code(), request.type()));
    }

    @PostMapping("/binding")
    public CustomPropertyBindingDto createCustomPropertyBinding(CreateCustomPropertyBindingRequest request) {
        return mapper.mapCustomPropertyBindingDto(customPropertyService.createCustomPropertyBinding(request.code(),
                request.entityName(), request.enabled()));
    }


}
