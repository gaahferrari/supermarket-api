package com.supermaket.GMarket.entity;

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
        @JoinColumn(name = "user_id", referencedColumnName = "id")
        private User user;

        private String streetName;

        private String number;

        private String CEP;

        private String city;

        private String state;

        private String country;

}
