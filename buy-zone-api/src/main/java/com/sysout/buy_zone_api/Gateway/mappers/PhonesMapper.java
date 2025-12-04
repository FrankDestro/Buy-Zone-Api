package com.sysout.buy_zone_api.Gateway.mappers;

import com.sysout.buy_zone_api.Gateway.models.entities.PhonePag;
import com.sysout.buy_zone_api.models.entities.Phone;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel="spring")
public interface PhonesMapper {

    List<PhonePag> orderPhonesToPagSeguroPhones(List<Phone> orderPhones);

}
