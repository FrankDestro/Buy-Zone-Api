package com.sysout.buy_zone_api.Gateway.services;

import com.sysout.buy_zone_api.Gateway.mappers.AmountMapper;
import com.sysout.buy_zone_api.Gateway.mappers.LinksMapper;
import com.sysout.buy_zone_api.Gateway.mappers.PixResponseMapper;
import com.sysout.buy_zone_api.Gateway.mappers.QrCodeMapper;
import com.sysout.buy_zone_api.Gateway.models.entities.Amount;
import com.sysout.buy_zone_api.Gateway.models.entities.Link;
import com.sysout.buy_zone_api.Gateway.models.entities.LinkQrcodes;
import com.sysout.buy_zone_api.Gateway.models.entities.QrCode;
import com.sysout.buy_zone_api.Gateway.repositories.PagSeguroPixResponseRepository;
import com.sysout.buy_zone_api.Gateway.response.PagSeguroPixResponse;
import com.sysout.buy_zone_api.enums.OrderStatus;
import com.sysout.buy_zone_api.models.entities.Order;
import com.sysout.buy_zone_api.models.entities.Payment;
import com.sysout.buy_zone_api.repository.OrderItemRepository;
import com.sysout.buy_zone_api.repository.OrderRepository;
import com.sysout.buy_zone_api.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;


@RequiredArgsConstructor
@Service
public class PixService {

    private final PagSeguroPixResponseRepository pagSeguroPixResponseRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository Orderrepository;
    private final PaymentService paymentService;

    private final PixResponseMapper pixResponseMapper;
    private final QrCodeMapper qrCodeMapper;
    private final LinksMapper linksMapper;
    private final AmountMapper amountMapper;


    public void gravarOrderPixRepository(Order order, PagSeguroPixResponse pixResponse) {

        order.setMoment(Instant.now());
        order.setStatus(OrderStatus.WAITING_PAYMENT);
        Orderrepository.save(order);
        orderItemRepository.saveAll(order.getItems());
        Payment payment = paymentService.createPayment(order);
        order.setPayment(payment);

        PagSeguroPixResponse result = pixResponseMapper.toPagSeguroPixResponse(pixResponse);

        List<QrCode> listQrCode = qrCodeMapper.toQrcodeCompleted(pixResponse.getQr_codes());
        List<String> listArrangements = qrCodeMapper.toListArrangements(pixResponse.getQr_codes().getLast().getArrangements());
        List<LinkQrcodes> linksQrcode = linksMapper.toListLinksQrcode(pixResponse.getQr_codes().get(0).getLinks());
        List<Link> linksOrder = linksMapper.toListLinks(pixResponse.getLinks());
        Amount amount = amountMapper.toAmountCompleted(pixResponse.getQr_codes().getLast().getAmount());

        listQrCode.get(0).setLinks(linksQrcode);
        listQrCode.get(0).setArrangements(listArrangements);

        result.setQr_codes(listQrCode);
        result.getQr_codes().get(0).setAmount(amount);
        result.setLinks(linksOrder);
        result.setPayment(payment);

        pagSeguroPixResponseRepository.save(result);
    }
}
