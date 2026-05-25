package com.caio.financial.mapper;

import com.caio.financial.dto.TransferCreateDTO;
import com.caio.financial.dto.TransferReponseDTO;
import com.caio.financial.entity.Transfer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = AccountMapper.class)
public interface TransferMapper {

     Transfer toEntity(TransferCreateDTO dto);

    @Mapping(source = "id",target = "transferId")
    @Mapping(source = "user.id",target = "userId")
    @Mapping(source = "dateCreation",target = "dateTransfer")
    @Mapping(source = "originAccount.id",target = "originAccountId")
    @Mapping(source = "originAccount.name",target = "nameOriginAccount")
    @Mapping(source = "destinyAccount.id",target = "destinyAccountId")
    @Mapping(source = "destinyAccount.name",target = "nameDestinyAccount")
    TransferReponseDTO toDTO(Transfer transfer);
}
