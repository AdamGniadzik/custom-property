package com.example.api;

import java.time.LocalDateTime;

public record PersonDto(Long id, LocalDateTime createdAt, LocalDateTime updatedAt) {
}