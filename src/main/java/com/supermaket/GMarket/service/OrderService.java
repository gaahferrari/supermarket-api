package com.supermaket.GMarket.service;

import com.supermaket.GMarket.DTO.OrderDTO;
import com.supermaket.GMarket.DTO.OrderProductDTO;
import com.supermaket.GMarket.DTO.WalletDTO;
import com.supermaket.GMarket.entity.*;
import com.supermaket.GMarket.exceptions.BadRequestException;
import com.supermaket.GMarket.exceptions.NotFoundException;
import com.supermaket.GMarket.mapper.OrderMapper;
import com.supermaket.GMarket.mapper.WalletMapper;
import com.supermaket.GMarket.repository.OrderRepository;
import com.supermaket.GMarket.repository.ProductRepository;
import com.supermaket.GMarket.repository.UserRepository;
import com.supermaket.GMarket.request.OrderRequest;
import com.supermaket.GMarket.responses.BaseBodyResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    public BaseBodyResponse<List<OrderProductDTO>> getAll() {
        List<Order> orders = orderRepository.findAll();
        if(orders.isEmpty()){
            throw new BadRequestException("A lista de pedidos está vazia");
        }
        return OrderMapper.toListResponse(orders);
    }

    public void deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            order.removeOrder();
            orderRepository.delete(order);
        } else {
            throw new BadRequestException("Erro ao deletar pedido");
        }
    }


    @Transactional
    public BaseBodyResponse<OrderDTO> create(@Valid OrderRequest request) {
        Optional<User> user = userRepository.findById(request.getUserId());

        if(user.isEmpty()){
            throw new BadRequestException("O usuário com o id " + request.getUserId() + " não existe");
        }
        Order newOrder = orderRepository.save(OrderMapper.toOrder(request));
        if (user.isPresent()) {
            newOrder.addUser(user.get());
        }
        return OrderMapper.toResponse(newOrder);
    }

    @Transactional
    public BaseBodyResponse<OrderProductDTO> addProductToOrder(Long orderId, Long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        Optional<Order> orderOptional = orderRepository.findById(orderId);

        if(productOptional.isEmpty() || orderOptional.isEmpty()){
            throw new NotFoundException("Pizza ou pedido não foi encontrado");
        }
        Product product = productOptional.get();
        Order order = orderOptional.get();

        order.addProduct(product);
        double totalProductPrice = order.getProducts().stream().mapToDouble(Product::getPrice).sum();
        order.setTotalPrice(totalProductPrice);

        Order savedOrder = orderRepository.save(order);
        return OrderMapper.toResponseProduct(savedOrder);
    }

    public void removeProduct(Long orderId, Long productId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        Product product = productRepository.findById(productId).orElse(null);
        if (order.getProducts().isEmpty()) {
            throw new IllegalStateException("A lista de produtos favoritos está vazia");
        }

        order.removeProduct(product);
        double totalProductPrice = order.getProducts().stream().mapToDouble(Product::getPrice).sum();
        order.setTotalPrice(totalProductPrice);
        orderRepository.save(order);
    }

    public BaseBodyResponse<OrderDTO> getById(Long orderID) {
        Order order = orderRepository.findById(orderID).orElse(null);
        if (order != null) {
            return OrderMapper.toResponse(order);
        } else {
            throw new NotFoundException("Carteira não encontrada com o ID: " + orderID);
        }
    }
}




