package com.supermaket.GMarket.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String lastName;

    private String userName;

    private String birthDate;

    private String password;

    private Boolean isAdmin;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "wallet_id", referencedColumnName = "id")
    private Wallet wallet;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "favorite",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> favoriteProducts = new ArrayList<>();


    public void addFavoriteProduct(Product product) {
        if (product.getName().isBlank()) {
            throw new IllegalArgumentException("O nome do produto não pode estar em branco");
        }
        favoriteProducts.add(product);
        product.getFavoriteByUsers().add(this);
    }

    public boolean removeFavoriteProduct(Product product) {
        if (this.favoriteProducts.isEmpty()) {
            throw new IllegalStateException("A lista de produtos favoritos está vazia");
        }
        if (favoriteProducts.remove(product)) {
            product.getFavoriteByUsers().remove(this);
        }
        return false;
    }
}
