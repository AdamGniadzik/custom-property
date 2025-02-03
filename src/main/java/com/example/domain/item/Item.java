package com.example.domain.item;

import com.example.domain.DomainCustomizableEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class Item implements DomainCustomizableEntity {
    Long id;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
