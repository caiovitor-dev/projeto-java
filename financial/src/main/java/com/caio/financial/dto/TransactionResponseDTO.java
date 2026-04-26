package com.caio.financial.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionResponseDTO(
        UUID transactionId,
        String description,
        BigDecimal value,
        LocalDateTime dateCreation,
        LocalDateTime dateUpdate,
        UUID accountId,
        UUID categoryId,
        UserResponseDTO user
) {
}
