package com.sysout.buy_zone_api.factory;

import com.sysout.buy_zone_api.mappers.AddressDTOMapper;
import com.sysout.buy_zone_api.models.dto.AddressDTO;
import com.sysout.buy_zone_api.models.entities.Address;
import org.springframework.beans.factory.annotation.Autowired;

public class AddressFactory {

    @Autowired
    private AddressDTOMapper addressDTOMapper;

    public static Address createAddress() {
        Address address = new Address(1L, "AV. Noventa e nove", 355, "Apt", "Jardim Cem",
                "Guarulhos", "SP", "BRA", "00090-001", true, UserFactory.createUserClient());
        return address;
    }

    public static AddressDTO createAddressDTO() {
        AddressDTO addressDTO = new AddressDTO(1L, "AV. Noventa e nove", 355, "Apt", "Jardim Cem",
                "Guarulhos", "SP", "BRA", "00090-001", true);
        return addressDTO;
    }
}
