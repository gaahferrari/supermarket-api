package com.supermaket.GMarket.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private String streetName;

    private String number;

    private String zipCode;

    private String city;

    private String state;

    private String country;

    public void addUser(User user) {
        if (user.getUserName().isBlank()) {
            throw new IllegalArgumentException("O nome não pode estar em branco");
        }
        setUser(user);
        user.setAddress(this);
    }



}
