package com.sysout.buy_zone_api.Gateway.strategy;


import com.sysout.buy_zone_api.enums.PaymentMethod;
import com.sysout.buy_zone_api.models.dto.OrderDTO;
import com.sysout.buy_zone_api.models.entities.Order;

public interface PaymentStrategy {
    PaymentMethod getType();
    OrderDTO processPayment(Order order);
}
