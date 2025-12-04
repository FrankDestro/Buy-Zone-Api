package com.sysout.buy_zone_api.Gateway.strategy;

import com.sysout.buy_zone_api.Gateway.integration.PagSeguroPixClient;
import com.sysout.buy_zone_api.Gateway.models.entities.Amount;
import com.sysout.buy_zone_api.Gateway.models.entities.QrCode;
import com.sysout.buy_zone_api.Gateway.request.PaymentOrderRequest;
import com.sysout.buy_zone_api.Gateway.request.PixOrderRequest;
import com.sysout.buy_zone_api.Gateway.response.PagSeguroPixResponse;
import com.sysout.buy_zone_api.Gateway.services.PixService;
import com.sysout.buy_zone_api.Gateway.utils.Functions;
import com.sysout.buy_zone_api.Gateway.utils.OrderDetailsService;
import com.sysout.buy_zone_api.enums.PaymentMethod;
import com.sysout.buy_zone_api.models.dto.OrderDTO;
import com.sysout.buy_zone_api.models.entities.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Primary
@Component
@RequiredArgsConstructor
public class PixPaymentStrategy implements PaymentStrategy{

    @Value("${PAGSEGURO_AUTH_TOKEN}")
    private String authorizationToken;

    private final PixService pixService;
    private final PagSeguroPixClient pagSeguroPixClient;
    private final Functions functions;

    private final OrderDetailsService orderDetailsService;

    @Override
    public PaymentMethod getType() {
        return PaymentMethod.PIX;
    }

    @Override
    public OrderDTO processPayment(Order order) {

        String bearerToken = "Bearer " + authorizationToken;

        PixOrderRequest pixOrderRequest = createPixOrderRequest(order);
        PagSeguroPixResponse pixResponse = pagSeguroPixClient.createPixOrder(bearerToken, pixOrderRequest);

        if (pixResponse != null && !pixResponse.getId().isEmpty()) {
            pixService.gravarOrderPixRepository(order, pixResponse);
        } else {
            throw new RuntimeException("Error processing payment with Pix.");
        }
        return new OrderDTO(order);
    }

    private PixOrderRequest createPixOrderRequest(Order order) {
        PixOrderRequest request = new PixOrderRequest();
        PaymentOrderRequest commonRequest = orderDetailsService.createCommonOrderDetails(order);
        orderDetailsService.copyCommonOrderDetails(commonRequest, request);

        List<QrCode> qr_codes = new ArrayList<>();
        QrCode qrCode = new QrCode();
        Amount amount = new Amount();
        amount.setValue(functions.converterValueDoubleToBigDecimalNoDecimal(order.getTotalSum()));
        qrCode.setAmount(amount);
        Instant expirationDate = Instant.now().plus(Duration.ofMinutes(15));
        qrCode.setExpiration_date(expirationDate.toString());
        qr_codes.add(qrCode);
        request.setQr_codes(qr_codes);

        return request;
    }
}
