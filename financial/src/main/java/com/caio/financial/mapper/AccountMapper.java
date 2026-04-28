package com.caio.financial.mapper;

import com.caio.financial.dto.*;
import com.caio.financial.entity.Account;
import com.caio.financial.entity.User;
import org.mapstruct.*;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring",uses = TransactionMapper.class)
public interface AccountMapper {

    public Account toEntity(AccountCreateDTO dto);

    @Mapping(source = "id",target = "accountId")
    @Mapping(source = "user.id",target = "userId")
    public AccountResponseDTO toDTO(Account account);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)

    public void updateAccount(UpdateAccountDTO dto , @MappingTarget Account account);


    @Mapping(source = "account.id",target = "accountId")
    @Mapping(source = "account.user.id",target = "userId")
    public AccountTransactionResponseDTO toResponse(Account account, List<TransactionResponseDTO> listTransaction);


    default AccountTransactionResponseDTO response(Account account,List<TransactionResponseDTO> listTransaction){

         return new AccountTransactionResponseDTO(
                 account.getId(),
                 account.getUser().getId(),
                 account.getName(),
                 account.getCpf(),
                 account.getTypeAccount(),
                 account.getDateCreation(),
                 listTransaction
         );
    }

}
