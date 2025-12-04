package com.sysout.buy_zone_api.repository;

import com.sysout.buy_zone_api.models.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
