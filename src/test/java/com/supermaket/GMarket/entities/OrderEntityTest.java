package com.supermaket.GMarket.entities;

import com.supermaket.GMarket.entity.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class OrderEntityTest {
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

        Order order = Order.builder()
                .id(1L)
                .totalPrice(20.0)
                .products(new HashSet<>())
                .createdAt(new Date())
                .user(new User())
                .build();

        //Act
        order.addProduct(product);

        //Assert
        assertEquals(product, order.getProducts().iterator().next());
        assertTrue(product.getOrders().contains(order));
        assertTrue(order.getProducts().contains(product));
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

        Order order = Order.builder()
                .id(1L)
                .totalPrice(20.0)
                .products(new HashSet<>())
                .createdAt(new Date())
                .user(new User())
                .build();



        String expectedError = "O nome não pode estar em branco";

        //Act
        IllegalArgumentException actualError = assertThrows(IllegalArgumentException.class, () -> order.addProduct(product));

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

        Order order = Order.builder()
                .id(1L)
                .totalPrice(20.0)
                .products(new HashSet<>())
                .createdAt(new Date())
                .user(new User())
                .build();


        order.addProduct(product);

        //Act
        order.removeProduct(product);

        //Assert

        assertFalse(product.getCategories().contains(order));
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

        Order order = Order.builder()
                .id(1L)
                .totalPrice(20.0)
                .products(new HashSet<>())
                .createdAt(new Date())
                .user(new User())
                .build();


        String expectedError = "A lista de produtos está vazia";

        //Act
        IllegalStateException actualError = assertThrows(IllegalStateException.class, () -> order.removeProduct(product));

        //Assert
        assertEquals(expectedError, actualError.getMessage());
    }

    @Test
    public void shouldAddUser_whenUserNameIsNotBlank() {
        //Arrange
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

        Order order = Order.builder()
                .id(1L)
                .totalPrice(20.0)
                .products(new HashSet<>())
                .createdAt(new Date())
                .user(new User())
                .build();

        //Act
        order.addUser(user);

        //Assert
        assertEquals(user.getUserName(), order.getUser().getUserName());
        assertEquals("Gabriela", user.getUserName());
    }
    @Test
    public void shouldAddUser_whenUserNameIsBlank() {
        //Arrange
        User user = User.builder()
                .userName("")
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

        Order order = Order.builder()
                .id(1L)
                .totalPrice(20.0)
                .products(new HashSet<>())
                .createdAt(new Date())
                .user(new User())
                .build();


        String expectedError = "O nome não pode estar em branco";

        //Act
        IllegalArgumentException actualError = assertThrows(IllegalArgumentException.class, () -> order.addUser(user));

        //Assert
        assertEquals(expectedError, actualError.getMessage());
    }

    @Test
    public void shouldRemoveOrder_WhenUserAndProductNameIsNotBlank() {
        //Arrange
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

        Order order = Order.builder()
                .id(1L)
                .totalPrice(20.0)
                .products(new HashSet<>())
                .createdAt(new Date())
                .user(new User())
                .build();

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

        order.addProduct(product);
        order.addUser(user);

        //Act
        order.removeOrder();

        //Assert
        assertNull(order.getUser());
        assertFalse(user.getOrders().contains(order));
        assertEquals(0, product.getOrders().size());
    }
    @Test
    public void shouldRemoveOrder_WhenUserAndProductNameIsBlank() {
        //Arrange
        User user = User.builder()
                .userName("")
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

        Order order = Order.builder()
                .id(1L)
                .totalPrice(20.0)
                .products(new HashSet<>())
                .createdAt(new Date())
                .user(user)
                .build();



        String expectedError = "O nome do cliente e do produto não pode estar em branco";

        //Act
       IllegalStateException actualError = assertThrows(IllegalStateException.class, () -> order.removeOrder());

        //Assert
        assertEquals(expectedError, actualError.getMessage());
    }
}
