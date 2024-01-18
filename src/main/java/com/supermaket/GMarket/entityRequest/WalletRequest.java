package com.supermaket.GMarket.entityRequest;

import com.supermaket.GMarket.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
public class WalletRequest {

    @Valid
    private Long userId;

    @Size(min = 3, max = 15, message = "O número do cartão deve ter entre 3 e 50 caracteres")
    @NotBlank(message = "O número do cartão não pode estar em branco")
    @NotNull(message = "O número do cartão não pode ser nulo")
    @Schema(description = "Número do cartão", example = "23423234412")
    private String cardNumber;

    @Size(min = 5, max = 5, message = "A data de expiração do cartão deve ter 3 caracteres")
    @NotBlank(message = "A data de expiração do cartão não pode estar em branco")
    @NotNull(message = "A data de expiração do cartão não pode ser nulo")
    @Schema(description = "Data de expiração do cartão", example = "01/24")
    private String cardExp;

    @Size(min = 3, max = 50, message = "O nome do titular do cartão deve ter entre 3 e 50 caracteres")
    @NotBlank(message = "O nome do titular do cartão não pode estar em branco")
    @NotNull(message = "O nome do titular do cartão não pode ser nulo")
    @Schema(description = "Nome do titular do cartão", example = "Francisco dos Santos")
    private String cardOwnerName;

    @Size(min = 3, max = 3, message = "O CVV do cartão deve ter 3 caracteres")
    @NotBlank(message = "O CVV do cartão não pode estar em branco")
    @NotNull(message = "O CVV do cartão não pode ser nulo")
    @Schema(description = "CVV do cartão", example = "030")
    private String CVV;

    @Valid
    @Schema(description = "Selecionar cartão de crédito", example = "true")
    private Boolean creditCard = false;
    @Valid

    @Schema(description = "Selecionar cartão de débito", example = "true")
    private Boolean debitCard = false;
}