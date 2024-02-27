package com.supermaket.GMarket.services;

import com.supermaket.GMarket.entity.Order;
import com.supermaket.GMarket.entity.Product;
import com.supermaket.GMarket.entity.User;
import com.supermaket.GMarket.exceptions.BadRequestException;
import com.supermaket.GMarket.exceptions.NotFoundException;
import com.supermaket.GMarket.mapper.OrderMapper;
import com.supermaket.GMarket.repository.OrderRepository;
import com.supermaket.GMarket.repository.ProductRepository;
import com.supermaket.GMarket.repository.UserRepository;
import com.supermaket.GMarket.request.OrderRequest;
import com.supermaket.GMarket.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    UserRepository userRepository;
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    @Spy
    OrderService orderService;



    @Test
    public void shouldReturnAllOrders_whenListIsNotEmpty() {
        try (MockedStatic<OrderMapper> orderMapper = mockStatic(OrderMapper.class)) {
            // Arrange
            Order order = mock(Order.class);
            List<Order> orders = Collections.singletonList(order);


            when(orderRepository.findAll()).thenReturn(orders);
            orderMapper.when(() -> OrderMapper.toOrder(any(OrderRequest.class))).thenReturn(order);

            // Act
            orderService.getAll();

            // Assert

            verify(orderRepository).findAll();
        }
    }

    @Test
    public void shouldReturnAllOrders_whenListIsEmpty() {

        //Arrange
        when(orderRepository.findAll()).thenReturn(Collections.emptyList());
        String expectedError = "A lista de pedidos está vazia";
        //Act
        BadRequestException actualError = assertThrows(BadRequestException.class, () -> orderService.getAll());

        //Assert
        assertEquals(expectedError, actualError.getMessage());
    }

    @Test
    public void shouldDeleteOrder_whenRequestIsValid(){

        //Arrange
        Order order = mock(Order.class);

        when(orderRepository.findById(anyLong())).thenReturn(Optional.ofNullable(order));

        //Act
        orderService.deleteOrder(order.getId());


        //Assert
        verify(order).removeOrder();
        verify(orderRepository).delete(order);

    }

    @Test
    public void shouldDeleteOrder_whenRequestIsNotValid(){

        //Arrange
        Order order = mock(Order.class);


        when(orderRepository.findById(any())).thenReturn(Optional.empty());
        String expectedError = "Erro ao deletar pedido";
        //Act
        BadRequestException actualError = assertThrows(BadRequestException.class, () -> orderService.deleteOrder(order.getId()));

        //Assert
        assertEquals(expectedError, actualError.getMessage());

    }

    @Test
    public void shouldCreateOrder_whenRequestIsValid(){

        try(MockedStatic<OrderMapper> orderMapper = mockStatic(OrderMapper.class)) {
            //Arrange
            User user = mock(User.class);

            OrderRequest orderRequest = mock(OrderRequest.class);

            Order order = mock(Order.class);

            when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
            when(orderRepository.save(any(Order.class))).thenReturn(order);
            orderMapper.when(() -> OrderMapper.toOrder(any(OrderRequest.class))).thenReturn(order);

            //Act
            orderService.create(orderRequest);

            //Assert
            verify(orderRepository).save(order);
            verify(order).addUser(user);
        }
    }

    @Test
    public void shouldCreateOrder_whenRequestIsNotValid(){

        //Arrange

        OrderRequest orderRequest = mock(OrderRequest.class);


        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        String expectedError = "O usuário com o id " + orderRequest.getUserId() + " não existe";
        //Act
        BadRequestException actualError = assertThrows(BadRequestException.class, () -> orderService.create(orderRequest));

        //Assert
        assertEquals(expectedError, actualError.getMessage());

    }

    @Test
    public void shouldAddProductToOrder_whenRequestIsValid() {
        try (MockedStatic<OrderMapper> orderMapper = mockStatic(OrderMapper.class)) {
            //Arrange
            Product product = mock(Product.class);

            Order order = mock(Order.class);

            when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
            when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
            orderMapper.when(() -> OrderMapper.toOrder(any(OrderRequest.class))).thenReturn(order);

            //Act
            orderService.addProductToOrder(order.getId(), product.getId());

            //Assert
            verify(orderRepository).save(order);
            verify(order).addProduct(product);
        }
    }

    @Test
    public void shouldAddProductToOrder_whenRequestIsNotValid(){

        //Arrange

        Order order = mock(Order.class);

        Product product = mock(Product.class);

        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        String expectedError = "Produto ou pedido não foi encontrado";
        //Act
        NotFoundException actualError = assertThrows(NotFoundException.class, () -> orderService.addProductToOrder(order.getId(), product.getId()));

        //Assert
        assertEquals(expectedError, actualError.getMessage());


    }

    @Test
    public void shouldRemoveProductToOrder_whenRequestIsValid() {

        //Arrange
        Order order = mock(Order.class);

        Product product = mock(Product.class);

        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        order.addProduct(product);
        //Act

        orderService.removeProduct( order.getId(),product.getId());

        //Assert
        verify(order).removeProduct(product);
        verify(orderRepository).save(order);
        assertEquals(0, order.getProducts().size());
        verify(orderRepository).findById(order.getId());
        verify(productRepository).findById(product.getId());
    }


    @Test
    public void shouldRemoveProductToOrder_whenRequestIsNotValid() {

        //Arrange
        Order order = mock(Order.class);
        Product product = mock(Product.class);


        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        String expectedError = "A lista de produtos favoritos está vazia";
        //Act
        BadRequestException actualError = assertThrows(BadRequestException.class, () -> orderService.removeProduct(order.getId(), product.getId()));

        //Assert
        assertEquals(expectedError, actualError.getMessage());

    }

    @Test
    public void shouldFindOrderById_whenRequestIsValid() {
        try (MockedStatic<OrderMapper> orderMapper = mockStatic(OrderMapper.class)) {
            //Arrange
            Order order = mock(Order.class);

            when(orderRepository.findById(anyLong())).thenReturn(Optional.ofNullable(order));
            orderMapper.when(() -> OrderMapper.toOrder(any(OrderRequest.class))).thenReturn(order);

            //Act
            orderService.getById(order.getId());

            //Assert
            verify(orderRepository).findById(order.getId());
        }
    }

    @Test
    public void shouldFindOrderById_whenRequestIsNotValid() {

        //Arrange
        Order order = mock(Order.class);

        when(orderRepository.findById(any())).thenReturn(Optional.empty());
        String expectedError = "Pedido não encontrado com o ID: " + order.getId();
        //Act
        NotFoundException actualError = assertThrows(NotFoundException.class, () -> orderService.getById(order.getId()));

        //Assert
        assertEquals(expectedError, actualError.getMessage());

    }

}
