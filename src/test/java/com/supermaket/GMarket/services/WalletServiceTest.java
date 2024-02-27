package com.supermaket.GMarket.services;

import com.supermaket.GMarket.entity.Address;
import com.supermaket.GMarket.entity.Category;
import com.supermaket.GMarket.entity.User;
import com.supermaket.GMarket.entity.Wallet;
import com.supermaket.GMarket.exceptions.BadRequestException;
import com.supermaket.GMarket.exceptions.NotFoundException;
import com.supermaket.GMarket.mapper.AddressMapper;
import com.supermaket.GMarket.mapper.CategoryMapper;
import com.supermaket.GMarket.mapper.WalletMapper;
import com.supermaket.GMarket.repository.UserRepository;
import com.supermaket.GMarket.repository.WalletRepository;
import com.supermaket.GMarket.request.AddressRequest;
import com.supermaket.GMarket.request.CategoryRequest;
import com.supermaket.GMarket.request.WalletRequest;
import com.supermaket.GMarket.service.WalletService;
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
public class WalletServiceTest {

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    @Spy
    WalletService walletService;


    @Test
    public void shouldReturnAllWallets_whenListIsNotEmpty() {
        try (MockedStatic<WalletMapper> walletMapper = mockStatic(WalletMapper.class)) {
            // Arrange
            Wallet wallet = mock(Wallet.class);
            List<Wallet> wallets = Collections.singletonList(wallet);


            when(walletRepository.findAll()).thenReturn(wallets);
            walletMapper.when(() -> WalletMapper.toWallet(any(WalletRequest.class))).thenReturn(wallet);

            // Act
            walletService.getAll();

            // Assert

            verify(walletRepository).findAll();
        }
    }

    @Test
    public void shouldReturnAllWallets_whenListIsEmpty() {

        //Arrange
        when(walletRepository.findAll()).thenReturn(Collections.emptyList());
        String expectedError = "A lista de carteiras está vazia";
        //Act
        BadRequestException actualError = assertThrows(BadRequestException.class, () -> walletService.getAll());

        //Assert
        assertEquals(expectedError, actualError.getMessage());
    }

    @Test
    public void shouldCreateWallet_whenRequestIsValid(){

        try(MockedStatic<WalletMapper> walletMapper = mockStatic(WalletMapper.class)) {
            //Arrange
            User user = mock(User.class);

            WalletRequest walletRequest = mock(WalletRequest.class);

            Wallet wallet = mock(Wallet.class);

            when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
            when(walletRepository.save(any(Wallet.class))).thenReturn(wallet);
            walletMapper.when(() -> WalletMapper.toWallet(any(WalletRequest.class))).thenReturn(wallet);

            //Act
            walletService.create(walletRequest);

            //Assert
            verify(walletRepository).save(wallet);
            verify(wallet).addUser(user);
        }
    }

    @Test
    public void shouldCreateWallet_whenRequestIsNotValid(){

        //Arrange

        WalletRequest walletRequest = mock(WalletRequest.class);


        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        String expectedError = "Usuário com o id " + walletRequest.getUserId() + " não existe";
        //Act
        BadRequestException actualError = assertThrows(BadRequestException.class, () -> walletService.create(walletRequest));

        //Assert
        assertEquals(expectedError, actualError.getMessage());

    }

    @Test
    public void shouldDeleteWallet_whenRequestIsValid(){


        //Arrange
        Wallet wallet = mock(Wallet.class);

        when(walletRepository.findById(anyLong())).thenReturn(Optional.of(wallet));

        //Act
        walletService.delete(wallet.getId());

        //Assert

        verify(walletRepository).delete(wallet);

    }



    @Test
    public void shouldDeleteWallet_whenRequestIsNotValid(){

        //Arrange
        Wallet wallet = mock(Wallet.class);

        when(walletRepository.findById(any())).thenReturn(Optional.empty());


        String expectedError = "Carteira com o ID " + wallet.getId() + " não encontrado";
        //Act
        BadRequestException actualError = assertThrows(BadRequestException.class, () -> walletService.delete(wallet.getId()));

        //Assert
        assertEquals(expectedError, actualError.getMessage());

    }

    @Test
    public void shouldFindWalletById_whenRequestIsValid() {
        try (MockedStatic<WalletMapper> walletMapper = mockStatic(WalletMapper.class)) {
            //Arrange
            Wallet wallet = mock(Wallet.class);


            when(walletRepository.findById(anyLong())).thenReturn(Optional.of(wallet));
            walletMapper.when(() -> WalletMapper.toWallet(any(WalletRequest.class))).thenReturn(wallet);

            //Act
            walletService.getById(wallet.getId());

            //Assert
            verify(walletRepository).findById(wallet.getId());
        }
    }

    @Test
    public void shouldFindWalletById_whenRequestIsNotValid() {

        //Arrange
        Wallet wallet = mock(Wallet.class);

        when(walletRepository.findById(any())).thenReturn(Optional.empty());
        String expectedError = "Carteira não encontrada com o ID: " + wallet.getId();
        //Act
        NotFoundException actualError = assertThrows(NotFoundException.class, () -> walletService.getById(wallet.getId()));

        //Assert
        assertEquals(expectedError, actualError.getMessage());

    }


}
