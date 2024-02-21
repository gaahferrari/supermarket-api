package com.supermaket.GMarket.mapper;


import com.supermaket.GMarket.DTO.ProductDTO;
import com.supermaket.GMarket.entity.Product;
import com.supermaket.GMarket.request.ProductRequest;
import com.supermaket.GMarket.responses.BaseBodyResponse;

import java.util.List;

public class ProductMapper {
    public static Product toProduct(ProductRequest request){
        return Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .description(request.getDescription())
                .image(request.getImage())
                .inStock(true)
                .quantity(1L)
                .build();
    }

    public static ProductDTO toDTO(Product product){
        return ProductDTO.builder()
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .image(product.getImage())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .id(product.getId())
                .inStock(product.getInStock())
                .quantity(product.getQuantity())
                .build();
    }


    public static BaseBodyResponse<ProductDTO> toResponse(Product product){
        return BaseBodyResponse.<ProductDTO>builder()
                .company("G-Market")
                .description("O produto " + product.getName() + " foi criado com sucesso!" )
                .result(toDTO(product)).build();
    }

    public static BaseBodyResponse<List<ProductDTO>> toListResponse(List<Product> products){
        List<ProductDTO> productDTOS = products.stream().map(ProductMapper::toDTO).toList();
        return BaseBodyResponse.<List<ProductDTO>>builder()
                .company("G-Market")
                .description("Lista de produtos cadastrados")
                .result(productDTOS)
                .build();
    }

}
