package com.sysout.buy_zone_api.Gateway.mappers;

import com.sysout.buy_zone_api.Gateway.models.entities.Amount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface AmountMapper {

    @Mappings({
            @Mapping(target = "summary", ignore = true)
    })
    Amount toAmountCompleted(Amount amount);

}
