package com.example.domain.customproperty;

import java.time.LocalDateTime;

public record CustomPropertyBinding(Long customPropertyId, String customPropertyCode, String entityClassName, boolean enabled,
                                    LocalDateTime createdAt, LocalDateTime updatedAt) {
}
