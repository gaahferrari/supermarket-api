package com.supermaket.GMarket.service;

import com.supermaket.GMarket.DTO.UserDTO;
import com.supermaket.GMarket.DTO.UserOrderDTO;
import com.supermaket.GMarket.DTO.UserProductsDTO;
import com.supermaket.GMarket.entity.*;
import com.supermaket.GMarket.exceptions.NotFoundException;
import com.supermaket.GMarket.mapper.UserMapper;
import com.supermaket.GMarket.repository.*;
import com.supermaket.GMarket.request.UserRequest;
import com.supermaket.GMarket.responses.BaseBodyResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.supermaket.GMarket.exceptions.BadRequestException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private final OrderRepository orderRepository;

    private final WalletRepository walletRepository;

    private final AddressRepository addressRepository;


    public BaseBodyResponse<List<UserDTO>> getAll() {
        List<User> customers = userRepository.findAll();
        if (customers.isEmpty()) {
            throw new BadRequestException("A lista de usuários está vazia");
        }
        return UserMapper.toListResponse(customers);
    }

    public BaseBodyResponse<User> create(@Valid UserRequest request) {
        User user = userRepository.save(UserMapper.toUser(request));
        if (user != null) {
            return UserMapper.toResponse(user);
        } else {
            throw new BadRequestException("Erro ao cadastrar o usuário");
        }

    }

    public BaseBodyResponse<UserOrderDTO> getByOrder(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new NotFoundException("Usuário com o id: " + userId + " não foi encontrado");
        } else {
            User user = userOptional.get();
            List<Order> orders = user.getOrders();
            return UserMapper.toResponseOrderID(user, orders);
        }
    }


    public BaseBodyResponse<User> getById(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            return UserMapper.toResponse(user);
        } else {
            throw new NotFoundException("Usuário não encontrado com o ID: " + userId);
        }
    }

    public BaseBodyResponse<UserProductsDTO> addFavoriteProduct(Long userId, Long productId) {
        Optional<User> user = userRepository.findById(userId);

        Optional<Product> product = productRepository.findById(productId);

        if (product.isEmpty() || user.isEmpty()) {
            throw new NotFoundException("Usuário ou produto não foram encontrados");
        }

        User user1 = user.get();
        Product product1 = product.get();

        user1.addFavoriteProduct(product1);

        User users = userRepository.save(user1);
        return UserMapper.toResponseProductID(users);
    }

    public void removeFavoriteProduct(Long userId, Long productId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Product> productOptional = productRepository.findById(productId);

        if (userOptional.isEmpty() || productOptional.isEmpty()) {
            throw new BadRequestException("A lista de produtos favoritos está vazia");
        }
        User user = userOptional.get();
        Product product = productOptional.get();
        user.removeFavoriteProduct(product);
        userRepository.save(user);

    }

    public BaseBodyResponse<UserProductsDTO> getByProducts(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new NotFoundException("Usuário com o id: " + userId + " não foi encontrado");
        } else {
            User user = userOptional.get();
            return UserMapper.toResponseProductID(user);
        }
    }


    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            Wallet wallet = user.getWallet();
            if (wallet != null) {
                walletRepository.delete(wallet);
            }
            Address address = user.getAddress();
            if (address != null) {
                addressRepository.delete(address);
            }
            for (Order order : new ArrayList<>(user.getOrders())) {
                order.getProducts().clear();
                orderRepository.delete(order);
            }

            user.getFavoriteProducts().clear();

            userRepository.delete(user);
        } else {
            throw new BadRequestException("Erro ao deletar o usuário");
        }
    }


    public UserDTO getByUserNameAndPassword(String userName, String password) {
        if (userName != null || password != null) {
            return UserMapper.toDTO(userRepository.findUserByUserNameAndPassword(userName, password));
        } else {
            throw new BadRequestException("Erro ao tentar encontrar usuário e senha");
        }

    }
}

