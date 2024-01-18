package com.supermaket.GMarket.entityRequest;

import com.supermaket.GMarket.entity.Address;
import com.supermaket.GMarket.entity.Order;
import com.supermaket.GMarket.entity.Wallet;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @NotBlank(message = "A data de nascimento não pode estar em branco")
    @NotNull(message = "A data de nascimento não pode ser nulo")
    @Schema(description = "Data de nascimento", example = "06/02/2002")
    private Date birthDate;
    @Size(min = 8, max = 15, message = "A senha deve ter entre 8 e 15 caracteres")
    @NotBlank(message = "A senha não pode estar em branco")
    @NotNull(message = "A senha não pode ser nulo")
    @Schema(description = "Senha do usuário", example = "123456Senha")
    private String password;
    @Valid
    @Schema(description = "Usuário administrador", example = "true")
    private Boolean isAdmin = false;
    @Valid
    private Long walletId;

    @Valid
    private Long addressId;

    @Valid
    private List<Long> ordersList;
}
