package com.supermaket.GMarket.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Double price;

    private String description;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "product_categories",
            joinColumns = @JoinColumn(name = "product_fk"),
            inverseJoinColumns = @JoinColumn(name = "categories_fk")
    )

    private Set<Category> categories = new HashSet<>();

    private String image;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    private Boolean inStock;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "product_orders",
            joinColumns = @JoinColumn(name = "product_fk"),
            inverseJoinColumns = @JoinColumn(name = "orders_fk")
    )
    private Set<Order> orders = new HashSet<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "favoriteProducts")
    private List<User> favoriteByUsers = new ArrayList<>();
}
