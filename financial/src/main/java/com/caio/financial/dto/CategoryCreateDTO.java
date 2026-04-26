package com.caio.financial.dto;

import com.caio.financial.entity.TypeCategory;

import java.util.UUID;

public record CategoryCreateDTO(String name, TypeCategory typeCategory) {
}
