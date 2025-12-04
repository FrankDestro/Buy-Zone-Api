package com.sysout.buy_zone_api.Gateway.request;

import com.sysout.buy_zone_api.Gateway.models.entities.Charge;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BarCodeOrderRequest extends PaymentOrderRequest{
    private List<Charge> charges;
}
