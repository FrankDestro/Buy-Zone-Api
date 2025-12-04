package com.sysout.buy_zone_api.models.dto;

import com.sysout.buy_zone_api.Gateway.response.PagSeguroPixResponse;
import com.sysout.buy_zone_api.enums.PaymentStatus;
import com.sysout.buy_zone_api.models.entities.Payment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class PaymentDTO {
    private Long id_payment;
    private PaymentStatus paymentStatus;
    private LocalDateTime paymentDate;
    private BigDecimal amount;
    private PagSeguroPixResponse pixResponse;

    public PaymentDTO(Payment entity) {
        id_payment = entity.getId_payment();
        paymentStatus = entity.getPaymentStatus();
        paymentDate = entity.getPaymentDate();
        amount = entity.getAmount();
        pixResponse = entity.getPixResponse();
    }
}
