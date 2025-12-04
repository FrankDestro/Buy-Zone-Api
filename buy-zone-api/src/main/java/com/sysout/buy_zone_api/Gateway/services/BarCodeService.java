package com.sysout.buy_zone_api.Gateway.services;

import com.sysout.buy_zone_api.Gateway.mappers.BarCodeResponseMapper;
import com.sysout.buy_zone_api.Gateway.response.PagSeguroBarCodeResponse;
import com.sysout.buy_zone_api.enums.OrderStatus;
import com.sysout.buy_zone_api.models.entities.Order;
import com.sysout.buy_zone_api.models.entities.Payment;
import com.sysout.buy_zone_api.repository.OrderItemRepository;
import com.sysout.buy_zone_api.repository.OrderRepository;
import com.sysout.buy_zone_api.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@RequiredArgsConstructor
@Service
public class BarCodeService {

    private final PaymentService paymentService;
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository Orderrepository;
    private final BarCodeResponseMapper barCodeResponseMapper;

    public void gravarOrderBarCodeRepository(Order order, PagSeguroBarCodeResponse barCodeResponse) {

        order.setMoment(Instant.now());
        order.setStatus(OrderStatus.WAITING_PAYMENT);
        Orderrepository.save(order);
        orderItemRepository.saveAll(order.getItems());
        Payment payment = paymentService.createPayment(order);
        order.setPayment(payment);

        PagSeguroBarCodeResponse result = barCodeResponseMapper.toBarCodeResponse(barCodeResponse);











    }


}
