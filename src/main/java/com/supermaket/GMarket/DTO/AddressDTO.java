package com.supermaket.GMarket.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {
    private Long id;

    private Long userId;

    private String streetName;

    private String number;

    private String CEP;

    private String city;

    private String state;

    private String country;
}
