package com.supermaket.GMarket.services;

import com.supermaket.GMarket.DTO.AddressDTO;
import com.supermaket.GMarket.entity.Address;
import com.supermaket.GMarket.entity.User;
import com.supermaket.GMarket.exceptions.BadRequestException;
import com.supermaket.GMarket.mapper.AddressMapper;
import com.supermaket.GMarket.repository.AddressRepository;
import com.supermaket.GMarket.repository.UserRepository;
import com.supermaket.GMarket.request.AddressRequest;
import com.supermaket.GMarket.responses.BaseBodyResponse;
import com.supermaket.GMarket.service.AddressService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {

    @Mock
    AddressRepository addressRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    @Spy
    AddressService addressService;

    @Test
    public void shouldReturnAllAddresses_whenListIsNotEmpty() {
        try (MockedStatic<AddressMapper> addressMapper = mockStatic(AddressMapper.class)) {
            // Arrange
            Address address = mock(Address.class);
            List<Address> addresses = Collections.singletonList(address);


            when(addressRepository.findAll()).thenReturn(addresses);
            addressMapper.when(() -> AddressMapper.toAddress(any(AddressRequest.class))).thenReturn(address);

            // Act
            addressService.getAll();

            // Assert

            verify(addressRepository).findAll();
        }
    }

    @Test
    public void shouldReturnAllAddresses_whenListIsEmpty() {

        //Arrange
        when(addressRepository.findAll()).thenReturn(Collections.emptyList());
        String expectedError = "A lista de endereços está vazia";
        //Act
        BadRequestException actualError = assertThrows(BadRequestException.class, () -> addressService.getAll());

        //Assert
        assertEquals(expectedError, actualError.getMessage());
    }

    @Test
    public void shouldCreateAddress_whenRequestIsValid(){

        try(MockedStatic<AddressMapper> addressMapper = mockStatic(AddressMapper.class)) {
            //Arrange
            User user = mock(User.class);

            AddressRequest addressRequest = mock(AddressRequest.class);

            Address address = mock(Address.class);

            when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
            when(addressRepository.save(any(Address.class))).thenReturn(address);
            addressMapper.when(() -> AddressMapper.toAddress(any(AddressRequest.class))).thenReturn(address);

            //Act
            addressService.create(addressRequest);

            //Assert
            verify(addressRepository).save(address);
            verify(address).addUser(user);
        }
    }

    @Test
    public void shouldCreateAddress_whenRequestIsNotValid(){

        //Arrange

        AddressRequest addressRequest = mock(AddressRequest.class);


        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        String expectedError = "Usuário com o id " + addressRequest.getUserId() + " não existe";
        //Act
        BadRequestException actualError = assertThrows(BadRequestException.class, () -> addressService.create(addressRequest));

        //Assert
        assertEquals(expectedError, actualError.getMessage());

    }


    @Test
    public void shouldUpdateAddress_whenRequestIsValid(){

        try(MockedStatic<AddressMapper> addressMapper = mockStatic(AddressMapper.class)) {
            //Arrange
            AddressRequest addressRequest = mock(AddressRequest.class);
            Long addressId = 1L;

            Address address = mock(Address.class);
            address.setId(addressId);

            when(addressRepository.findById(addressId)).thenReturn(Optional.of(address));
            when(addressRepository.save(address)).thenReturn(address);
            addressMapper.when(() -> AddressMapper.toAddress(any(AddressRequest.class))).thenReturn(address);


            //Act
            addressService.update(addressId, addressRequest);

            //Assert

            verify(addressRepository).save(address);
        }
    }



    @Test
    public void shouldUpdateAddress_whenRequestIsNotValid(){

        //Arrange
        Long addressId = 1L;
        AddressRequest addressRequest = mock(AddressRequest.class);

       lenient().when(userRepository.findById(any())).thenReturn(Optional.empty());


        String expectedError = "Endereço com o ID " + addressId + " não encontrado";
        //Act
        BadRequestException actualError = assertThrows(BadRequestException.class, () -> addressService.update(addressId, addressRequest));

        //Assert
        assertEquals(expectedError, actualError.getMessage());

    }

    @Test
    public void shouldDeleteAddress_whenRequestIsValid(){


            //Arrange
            Address address = mock(Address.class);

            when(addressRepository.findById(anyLong())).thenReturn(Optional.of(address));



            //Act
            addressService.delete(address.getId());

            //Assert

            verify(addressRepository).delete(address);

    }



    @Test
    public void shouldDeleteAddress_whenRequestIsNotValid(){

        //Arrange
        Address address = mock(Address.class);

        when(addressRepository.findById(any())).thenReturn(Optional.empty());


        String expectedError = "Endereço com o ID " + address.getId() + " não encontrado";
        //Act
        BadRequestException actualError = assertThrows(BadRequestException.class, () -> addressService.delete(address.getId()));

        //Assert
        assertEquals(expectedError, actualError.getMessage());

    }





}
