package com.supermaket.GMarket.repository;

import com.supermaket.GMarket.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
