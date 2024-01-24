package com.supermaket.GMarket.service;

import com.supermaket.GMarket.DTO.AddressDTO;
import com.supermaket.GMarket.entity.Address;
import com.supermaket.GMarket.entity.User;
import com.supermaket.GMarket.exceptions.BadRequestException;
import com.supermaket.GMarket.mapper.AddressMapper;
import com.supermaket.GMarket.repository.AddressRepository;
import com.supermaket.GMarket.repository.UserRepository;
import com.supermaket.GMarket.request.AddressRequest;
import com.supermaket.GMarket.responses.BaseBodyResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;

    private final UserRepository userRepository;

    public BaseBodyResponse<List<AddressDTO>> getAll() {
        List<Address> addresses = addressRepository.findAll();
        if (addresses.isEmpty()) {
            throw new BadRequestException("A lista de endereços está vazia");
        }
        return AddressMapper.toListResponse(addresses);
    }

    @Transactional
    public BaseBodyResponse<Address> create(@Valid AddressRequest request) {
        Optional<User> user = userRepository.findById(request.getUserId());
        if (user.isEmpty()) {
            throw new BadRequestException("Usuário com o id " + request.getUserId() + " não existe");
        }
        Address newAddress = addressRepository.save(AddressMapper.toAddress(request));
        if (user.isPresent()) {
            newAddress.addUser(user.get());
        }
        return AddressMapper.toResponse(newAddress);
    }


    @Transactional
    public void delete(Long addressId) {
        Optional<Address> addressOptional = addressRepository.findById(addressId);
        if (addressOptional.isEmpty()) {
            throw new BadRequestException("Endereço com o ID " + addressId + " não encontrado");
        }
        Address address = addressOptional.get();

        if (address.getUser() != null) {
            address.getUser().setAddress(null);
            userRepository.save(address.getUser());
        }

        addressRepository.delete(address);
    }

    @Transactional
    public BaseBodyResponse<AddressDTO> update(Long addressId, @Valid AddressRequest request) {
        Optional<Address> addressOptional = addressRepository.findById(addressId);
        if (addressOptional.isEmpty()) {
            throw new BadRequestException("Endereço com o ID " + addressId + " não encontrado");
        }
       Address existingAddress = addressOptional.get();
        if (request.getStreetName() != null) {
            existingAddress.setStreetName(request.getStreetName());
        }
        if (request.getNumber() != null) {
            existingAddress.setNumber(request.getNumber());
        }
        if (request.getCity() != null) {
            existingAddress.setCity(request.getCity());
        }
        if (request.getZipCode() != null) {
            existingAddress.setZipCode(request.getZipCode());
        }
        if (request.getState() != null) {
            existingAddress.setState(request.getState());
        }
        if (request.getCountry() != null) {
            existingAddress.setCountry(request.getCountry());
        }


        Address updatedAddress = addressRepository.save(existingAddress);
        return AddressMapper.toResponseDTO(updatedAddress);
    }



}



