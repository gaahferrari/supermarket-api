package com.supermaket.GMarket.request;

import com.supermaket.GMarket.entity.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

    @Size(min = 3, max = 30, message = "O nome do produto deve ter entre 3 e 30 caracteres")
    @NotBlank(message = "O nome do produto não pode estar em branco")
    @NotNull(message = "O nome do produto não pode ser nulo")
    @Schema(description = "Nome do produto", example = "Sabão em pó")
    private String name;
    @DecimalMin(value = "0.0", message = "O valor deve ser maior que zero")
    @NotNull(message = "O preço total do produto não pode ser nulo")
    @Schema(description = "O valor total do produto", example = "25.00")
    private Double price;


    @Size(min = 3, max = 30, message = "A descrição do produto deve ter entre 3 e 30 caracteres")
    @NotBlank(message = "A descrição do produto não pode estar em branco")
    @NotNull(message = "A descrição do produto não pode ser nulo")
    @Schema(description = "Descrição do produto", example = "Frutas caramelizadas com açúcar refinado")
    private String description;

    @Size(min = 3, max = 30, message = "A imagem do produto deve ter entre 3 e 30 caracteres")
    @NotBlank(message = "A imagem do produto não pode estar em branco")
    @NotNull(message = "A imagem do produto não pode ser nulo")
    @Schema(description = "Imagem do produto", example = "imagem.jpg")
    private String image;

    @Valid
    @Schema(description = "Indica se o produto está em estoque", example = "true")
    private Boolean inStock;

}
