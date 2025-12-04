package com.sysout.buy_zone_api.mappers;

import com.sysout.buy_zone_api.models.dto.UserDTO;
import com.sysout.buy_zone_api.models.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface UserDTOMapper {

    UserDTO toUserDTO(User user);
    User toUserEntity(UserDTO userDTO);

}
