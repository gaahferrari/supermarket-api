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

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequest {
    @Valid
    @Schema(description = "ID do usuário", example = "5")
    private Long userId;

    @Size(min = 1, max = 50, message = "O nome da rua deve ter entre 3 e 50 caracteres")
    @NotBlank(message = "O nome da rua não pode estar em branco")
    @NotNull(message = "O nome da rua não pode ser nulo")
    @Schema(description = "Nome da rua", example = "Avenida Paulista")
    private String streetName;

    @Size(min = 3, max = 20, message = "O número da rua deve ter entre 3 e 20 caracteres")
    @NotBlank(message = "O número da rua não pode estar em branco")
    @NotNull(message = "O número da rua não pode ser nulo")
    @Schema(description = "Número da rua", example = "2552")
    private String number;

    @Size(min = 8, max = 8, message = "O CEP da residência deve ter 8 caracteres")
    @NotBlank(message = "O CEP não pode estar em branco")
    @NotNull(message = "O CEP não pode ser nulo")
    @Schema(description = "CEP da residência", example = "09791-101")
    private String zipCode;

    @Size(min = 1, max = 50, message = "A cidade deve ter entre 4 e 50 caracteres")
    @NotBlank(message = "A cidade  não pode estar em branco")
    @NotNull(message = "A cidade  não pode ser nulo")
    @Schema(description = "Cidade do endereço", example = "São Bernardo do Campo")
    private String city;

    @Size(min = 1, max = 50, message = "O estado deve ter entre 4 e 50 caracteres")
    @NotBlank(message = "O estado  não pode estar em branco")
    @NotNull(message = "O estado  não pode ser nulo")
    @Schema(description = "Estado do endereço", example = "São Paulo")
    private String state;

    @Size(min = 4, max = 50, message = "O país deve ter entre 4 e 50 caracteres")
    @NotBlank(message = "O país  não pode estar em branco")
    @NotNull(message = "O país  não pode ser nulo")
    @Schema(description = "País do endereço", example = "Brasil")
    private String country;
}
