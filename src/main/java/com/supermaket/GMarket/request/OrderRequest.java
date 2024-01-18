package com.supermaket.GMarket.request;

import com.supermaket.GMarket.entity.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    @Valid
    private Long userId;

    @Valid
    private Set<Product> productsId;

    @DecimalMin(value = "0.0", message = "O valor do pedido deve ser maior que zero")
    @NotNull(message = "O preço total do pedido não pode ser nulo")
    @Schema(description = "O valor total do pedido", example = "25.00")
    private Double totalPrice;
}