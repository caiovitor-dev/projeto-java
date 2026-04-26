package com.caio.financial.dto;

import com.caio.financial.entity.TypeAccount;

import java.util.UUID;

public record AccountCreateDTO (String name,String cpf, TypeAccount typeAccount){
}
