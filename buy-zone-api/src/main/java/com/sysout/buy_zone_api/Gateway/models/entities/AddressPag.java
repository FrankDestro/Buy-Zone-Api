package com.sysout.buy_zone_api.Gateway.models.entities;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Embeddable
public class AddressPag {

    private String street;
    private String number;
    private String complement;
    private String locality;
    private String city;
    private String region_code;
    private String country;
    private String postal_code;
    private String region;
}
