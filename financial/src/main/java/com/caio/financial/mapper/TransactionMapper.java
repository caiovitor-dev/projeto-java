package com.caio.financial.mapper;

import com.caio.financial.dto.TransactionCreateDTO;
import com.caio.financial.dto.TransactionResponseDTO;
import com.caio.financial.dto.UpdateTransactionDTO;
import com.caio.financial.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring",uses = UserMapper.class)
public interface TransactionMapper{

    public Transaction toEntity(TransactionCreateDTO dto);

    public Transaction toEntity(UpdateTransactionDTO dto);

    @Mapping(source = "id",target = "transactionId")
    @Mapping(source = "category.id",target = "categoryId")
    @Mapping(source = "account.id",target = "accountId")
    public TransactionResponseDTO toDTO(Transaction transaction);



}
