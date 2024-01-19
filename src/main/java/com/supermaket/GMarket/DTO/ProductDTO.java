package com.supermaket.GMarket.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.supermaket.GMarket.entity.Category;
import com.supermaket.GMarket.entity.Order;
import com.supermaket.GMarket.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.*;
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private Long id;

    private String name;

    private Double price;

    private String description;

    private String image;

    private Date createdAt;

    private Date updatedAt;

    private Boolean inStock;

}
