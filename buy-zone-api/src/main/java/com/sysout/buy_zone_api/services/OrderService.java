package com.sysout.buy_zone_api.services;

import com.sysout.buy_zone_api.Gateway.strategy.PaymentStrategy;
import com.sysout.buy_zone_api.models.dto.AddressDTO;
import com.sysout.buy_zone_api.models.dto.OrderDTO;
import com.sysout.buy_zone_api.models.dto.OrderItemDTO;
import com.sysout.buy_zone_api.models.entities.*;
import com.sysout.buy_zone_api.repository.AddressRepository;
import com.sysout.buy_zone_api.repository.OrderRepository;
import com.sysout.buy_zone_api.repository.ProductRepository;
import com.sysout.buy_zone_api.services.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository repository;
    private final ProductRepository productRepository;
    private final AddressRepository addressRepository;
    private final UserService userService;

    private final List<PaymentStrategy> paymentStrategies;

    @Autowired
    private PaymentStrategy paymentStrategy;

    @Transactional(readOnly = true)
    public OrderDTO findById(Long id) {
        Order order = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Resource not found"));
        return new OrderDTO(order);
    }

    @Transactional
    public OrderDTO recordOrder(OrderDTO orderDTO) {
        Order order = getOrder(orderDTO);
        PaymentStrategy paymentStrategy = paymentStrategies.stream().filter(strategy -> strategy.getType() == orderDTO.getPaymentMethod()).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Método de pagamento não suportado: " + orderDTO.getPaymentMethod()));
        return paymentStrategy.processPayment(order);
    }

    private Order getOrder(OrderDTO dto) {
        Order order = new Order();
        order.setPaymentMethod(dto.getPaymentMethod());
        User user = userService.authenticated();
        order.setClient(user);
        AddressDTO address = dto.getAddress();
        Address addressShipping = addressRepository.getReferenceById(address.getId());
        order.setAddress(addressShipping);

        double total = 0.0;

        for (OrderItemDTO itemDto : dto.getItems()) {
            Product product = productRepository.getReferenceById(itemDto.getProductId());
            double subtotal = product.getPrice() * itemDto.getQuantity();
            total += subtotal;
            OrderItem item = new OrderItem(order, product, itemDto.getQuantity(), product.getPrice(), subtotal, product.getName());
            order.getItems().add(item);
        }
        order.setTotalSum(total);
        return order;
    }
}
