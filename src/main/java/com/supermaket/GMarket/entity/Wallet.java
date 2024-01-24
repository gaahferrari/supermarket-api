package com.supermaket.GMarket.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    private String cardNumber;

    private String cardExp;

    private String cardOwnerName;

    private String securityCode;


    public void addUser(User user){
        if(user.getUserName().isBlank()){
            throw new IllegalArgumentException("O nome não pode estar em branco");
        }
        setUser(user);
        user.getWallets().add(this);
    }
}
