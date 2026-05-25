package com.caio.financial.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferCreateDTO(BigDecimal valueTransfer, UUID originAccountId, UUID destinyAccountId){
}
