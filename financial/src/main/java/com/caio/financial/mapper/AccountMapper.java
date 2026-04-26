package com.caio.financial.mapper;

import com.caio.financial.dto.AccountCreateDTO;
import com.caio.financial.dto.AccountResponseDTO;
import com.caio.financial.dto.AccountTransactionResponseDTO;
import com.caio.financial.dto.UpdateAccountDTO;
import com.caio.financial.entity.Account;
import com.caio.financial.entity.User;
import org.mapstruct.*;

import java.util.UUID;

@Mapper(componentModel = "spring",uses = TransactionMapper.class)
public interface AccountMapper {

    public Account toEntity(AccountCreateDTO dto);

    @Mapping(source = "id",target = "accountId")
    @Mapping(source = "user.id",target = "userId")
    public AccountResponseDTO toDTO(Account account);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)

    public void updateAccount(UpdateAccountDTO dto , @MappingTarget Account account);

    public AccountTransactionResponseDTO toResponse(Account account);

}
