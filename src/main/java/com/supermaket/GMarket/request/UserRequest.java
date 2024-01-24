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

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @Size(min = 3, max = 30, message = "O primeiro nome deve ter entre 3 e 30 caracteres")
    @NotBlank(message = "O primeiro nome não pode estar em branco")
    @NotNull(message = "O primeiro nome não pode ser nulo")
    @Schema(description = "Primeiro nome", example = "Francisco")
    private String name;

    @Size(min = 3, max = 30, message = "O sobrenome deve ter entre 3 e 30 caracteres")
    @NotBlank(message = "O sobrenome não pode estar em branco")
    @NotNull(message = "O sobrenome não pode ser nulo")
    @Schema(description = "sobrenome", example = "Santos")
    private String lastName;

    @Size(min = 3, max = 30, message = "O usuário deve ter entre 3 e 30 caracteres")
    @NotBlank(message = "O usuário não pode estar em branco")
    @NotNull(message = "O usuário não pode ser nulo")
    @Schema(description = "Nome do usuário", example = "Franciscosantos123")
    private String userName;

    @Size(min = 3, max = 30, message = "O usuário deve ter entre 3 e 30 caracteres")
    @NotNull(message = "A data de nascimento não pode ser nulo")
    @Schema(description = "Data de nascimento", example = "06/02/2002")
    private String birthDate;
    @Size(min = 8, max = 15, message = "A senha deve ter entre 8 e 15 caracteres")
    @NotBlank(message = "A senha não pode estar em branco")
    @NotNull(message = "A senha não pode ser nulo")
    @Schema(description = "Senha do usuário", example = "123456Senha")
    private String password;
    @Valid
    @Schema(description = "Usuário administrador", example = "true")
    private Boolean isAdmin = false;
    @Valid
    @Schema(description = "ID da carteira", example = "12345")
    private Long walletId;

    @Valid
    @Schema(description = "ID do endereço", example = "98765")
    private Long addressId;

    @Valid
    @Schema(description = "Lista de IDs de pedidos", example = "[1001, 1002, 1003]")
    private List<Long> ordersList;
}
