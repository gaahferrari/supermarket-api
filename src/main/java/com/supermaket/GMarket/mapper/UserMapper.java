package com.supermaket.GMarket.mapper;

import com.supermaket.GMarket.DTO.*;
import com.supermaket.GMarket.entity.Order;
import com.supermaket.GMarket.entity.User;
import com.supermaket.GMarket.entity.Wallet;
import com.supermaket.GMarket.request.UserRequest;
import com.supermaket.GMarket.responses.BaseBodyResponse;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static User toUser (UserRequest request){
        return User.builder()
                .userName(request.getUserName())
                .name(request.getName())
                .lastName(request.getLastName())
                .birthDate(request.getBirthDate())
                .password(request.getPassword())
                .isAdmin(false)
                .build();
    }

    public static UserDTO toDTO (User user){
        return UserDTO.builder()
                .userName(user.getUserName())
                .name(user.getName())
                .lastName(user.getLastName())
                .birthDate(user.getBirthDate())
                .addressId(user.getAddress().getId())
                .id(user.getId())
                .build();
    }

    public static UserOrderDTO toOrdersDTO(User user, List<Order> orders){
        List<OrderDTO> ordersDTO = orders.stream().map(OrderMapper::toDTO).toList();

        return UserOrderDTO.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .name(user.getName())
                .lastName(user.getLastName())
                .birthDate(user.getBirthDate())
                .addressId(user.getAddress().getId())
                .orderIds(ordersDTO)
                .build();
    }

    public static UserProductsDTO toProductsDTO(User user){

        return UserProductsDTO.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .name(user.getName())
                .lastName(user.getLastName())
                .addressId(user.getAddress().getId())
                .birthDate(user.getBirthDate())
                .favoriteProductIds(user.getFavoriteProducts().stream().map(ProductMapper::toDTO).collect(Collectors.toList()))
                .build();
    }

    public static UserWalletDTO toWalletDTO(User user, List<Wallet> wallets){
        List<WalletDTO> walletDTO = wallets.stream().map(WalletMapper::toDTO).toList();

        return UserWalletDTO.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .name(user.getName())
                .lastName(user.getLastName())
                .addressId(user.getAddress().getId())
                .birthDate(user.getBirthDate())
                .walletIds(walletDTO)
                .build();
    }

    public static BaseBodyResponse<User> toResponse(User user){
        return BaseBodyResponse.<User>builder()
                .company("G-Market")
                .description("Usuário " + user.getUserName() + " foi criado com sucesso!" )
                .result(user).build();
    }

    public static BaseBodyResponse<List<UserDTO>> toListResponse(List<User> users){
        List<UserDTO> userDTOS = users.stream().map(UserMapper::toDTO).toList();
        return BaseBodyResponse.<List<UserDTO>>builder()
                .company("G-Market")
                .description("Lista de usuários")
                .result(userDTOS)
                .build();
    }

    public static BaseBodyResponse<UserOrderDTO> toResponseOrderID(User user, List<Order> orders){
        return BaseBodyResponse.<UserOrderDTO>builder()
                .company("G-Market")
                .description("Pedidos do usuário: " + user.getUserName().toString())
                .result(toOrdersDTO(user, orders)).build();
    }

    public static BaseBodyResponse<UserProductsDTO> toResponseProductID(User user){
        return BaseBodyResponse.<UserProductsDTO>builder()
                .company("G-Market")
                .description("Produtos favoritos do usuário: " + user.getUserName().toString())
                .result(toProductsDTO(user)).build();
    }

    public static BaseBodyResponse<UserWalletDTO> toResponseWalletID(User user, List<Wallet> wallets){
        return BaseBodyResponse.<UserWalletDTO>builder()
                .company("G-Market")
                .description("Métodos de pagamentos do usuário: " + user.getUserName().toString())
                .result(toWalletDTO(user, wallets)).build();
    }

}
