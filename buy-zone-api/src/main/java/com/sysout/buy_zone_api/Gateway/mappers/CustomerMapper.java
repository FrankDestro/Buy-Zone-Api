package com.sysout.buy_zone_api.Gateway.mappers;

import com.sysout.buy_zone_api.Gateway.models.entities.Customer;
import com.sysout.buy_zone_api.models.entities.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring")
public interface CustomerMapper {

    @Mapping(source = "client.fullName", target = "name")
    @Mapping(source = "client.email", target = "email")
    @Mapping(source = "client.cpf", target = "tax_id")
    Customer orderClientToPagSeguroCustomer(Order order);
}
