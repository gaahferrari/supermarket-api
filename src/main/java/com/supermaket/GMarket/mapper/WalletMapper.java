package com.supermaket.GMarket.mapper;

import com.supermaket.GMarket.DTO.AddressDTO;
import com.supermaket.GMarket.DTO.OrderDTO;
import com.supermaket.GMarket.DTO.WalletDTO;
import com.supermaket.GMarket.entity.Address;
import com.supermaket.GMarket.entity.Order;
import com.supermaket.GMarket.entity.Wallet;
import com.supermaket.GMarket.request.OrderRequest;
import com.supermaket.GMarket.request.WalletRequest;
import com.supermaket.GMarket.responses.BaseBodyResponse;

import java.util.List;

public class WalletMapper {
    public static Wallet toWallet(WalletRequest request){
        return Wallet.builder()
                .cardNumber(request.getCardNumber())
                .cardExp(request.getCardExp())
                .cardOwnerName(request.getCardOwnerName())
                .CVV(request.getCVV())
                .debitCard(request.getDebitCard())
                .creditCard(request.getDebitCard())
                .build();
    }

    public static WalletDTO toDTO(Wallet wallet){
        return WalletDTO.builder()
                .cardNumber(wallet.getCardNumber())
                .cardExp(wallet.getCardExp())
                .cardOwnerName(wallet.getCardOwnerName())
                .CVV(wallet.getCVV())
                .debitCard(wallet.getDebitCard())
                .creditCard(wallet.getDebitCard())
                .id(wallet.getId())
                .userId(wallet.getId())
                .build();
    }


    public static BaseBodyResponse<Wallet> toResponse(Wallet wallet){
        return BaseBodyResponse.<Wallet>builder()
                .company("G-Market")
                .description("O método de pagamento do usuário " + wallet.getUser().getUserName() + " foi criado com sucesso!" )
                .result(wallet).build();
    }

    public static BaseBodyResponse<List<WalletDTO>> toListResponse(List<Wallet> wallets){
        List<WalletDTO> addressDTOS = wallets.stream().map(WalletMapper::toDTO).toList();
        return BaseBodyResponse.<List<WalletDTO>>builder()
                .company("G-Market")
                .description("Lista de métodos de pagamentos cadastrados")
                .result(addressDTOS)
                .build();
    }
}
