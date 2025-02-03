package com.example.domain.person;

import com.example.domain.DomainCustomizableEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class Person implements DomainCustomizableEntity {
    Long id;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
