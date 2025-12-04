package com.sysout.buy_zone_api.Gateway.response;

import com.sysout.buy_zone_api.Gateway.models.entities.*;
import lombok.Data;

import java.util.List;

@Data
public class PagSeguroBarCodeResponse {
    private String id;
    private String reference_id;
    private String created_at;
    private Customer customer;
    private List<Item> items;
    private Shipping shipping;
    private List<Charge> charges;
    private List<String> notification_urls;
    private List<Link> links;
}

