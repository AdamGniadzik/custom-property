package com.example.api;

import com.example.api.request.CreateCustomPropertyBindingRequest;
import com.example.api.request.CreateCustomPropertyRequest;
import com.example.domain.customproperty.CustomPropertyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/")
    @ResponseBody
    public List<CustomPropertyDto> getAllCustomProperties(){
        return customPropertyService.getAllCustomProperties().stream().map(mapper::mapCustomProperty).collect(Collectors.toList());
    }

    @PostMapping("/binding")
    @ResponseBody
    public CustomPropertyBindingDto createCustomPropertyBinding(@RequestBody CreateCustomPropertyBindingRequest request) {
        return mapper.mapCustomPropertyBindingDto(customPropertyService.createCustomPropertyBinding(request.code(),
                request.entityName(), request.enabled()));
    }

    @PutMapping("/binding")
    @ResponseBody
    public CustomPropertyBindingDto updateCustomPropertyBinding(@RequestBody CreateCustomPropertyBindingRequest request) {
        return mapper.mapCustomPropertyBindingDto(customPropertyService.updateCustomPropertyBinding(request.code(),
                request.entityName(), request.enabled()));
    }



}
