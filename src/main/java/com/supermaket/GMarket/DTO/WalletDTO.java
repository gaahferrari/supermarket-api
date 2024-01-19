package com.supermaket.GMarket.DTO;

import com.supermaket.GMarket.entity.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    private String CVV;

    private Boolean creditCard;

    private Boolean debitCard;
}
