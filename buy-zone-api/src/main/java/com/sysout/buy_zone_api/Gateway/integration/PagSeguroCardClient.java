package com.sysout.buy_zone_api.Gateway.integration;

import com.sysout.buy_zone_api.Gateway.request.CardCreditOrderRequest;
import com.sysout.buy_zone_api.Gateway.response.PagSeguroCreditCardResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Component
@FeignClient(name = "pagseguro-credit-card", url = "https://sandbox.api.pagseguro.com")
public interface PagSeguroCardClient {

    @PostMapping("/orders")
    PagSeguroCreditCardResponse createCreditCardOrder(
            @RequestHeader("Authorization") String bearerToken,
            @RequestBody CardCreditOrderRequest cardCreditOrderRequest
    );
}