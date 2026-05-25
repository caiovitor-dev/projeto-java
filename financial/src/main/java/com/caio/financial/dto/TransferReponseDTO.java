package com.caio.financial.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransferReponseDTO (
        UUID transferId,
        BigDecimal valueTransfer,
        LocalDateTime dateTransfer,
        UUID userId,
        UUID originAccountId,
        String nameOriginAccount,
        UUID destinyAccountId,
        String nameDestinyAccount
){
}
