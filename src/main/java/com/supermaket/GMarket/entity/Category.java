package com.supermaket.GMarket.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "categories")
    private List<Product> products;

    public void addProduct(Product product) {
        if(product.getName().isBlank()){
            throw new IllegalArgumentException("O nome não pode estar em branco");
        }
        products.add(product);
        product.getCategories().add(this);
    }

    public void removeProduct(Product product) {
        if (this.products.isEmpty()) {
            throw new IllegalStateException("A lista de produtos está vazia");
        }
        if (this.products.contains(product)) {
            this.products.remove(product);
            product.getCategories().remove(this);
        }
    }

}
