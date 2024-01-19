package com.supermaket.GMarket.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "wallet")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String cardNumber;

    private String cardExp;

    private String cardOwnerName;

    private String CVV;

    private Boolean creditCard;

    private Boolean debitCard;
}
