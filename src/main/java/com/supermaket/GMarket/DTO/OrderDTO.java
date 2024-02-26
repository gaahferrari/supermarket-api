package com.supermaket.GMarket.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long id;

    private Long userId;

    private String name;

    private Long address;

    private Date createdAt;

    private Double totalPrice;

    private Long wallet;
}
