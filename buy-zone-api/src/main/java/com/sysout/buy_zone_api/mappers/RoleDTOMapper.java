package com.sysout.buy_zone_api.mappers;

import com.sysout.buy_zone_api.models.dto.RoleDTO;
import com.sysout.buy_zone_api.models.entities.Role;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel="spring")
public interface RoleDTOMapper {
    List<RoleDTO> toRoleDTOList(List<Role> roleList);
}
