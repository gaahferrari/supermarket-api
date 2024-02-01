package com.supermaket.GMarket.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class WalletDTO {
    private Long id;

    private Long userId;

    private String cardNumber;

    private String cardExp;

    private String cardOwnerName;

    private String securityCode;

}
