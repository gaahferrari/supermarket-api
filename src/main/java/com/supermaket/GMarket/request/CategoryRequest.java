package com.supermaket.GMarket.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {

    @Size(min = 3, max = 30, message = "A categoria do produto deve ter entre 3 e 30 caracteres")
    @NotBlank(message = "A categoria do produto não pode estar em branco")
    @NotNull(message = "A categoria do produto não pode ser nulo")
    @Schema(description = "categoria do produto", example = "Fruta")
    private String name;

    @Valid
    private List<Long> productsId;
}
