package com.caio.financial.dto;

import com.caio.financial.entity.Transaction;
import com.caio.financial.entity.TypeAccount;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record AccountTransactionResponseDTO(
        UUID accountId,
        UUID userId,
        String name,
        String cpf,
        TypeAccount typeAccount,
        LocalDateTime dateCreation,
       List<TransactionResponseDTO> transactions
) {
}
