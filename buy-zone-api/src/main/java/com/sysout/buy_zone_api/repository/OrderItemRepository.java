package com.sysout.buy_zone_api.repository;

import com.sysout.buy_zone_api.models.entities.OrderItem;
import com.sysout.buy_zone_api.models.entities.OrderItemPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {

}