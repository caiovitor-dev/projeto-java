package com.caio.financial.dto;

import com.caio.financial.entity.TypeAccount;

import java.math.BigDecimal;
import java.util.UUID;

public record AccountExpensesResponseDTO (
        UUID accountId,
        UUID userId,
        String name,
        String cpf,
        TypeAccount typeAccount,
        BigDecimal totalExpense
        ){
}
