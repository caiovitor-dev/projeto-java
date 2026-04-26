package com.caio.financial.dto;

import com.caio.financial.entity.TypeAccount;

import java.math.BigDecimal;


public record UpdateAccountDTO (String cpf, BigDecimal currentBalance, String name, TypeAccount typeAccount){
}
