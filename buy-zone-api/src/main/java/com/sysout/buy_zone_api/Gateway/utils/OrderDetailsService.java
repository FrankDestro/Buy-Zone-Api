package com.sysout.buy_zone_api.Gateway.utils;


import com.sysout.buy_zone_api.Gateway.mappers.CustomerMapper;
import com.sysout.buy_zone_api.Gateway.mappers.ItemsMapper;
import com.sysout.buy_zone_api.Gateway.mappers.PhonesMapper;
import com.sysout.buy_zone_api.Gateway.mappers.ShippingMapper;
import com.sysout.buy_zone_api.Gateway.models.entities.Customer;
import com.sysout.buy_zone_api.Gateway.request.PaymentOrderRequest;
import com.sysout.buy_zone_api.models.entities.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderDetailsService {

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private PhonesMapper phonesMapper;

    @Autowired
    private ItemsMapper itemsMapper;

    @Autowired
    private ShippingMapper shippingMapper;

    public PaymentOrderRequest createCommonOrderDetails(Order order) {
        PaymentOrderRequest request = new PaymentOrderRequest();

        Customer customer = customerMapper.orderClientToPagSeguroCustomer(order);
        customer.setPhones(phonesMapper.orderPhonesToPagSeguroPhones(order.getClient().getPhones()));
        request.setCustomer(customer);
        request.setItems(itemsMapper.orderItemsToItems(order.getItems()));

        List<String> listNotificationUrl = new ArrayList<>();
        listNotificationUrl.add("https://meusite.com/notificacoes");
        request.setNotification_urls(listNotificationUrl);

        request.setShipping(shippingMapper.orderAddressToPagSeguroShipping(order.getAddress()));
        return request;
    }

    public void copyCommonOrderDetails(PaymentOrderRequest commonRequest, PaymentOrderRequest specificRequest) {
        specificRequest.setCustomer(commonRequest.getCustomer());
        specificRequest.setItems(commonRequest.getItems());
        specificRequest.setNotification_urls(commonRequest.getNotification_urls());
        specificRequest.setShipping(commonRequest.getShipping());
    }
}

