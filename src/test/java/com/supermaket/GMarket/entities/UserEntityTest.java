package com.supermaket.GMarket.entities;

import com.supermaket.GMarket.entity.Address;
import com.supermaket.GMarket.entity.Order;
import com.supermaket.GMarket.entity.Product;
import com.supermaket.GMarket.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserEntityTest {

    @Test
    public void shouldRemoveFavoriteProduct_whenListIsNotEmpty() {
        // Arrange
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

        User user = User.builder()
                .userName("Gabriela")
                .name("Gabriela")
                .lastName("Ferrari")
                .address(new Address())
                .id(1L)
                .birthDate("06/07/1997")
                .favoriteProducts(new ArrayList<>())
                .isAdmin(false)
                .orders(new ArrayList<>())
                .wallets(new ArrayList<>())
                .build();

        user.addFavoriteProduct(product);

        // Act
         user.removeFavoriteProduct(product);

        // Assert
        assertFalse(user.getFavoriteProducts().contains(product));
        assertFalse(product.getFavoriteByUsers().contains(user));
    }

    @Test
    public void shouldThrowException_whenListIsEmpty() {
        // Arrange
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

        User user = User.builder()
                .userName("Gabriela")
                .name("Gabriela")
                .lastName("Ferrari")
                .address(new Address())
                .id(1L)
                .birthDate("06/07/1997")
                .favoriteProducts(new ArrayList<>())
                .isAdmin(false)
                .orders(new ArrayList<>())
                .wallets(new ArrayList<>())
                .build();

        String expectedError = "A lista de produtos favoritos está vazia";

        //Act
        IllegalStateException actualError = assertThrows(IllegalStateException.class, () -> user.removeFavoriteProduct(product));

        //Assert
        assertEquals(expectedError, actualError.getMessage());
    }

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

        User user = User.builder()
                .userName("Gabriela")
                .name("Gabriela")
                .lastName("Ferrari")
                .address(new Address())
                .id(1L)
                .birthDate("06/07/1997")
                .favoriteProducts(new ArrayList<>())
                .isAdmin(false)
                .orders(new ArrayList<>())
                .wallets(new ArrayList<>())
                .build();

        //Act
        user.addFavoriteProduct(product);

        //Assert
        assertTrue(user.getFavoriteProducts().contains(product));
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

        User user = User.builder()
                .userName("Gabriela")
                .name("Gabriela")
                .lastName("Ferrari")
                .address(new Address())
                .id(1L)
                .birthDate("06/07/1997")
                .favoriteProducts(new ArrayList<>())
                .isAdmin(false)
                .orders(new ArrayList<>())
                .wallets(new ArrayList<>())
                .build();


        String expectedError = "O nome do produto não pode estar em branco";

        //Act
        IllegalArgumentException actualError = assertThrows(IllegalArgumentException.class, () -> user.addFavoriteProduct(product));

        //Assert
        assertEquals(expectedError, actualError.getMessage());
    }

}
