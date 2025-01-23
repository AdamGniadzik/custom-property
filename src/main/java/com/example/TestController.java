package com.example;

import com.example.db.repository.CustomPropertyRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/")
@AllArgsConstructor
public class TestController {

    private final CustomPropertyRepository customPropertyRepository;

    @GetMapping("/")
    public void test(){
        customPropertyRepository.test();
    }
}
