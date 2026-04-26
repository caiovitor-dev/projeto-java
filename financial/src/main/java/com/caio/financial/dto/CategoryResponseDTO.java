package com.caio.financial.dto;

import com.caio.financial.entity.TypeCategory;

import java.time.LocalDateTime;
import java.util.UUID;

public record CategoryResponseDTO(
        UUID categoryId,
        String name,
        TypeCategory typeCategory,
        LocalDateTime dateCreation,
        UserResponseDTO user
) {
}
