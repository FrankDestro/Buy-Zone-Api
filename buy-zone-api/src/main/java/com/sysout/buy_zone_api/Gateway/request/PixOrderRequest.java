package com.sysout.buy_zone_api.Gateway.request;

import com.sysout.buy_zone_api.Gateway.models.entities.QrCode;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PixOrderRequest extends PaymentOrderRequest {
    private List<QrCode> qr_codes;

}


