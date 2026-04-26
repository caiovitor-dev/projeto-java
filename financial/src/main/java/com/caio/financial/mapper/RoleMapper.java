package com.caio.financial.mapper;

import com.caio.financial.dto.RoleCreateDTO;
import com.caio.financial.entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    public Role toEntity(RoleCreateDTO dto);
}
