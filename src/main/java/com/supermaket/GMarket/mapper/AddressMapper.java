package com.supermaket.GMarket.mapper;

import com.supermaket.GMarket.DTO.AddressDTO;
import com.supermaket.GMarket.entity.Address;
import com.supermaket.GMarket.request.AddressRequest;
import com.supermaket.GMarket.responses.BaseBodyResponse;

import java.util.List;

public class AddressMapper {

    public static Address toAddress(AddressRequest request){
        return Address.builder()
                .streetName(request.getStreetName())
                .zipCode(request.getZipCode())
                .city(request.getCity())
                .country(request.getCountry())
                .state(request.getState())
                .number(request.getNumber())
                .build();
    }

    public static AddressDTO toDTO(Address address){
        return AddressDTO.builder()
                .streetName(address.getStreetName())
                .zipCode(address.getZipCode())
                .city(address.getCity())
                .country(address.getCountry())
                .state(address.getState())
                .number(address.getNumber())
                .id(address.getId())
                .userId(address.getUser().getId())
                .build();
    }

    public static BaseBodyResponse<AddressDTO> toResponseDTO(Address address){
        return BaseBodyResponse.<AddressDTO>builder()
                .company("G-Market")
                .description("O endereço do usuário " + address.getUser().getUserName() + " foi criado com sucesso!" )
                .result(toDTO(address)).build();
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
