package com.caio.financial.dto;

import com.caio.financial.entity.TypeAccount;

import java.time.LocalDateTime;
import java.util.UUID;

public record AccountResponseDTO(
        UUID accountId,
        UUID userId,
        String name,
        String cpf,
        TypeAccount typeAccount,
        LocalDateTime dateCreation
        ) {
}
