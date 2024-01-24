package com.supermaket.GMarket.entities;

import com.supermaket.GMarket.entity.Address;
import com.supermaket.GMarket.entity.User;
import com.supermaket.GMarket.entity.Wallet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
@ExtendWith(MockitoExtension.class)
public class WalletEntityTest {

    @Test
    public void shouldAddUser_whenUserNameIsNotBlank() {
        //Arrange
        User user = User.builder()
                .userName("Gabriela")
                .name("Gabriela")
                .lastName("Ferrari")
                .address(new Address())
                .id(1L)
                .birthDate("06/07/1997")
                .favoriteProducts(new ArrayList<>())
                .isAdmin(false)
                .orders(new ArrayList<>())
                .wallets(new ArrayList<>())
                .build();

        Wallet wallet = Wallet.builder()
                .id(1L)
                .cardNumber("111111")
                .cardExp("10/25")
                .securityCode("111")
                .user(user)
                .cardOwnerName("Gabriela")
                .build();

        //Act
        wallet.addUser(user);

        //Assert
        assertEquals(user.getUserName(), wallet.getUser().getUserName());
        assertEquals("Gabriela", user.getUserName());
    }
    @Test
    public void shouldAddUser_whenUserNameIsBlank() {
        //Arrange
        User user = User.builder()
                .userName("")
                .name("Gabriela")
                .lastName("Ferrari")
                .address(new Address())
                .id(1L)
                .birthDate("06/07/1997")
                .favoriteProducts(new ArrayList<>())
                .isAdmin(false)
                .orders(new ArrayList<>())
                .wallets(new ArrayList<>())
                .build();

        Wallet wallet = Wallet.builder()
                .id(1L)
                .cardNumber("111111")
                .cardExp("10/25")
                .securityCode("111")
                .user(user)
                .cardOwnerName("Gabriela")
                .build();


        String expectedError = "O nome não pode estar em branco";

        //Act
        IllegalArgumentException actualError = assertThrows(IllegalArgumentException.class, () -> wallet.addUser(user));

        //Assert
        assertEquals(expectedError, actualError.getMessage());
    }

}
