package com.supermaket.GMarket.mapper;

import com.supermaket.GMarket.DTO.*;
import com.supermaket.GMarket.entity.Order;
import com.supermaket.GMarket.entity.Product;
import com.supermaket.GMarket.entity.User;
import com.supermaket.GMarket.request.OrderRequest;
import com.supermaket.GMarket.responses.BaseBodyResponse;
import org.aspectj.weaver.ast.Or;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class OrderMapper {
    public static Order toOrder(OrderRequest request){
        return Order.builder()
                .totalPrice(request.getTotalPrice())
                .build();
    }

    public static OrderDTO toDTO(Order order){
        return OrderDTO.builder()
                .id(order.getId())
                .totalPrice(order.getTotalPrice())
                .userId(order.getUser().getId())
                .name(order.getUser().getName())
                .address(order.getUser().getAddress().getId())
                .createdAt(order.getCreatedAt())
                .build();
    }

    public static OrderProductDTO toProductsDTO(Order order){

        return OrderProductDTO.builder()
                .id(order.getId())
                .totalPrice(order.getTotalPrice())
                .userId(order.getUser().getId())
                .name(order.getUser().getName())
                .address(order.getUser().getAddress().getId())
                .createdAt(order.getCreatedAt())
                .productsIds(order.getProducts().stream().map(ProductMapper::toDTO).collect(Collectors.toSet()))
                .build();
    }

    public static BaseBodyResponse<Order> toResponse(Order order){
        return BaseBodyResponse.<Order>builder()
                .company("G-Market")
                .description("O pedido número " + order.getId() + " foi criado com sucesso!" )
                .result(order).build();
    }

    public static BaseBodyResponse<List<OrderDTO>> toListResponse(List<Order> orders){
        List<OrderDTO> orderDTOS = orders.stream().map(OrderMapper::toDTO).toList();
        return BaseBodyResponse.<List<OrderDTO>>builder()
                .company("G-Market")
                .description("Lista de pedidos realizados")
                .result(orderDTOS)
                .build();
    }

    public static BaseBodyResponse<OrderProductDTO> toResponseProduct(Order order){
        return BaseBodyResponse.<OrderProductDTO>builder()
                .company("Pizza Store")
                .description("Um novo produto foi adicionado ao pedido")
                .result(toProductsDTO(order)).build();
    }

}
