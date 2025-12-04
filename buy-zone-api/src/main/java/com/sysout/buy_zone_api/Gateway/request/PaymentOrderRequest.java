package com.sysout.buy_zone_api.Gateway.request;

import com.sysout.buy_zone_api.Gateway.models.entities.Customer;
import com.sysout.buy_zone_api.Gateway.models.entities.Item;
import com.sysout.buy_zone_api.Gateway.models.entities.Shipping;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PaymentOrderRequest {

    private String reference_id;
    private Customer customer;
    private List<Item> items;
    private Shipping shipping;
    private List<String> notification_urls;
}
