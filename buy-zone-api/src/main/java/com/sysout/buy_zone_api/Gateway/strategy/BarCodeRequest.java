package com.sysout.buy_zone_api.Gateway.strategy;



import com.sysout.buy_zone_api.Gateway.integration.PagSeguroBarCodeClient;
import com.sysout.buy_zone_api.Gateway.mappers.ChargeMapper;
import com.sysout.buy_zone_api.Gateway.models.entities.Charge;
import com.sysout.buy_zone_api.Gateway.request.BarCodeOrderRequest;
import com.sysout.buy_zone_api.Gateway.request.PaymentOrderRequest;
import com.sysout.buy_zone_api.Gateway.response.PagSeguroBarCodeResponse;
import com.sysout.buy_zone_api.Gateway.services.BarCodeService;
import com.sysout.buy_zone_api.Gateway.utils.Functions;
import com.sysout.buy_zone_api.Gateway.utils.OrderDetailsService;
import com.sysout.buy_zone_api.enums.PaymentMethod;
import com.sysout.buy_zone_api.models.dto.OrderDTO;
import com.sysout.buy_zone_api.models.entities.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BarCodeRequest implements PaymentStrategy{

    @Value("${PAGSEGURO_AUTH_TOKEN}")
    private String authorizationToken;

    private final Functions functions;

    private final OrderDetailsService orderDetailsService;

    private final PagSeguroBarCodeClient pagSeguroBarCodeClient;

    private final BarCodeService barCodeService;

    @Override
    public PaymentMethod getType() {
        return PaymentMethod.BARCODE;
    }

    @Override
    public OrderDTO processPayment(Order order) {

        String bearerToken = "Bearer " + authorizationToken;

        BarCodeOrderRequest barCodeOrderRequest = createBarCodeOrderRequest(order);
        PagSeguroBarCodeResponse barCodeResponse = pagSeguroBarCodeClient.createBarCodeOrder(bearerToken, barCodeOrderRequest);

        if (barCodeResponse != null && !barCodeResponse.getId().isEmpty()) {
            barCodeService.gravarOrderBarCodeRepository(order, barCodeResponse);
        } else {
            throw new RuntimeException("Error processing payment with BarCode.");
        }

        return  new OrderDTO(order);
    }

    private BarCodeOrderRequest createBarCodeOrderRequest(Order order) {
        BarCodeOrderRequest request = new BarCodeOrderRequest();
        PaymentOrderRequest commonRequest = orderDetailsService.createCommonOrderDetails(order);
        orderDetailsService.copyCommonOrderDetails(commonRequest, request);

        List<Charge> chargeList = new ArrayList<>();
        Charge charge = ChargeMapper.INSTANCE.orderToCharge(order, request, functions);
        chargeList.add(charge);
        request.setCharges(chargeList);
        return request;
    }

}

