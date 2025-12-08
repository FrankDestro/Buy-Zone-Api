package com.sysout.buy_zone_api.services;

import com.sysout.buy_zone_api.enums.PaymentStatus;
import com.sysout.buy_zone_api.models.entities.Order;
import com.sysout.buy_zone_api.models.entities.Payment;
import com.sysout.buy_zone_api.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public Payment createPayment(Order order) {
        Payment payment = new Payment();
        payment.setPaymentStatus(PaymentStatus.WAITING_PAYMENT);
        payment.setPaymentDate(LocalDateTime.now());
        payment.setAmount(BigDecimal.valueOf(order.getTotalSum()));
        payment.setOrder(order);
        Payment savedPayment = paymentRepository.save(payment);

        return savedPayment;
    }
}
