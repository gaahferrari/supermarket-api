package com.supermaket.GMarket.repository;

import com.supermaket.GMarket.entity.Category;
import com.supermaket.GMarket.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    public Category findCategoryByName(String name);
}
