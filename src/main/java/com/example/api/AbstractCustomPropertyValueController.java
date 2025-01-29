package com.example.api;

import com.example.db.repository.CustomPropertyValueRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public abstract class AbstractCustomPropertyValueController<T> {

    private final CustomPropertyValueRepository customPropertyValueRepository;

//    @GetMapping("/{id}/cp/{code}")
//    String getCustomPropertyValueByCode(@PathVariable  Long id, @PathVariable String code){
//        return customPropertyValueRepository.createCustomPropertyValue();
//    }
}
