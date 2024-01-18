package com.supermaket.GMarket.repository;

import com.supermaket.GMarket.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
