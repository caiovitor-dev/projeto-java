package com.caio.financial.dto;

import com.caio.financial.entity.TypeCategory;

import java.util.UUID;

public record UpdateCategoryDTO(String name, TypeCategory typeCategory) {
}
