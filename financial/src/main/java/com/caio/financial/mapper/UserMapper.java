package com.caio.financial.mapper;

import com.caio.financial.dto.UserCreateDTO;
import com.caio.financial.dto.UserResponseDTO;
import com.caio.financial.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    public User toEntity(UserCreateDTO dto);

    @Mapping(source = "id",target = "userId")
    public UserResponseDTO toDTO(User user);
}
