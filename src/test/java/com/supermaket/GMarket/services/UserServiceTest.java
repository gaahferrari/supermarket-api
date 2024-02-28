package com.supermaket.GMarket.services;

import com.supermaket.GMarket.entity.*;
import com.supermaket.GMarket.exceptions.BadRequestException;
import com.supermaket.GMarket.exceptions.NotFoundException;
import com.supermaket.GMarket.mapper.UserMapper;
import com.supermaket.GMarket.repository.*;
import com.supermaket.GMarket.request.UserRequest;
import com.supermaket.GMarket.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    @Spy
    UserService userService;


    @Test
    public void shouldReturnAllUsers_whenListIsNotEmpty() {
        try (MockedStatic<UserMapper> userMapper = mockStatic(UserMapper.class)) {
            // Arrange
            User user = mock(User.class);
            List<User> users = Collections.singletonList(user);


            when(userRepository.findAll()).thenReturn(users);
            userMapper.when(() -> UserMapper.toUser(any(UserRequest.class))).thenReturn(user);

            // Act
            userService.getAll();

            // Assert

            verify(userRepository).findAll();
        }
    }

    @Test
    public void shouldReturnAllUsers_whenListIsEmpty() {

        //Arrange
        when(userRepository.findAll()).thenReturn(Collections.emptyList());
        String expectedError = "A lista de usuários está vazia";
        //Act
        BadRequestException actualError = assertThrows(BadRequestException.class, () -> userService.getAll());

        //Assert
        assertEquals(expectedError, actualError.getMessage());
    }

    @Test
    public void shouldCreateUser_whenRequestIsValid() {

        try (MockedStatic<UserMapper> userMapper = mockStatic(UserMapper.class)) {
            //Arrange
            UserRequest userRequest = mock(UserRequest.class);

            User user = mock(User.class);

            when(userRepository.save(any(User.class))).thenReturn(user);
            userMapper.when(() -> UserMapper.toUser(any(UserRequest.class))).thenReturn(user);

            //Act
            userService.create(userRequest);

            //Assert
            verify(userRepository).save(user);
        }
    }

    @Test
    public void shouldCreateUser_whenRequestIsNotValid() {

        //Arrange
        UserRequest userRequest = mock(UserRequest.class);

        String expectedError = "Erro ao cadastrar o usuário";
        //Act
        BadRequestException actualError = assertThrows(BadRequestException.class, () -> userService.create(userRequest));

        //Assert
        assertEquals(expectedError, actualError.getMessage());

    }

    @Test
    public void shouldFindOrderByUser_whenRequestIsValid() {

        //Arrange
        User user = mock(User.class);
        Long userId = 1L;
        user.setId(userId);

        Order order = mock(Order.class);
        List<Order> orders = new ArrayList<>();
        orders.add(order);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        //Act
        userService.getByOrder(userId);

        //Assert
        verify(userRepository).findById(userId);

    }

    @Test
    public void shouldFindOrderByUser_whenRequestIsNotValid() {

        //Arrange
        User user = mock(User.class);

        String expectedError = "Usuário com o id: " + user.getId() + " não foi encontrado";
        //Act
        NotFoundException actualError = assertThrows(NotFoundException.class, () -> userService.getByOrder(user.getId()));

        //Assert
        assertEquals(expectedError, actualError.getMessage());

    }

    @Test
    public void shouldFindFavoriteProductsByUser_whenRequestIsValid() {

        //Arrange
        User user = mock(User.class);
        Long userId = 1L;
        user.setId(userId);


        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        //Act
        userService.getByProducts(userId);

        //Assert
        verify(userRepository).findById(userId);

    }

    @Test
    public void shouldFindFavoriteProductsByUser_whenRequestIsNotValid() {

        //Arrange
        User user = mock(User.class);

        String expectedError = "Usuário com o id: " + user.getId() + " não foi encontrado";
        //Act
        NotFoundException actualError = assertThrows(NotFoundException.class, () -> userService.getByProducts(user.getId()));

        //Assert
        assertEquals(expectedError, actualError.getMessage());

    }

    @Test
    public void shouldFindUserById_whenRequestIsValid() {
        try (MockedStatic<UserMapper> userMapper = mockStatic(UserMapper.class)) {
            //Arrange
            User user = mock(User.class);

            when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
            userMapper.when(() -> UserMapper.toUser(any(UserRequest.class))).thenReturn(user);

            //Act
            userService.getById(user.getId());

            //Assert
            verify(userRepository).findById(user.getId());
        }
    }

    @Test
    public void shouldFindUserById_whenRequestIsNotValid() {

        //Arrange
        User user = mock(User.class);

        when(userRepository.findById(any())).thenReturn(Optional.empty());
        String expectedError = "Usuário não encontrado com o ID: " + user.getId();
        //Act
        NotFoundException actualError = assertThrows(NotFoundException.class, () -> userService.getById(user.getId()));

        //Assert
        assertEquals(expectedError, actualError.getMessage());

    }

    @Test
    public void shouldAddProductToUsersList_whenRequestIsValid() {
        try (MockedStatic<UserMapper> userMapper = mockStatic(UserMapper.class)) {
            //Arrange
            User user = mock(User.class);

            Product product = mock(Product.class);

            when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
            when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
            userMapper.when(() -> UserMapper.toUser(any(UserRequest.class))).thenReturn(user);

            //Act
            userService.addFavoriteProduct(user.getId(), product.getId());

            //Assert
            verify(userRepository).save(user);
            verify(user).addFavoriteProduct(product);
            verify(userRepository).findById(user.getId());
            verify(productRepository).findById(product.getId());
        }
    }

    @Test
    public void shouldAddProductToUsersList_whenRequestIsNotValid() {

        //Arrange
        User user = mock(User.class);
        Product product = mock(Product.class);


        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        String expectedError = "Usuário ou produto não foram encontrados";
        //Act
        NotFoundException actualError = assertThrows(NotFoundException.class, () -> userService.addFavoriteProduct(user.getId(), product.getId()));

        //Assert
        assertEquals(expectedError, actualError.getMessage());

    }

    @Test
    public void shouldRemoveProductToUsersList_whenRequestIsValid() {

        //Arrange
        User user = mock(User.class);
        Product product = mock(Product.class);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        user.addFavoriteProduct(product);
        //Act

        userService.removeFavoriteProduct(user.getId(), product.getId());

        //Assert
        verify(user).removeFavoriteProduct(product);
        verify(userRepository).save(user);
        verify(userRepository).findById(user.getId());
        verify(productRepository).findById(product.getId());
    }


    @Test
    public void shouldRemoveProductToUsersList_whenRequestIsNotValid() {

        //Arrange
        User user = mock(User.class);
        Product product = mock(Product.class);


        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        String expectedError = "A lista de produtos favoritos está vazia";
        //Act
        BadRequestException actualError = assertThrows(BadRequestException.class, () -> userService.removeFavoriteProduct(user.getId(), product.getId()));

        //Assert
        assertEquals(expectedError, actualError.getMessage());

    }

    @Test
    public void shouldFindUserByUserNameAndPassword_whenRequestIsValid() {
        try (MockedStatic<UserMapper> userMapper = mockStatic(UserMapper.class)) {
            //Arrange
            User user = mock(User.class);
            String userName = "gsilv239";
            String passaword = "12345";

            when(userRepository.findUserByUserNameAndPassword(userName, passaword)).thenReturn(user);
            userMapper.when(() -> UserMapper.toUser(any(UserRequest.class))).thenReturn(user);

            //Act
            userService.getByUserNameAndPassword(userName, passaword );

            //Assert
            verify(userRepository).findUserByUserNameAndPassword(userName, passaword );
        }
    }

    @Test
    public void shouldFindUserByUserNameAndPassword_whenRequestIsNotValid() {

        //Arrange
        User user = mock(User.class);

        String expectedError = "Erro ao tentar encontrar usuário e senha";
        //Act
        BadRequestException actualError = assertThrows(BadRequestException.class, () -> userService.getByUserNameAndPassword(user.getUserName(), user.getPassword()));

        //Assert
        assertEquals(expectedError, actualError.getMessage());

    }


    @Test
    public void shouldDeleteUser_whenRequestIsValid() {
        try (MockedStatic<UserMapper> userMapper = mockStatic(UserMapper.class)) {
            //Arrange
            User user = mock(User.class);
            Long userId = 1L;
            user.setId(userId);
            Wallet wallet = mock(Wallet.class);
            Address address = mock(Address.class);
            Order order = mock(Order.class);
            Product product = mock(Product.class);
            order.getProducts().add(product);
            user.setWallet(wallet);
            user.setAddress(address);
            user.getOrders().add(order);


            when(userRepository.findById(userId)).thenReturn(Optional.of(user));
            userMapper.when(() -> UserMapper.toUser(any(UserRequest.class))).thenReturn(user);

            //Act
            userService.deleteUser(userId);

            //Assert
            verify(userRepository).delete(user);

        }
    }

    @Test
    public void shouldDeleteUser_whenRequestIsNotValid() {

        //Arrange
        User user = mock(User.class);

        String expectedError = "Erro ao deletar o usuário";
        //Act
        BadRequestException actualError = assertThrows(BadRequestException.class, () -> userService.deleteUser(user.getId()));

        //Assert
        assertEquals(expectedError, actualError.getMessage());

    }



}
