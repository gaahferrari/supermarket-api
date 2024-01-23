package com.supermaket.GMarket.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToMany(mappedBy = "orders")
    private Set<Product> products = new HashSet<>();

    @CreationTimestamp
    private Date createdAt;

    private Double totalPrice;

    public void addUser(User user) {
        if (user.getUserName().isBlank()) {
            throw new IllegalArgumentException("O nome não pode estar em branco");
        } else {
            setUser(user);
            user.getOrders().add(this);
        }
    }

    public void addProduct(Product product) {
        if (product.getName().isBlank()) {
            throw new IllegalArgumentException("O nome não pode estar em branco");
        } else {
            products.add(product);
            product.getOrders().add(this);
        }
    }

    public void removeOrder() {

        if (this.user.getUserName().isBlank() || this.products.isEmpty()) {
            throw new IllegalStateException("O nome do cliente e do produto não pode estar em branco");

        } else {
            for (Product product : new HashSet<>(this.products))
                product.getOrders().remove(this);
            this.products.remove(products);
        }
        this.user.getOrders().remove(this);
        this.user = null;
    }
}
