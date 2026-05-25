package com.caio.financial.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionCreateDTO(
        BigDecimal value,
        String description,
        UUID categoryId,
        UUID accountId) {
}
