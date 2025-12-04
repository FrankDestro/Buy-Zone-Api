package com.sysout.buy_zone_api.mappers;

import com.sysout.buy_zone_api.models.dto.PropsDTO;
import com.sysout.buy_zone_api.models.entities.Props;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface PropsDTOMapper {

    PropsDTO toPropsDTO(Props props);

    Props toPropsEntity(PropsDTO propsDTO);

}
