package com.example.api;

import com.example.api.request.CreateCustomPropertyBindingRequest;
import com.example.api.request.CreateCustomPropertyRequest;
import com.example.domain.customproperty.CustomPropertyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/cp")
@AllArgsConstructor
public class CustomPropertyController {

    private final CustomPropertyService customPropertyService;
    private final CustomPropertyMapper mapper;

    @PostMapping("/")
    @ResponseBody
    public CustomPropertyDto createCustomProperty(@RequestBody CreateCustomPropertyRequest request) {
        return mapper.mapCustomProperty(customPropertyService.createCustomProperty(request.code(), request.type().name()));
    }

    @PostMapping("/binding")
    @ResponseBody
    public CustomPropertyBindingDto createCustomPropertyBinding(@RequestBody CreateCustomPropertyBindingRequest request) {
        return mapper.mapCustomPropertyBindingDto(customPropertyService.createCustomPropertyBinding(request.code(),
                request.entityName(), request.enabled()));
    }


}
