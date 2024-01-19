package com.supermaket.GMarket.mapper;

import com.supermaket.GMarket.DTO.AddressDTO;
import com.supermaket.GMarket.DTO.UserDTO;
import com.supermaket.GMarket.DTO.UserOrderDTO;
import com.supermaket.GMarket.entity.Address;
import com.supermaket.GMarket.entity.Order;
import com.supermaket.GMarket.entity.User;
import com.supermaket.GMarket.request.AddressRequest;
import com.supermaket.GMarket.responses.BaseBodyResponse;

import java.util.List;

public class AddressMapper {

    public static Address toAddress(AddressRequest request){
        return Address.builder()
                .streetName(request.getStreetName())
                .CEP(request.getCEP())
                .city(request.getCity())
                .country(request.getCountry())
                .state(request.getState())
                .number(request.getNumber())
                .build();
    }

    public static AddressDTO toDTO(Address address){
        return AddressDTO.builder()
                .streetName(address.getStreetName())
                .CEP(address.getCEP())
                .city(address.getCity())
                .country(address.getCountry())
                .state(address.getState())
                .number(address.getNumber())
                .id(address.getId())
                .userId(address.getUser().getId())
                .build();
    }

    public static BaseBodyResponse<Address> toResponse(Address address){
        return BaseBodyResponse.<Address>builder()
                .company("G-Market")
                .description("O endereço do usuário " + address.getUser().getUserName() + " foi criado com sucesso!" )
                .result(address).build();
    }

    public static BaseBodyResponse<List<AddressDTO>> toListResponse(List<Address> addresses){
        List<AddressDTO> addressDTOS = addresses.stream().map(AddressMapper::toDTO).toList();
        return BaseBodyResponse.<List<AddressDTO>>builder()
                .company("G-Market")
                .description("Lista de endereços do usuários")
                .result(addressDTOS)
                .build();
    }


}
