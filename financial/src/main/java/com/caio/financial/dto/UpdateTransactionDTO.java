package com.caio.financial.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record UpdateTransactionDTO(BigDecimal value, String description, UUID accountId, UUID categoryId) {
}
