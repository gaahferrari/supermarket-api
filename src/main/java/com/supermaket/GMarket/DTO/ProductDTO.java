package com.supermaket.GMarket.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

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

    private Long quantity;

}
