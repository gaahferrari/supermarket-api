package com.supermaket.GMarket.DTO;

import com.supermaket.GMarket.entity.Address;
import com.supermaket.GMarket.entity.Order;
import com.supermaket.GMarket.entity.Product;
import com.supermaket.GMarket.entity.Wallet;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;

    private String name;

    private String lastName;

    private String userName;

    private String birthDate;

    private Long addressId;


}
