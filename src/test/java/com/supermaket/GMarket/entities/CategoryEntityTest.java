package com.supermaket.GMarket.entities;

import com.supermaket.GMarket.entity.Category;
import com.supermaket.GMarket.entity.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
@ExtendWith(MockitoExtension.class)
public class CategoryEntityTest {
    @Test
    public void shouldAddProduct_whenNameIsNotBlank() {
        //Arrange
        Product product = Product.builder()
                .id(1L)
                .name("Mussarela")
                .price(10.00)
                .orders(new HashSet<>())
                .inStock(true)
                .categories(new HashSet<>())
                .image("image.jpg")
                .favoriteByUsers(new ArrayList<>())
                .createdAt(new Date())
                .updatedAt(new Date())
                .description("amarelo")
                .build();

        Category category = Category.builder()
                .id(1L)
                .name("Frutas")
                .products(new ArrayList<>())
                .build();

        //Act
        category.addProduct(product);

        //Assert
        assertEquals(product, category.getProducts().iterator().next());
        assertTrue(product.getCategories().contains(category));
        assertTrue(category.getProducts().contains(product));
        assertEquals("Mussarela", product.getName());
    }

    @Test
    public void shouldAddProduct_whenUserNameIsBlank() {
        //Arrange
        Product product = Product.builder()
                .id(1L)
                .name("")
                .price(10.00)
                .orders(new HashSet<>())
                .inStock(true)
                .categories(new HashSet<>())
                .image("image.jpg")
                .favoriteByUsers(new ArrayList<>())
                .createdAt(new Date())
                .updatedAt(new Date())
                .description("amarelo")
                .build();

        Category category = Category.builder()
                .id(1L)
                .name("Frutas")
                .products(new ArrayList<>())
                .build();



        String expectedError = "O nome não pode estar em branco";

        //Act
        IllegalArgumentException actualError = assertThrows(IllegalArgumentException.class, () -> category.addProduct(product));

        //Assert
        assertEquals(expectedError, actualError.getMessage());
    }

    @Test
    public void shouldRemoveProduct_whenIsNotEmpty() {
        //Arrange
        Product product = Product.builder()
                .id(1L)
                .name("Mussarela")
                .price(10.00)
                .orders(new HashSet<>())
                .inStock(true)
                .categories(new HashSet<>())
                .image("image.jpg")
                .favoriteByUsers(new ArrayList<>())
                .createdAt(new Date())
                .updatedAt(new Date())
                .description("amarelo")
                .build();

        Category category = Category.builder()
                .id(1L)
                .name("Frutas")
                .products(new ArrayList<>())
                .build();

        category.addProduct(product);

        //Act
        category.removeProduct(product);

        //Assert

        assertFalse(product.getCategories().contains(category));
        assertEquals("Mussarela", product.getName());
        assertTrue(product.getCategories().isEmpty());
    }

    @Test
    public void shouldRemoveProduct_whenIsEmpty() {
        //Arrange
        Product product = Product.builder()
                .id(1L)
                .name("Mussarela")
                .price(10.00)
                .orders(new HashSet<>())
                .inStock(true)
                .categories(new HashSet<>())
                .image("image.jpg")
                .favoriteByUsers(new ArrayList<>())
                .createdAt(new Date())
                .updatedAt(new Date())
                .description("amarelo")
                .build();

        Category category = Category.builder()
                .id(1L)
                .name("Frutas")
                .products(new ArrayList<>())
                .build();



        String expectedError = "A lista de produtos está vazia";

        //Act
        IllegalStateException actualError = assertThrows(IllegalStateException.class, () -> category.removeProduct(product));

        //Assert
        assertEquals(expectedError, actualError.getMessage());
    }

}
