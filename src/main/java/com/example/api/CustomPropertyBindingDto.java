package com.example.api;

import java.time.LocalDateTime;

public record CustomPropertyBindingDto(String code, String entityName, LocalDateTime createdAt, LocalDateTime updatedAt,
                                       boolean enabled) {
}
