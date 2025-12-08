package com.sysout.buy_zone_api.factory;


import com.sysout.buy_zone_api.models.dto.PhoneDTO;
import com.sysout.buy_zone_api.models.entities.Phone;

public class PhoneFactory {

    public static Phone createPhone() {
        Phone phone = new Phone(1L, 55, 11, 982364758, "MOBILE", UserFactory.createUserClient());
        return phone;
    }

    public static PhoneDTO createPhoneDTO() {
        Phone phone = createPhone();
        PhoneDTO phoneDTO = new PhoneDTO(1L, 55, 11, 982364758, "MOBILE", UserFactory.createUserClient().getId());
        return phoneDTO;
    }
}


