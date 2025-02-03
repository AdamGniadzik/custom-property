package com.example.api;

import java.time.LocalDateTime;

public record ItemDto(Long id, LocalDateTime createdAt, LocalDateTime updatedAt) {
}
