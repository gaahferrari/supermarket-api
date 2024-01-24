package com.supermaket.GMarket.entities;

import com.supermaket.GMarket.entity.Address;
import com.supermaket.GMarket.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AddressEntityTest {

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

        Address address = Address.builder()
                .id(1L)
                .streetName("Rua 1")
                .city("SBC")
                .state("São Paulo")
                .country("Brasil")
                .zipCode("09791480")
                .number("122")
                .build();

        //Act
        address.addUser(user);

        //Assert
        assertEquals(user.getUserName(), address.getUser().getUserName());
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

        Address address = Address.builder()
                .id(1L)
                .streetName("Rua 1")
                .city("SBC")
                .state("São Paulo")
                .country("Brasil")
                .zipCode("09791480")
                .number("122")
                .build();


        String expectedError = "O nome não pode estar em branco";

        //Act
        IllegalArgumentException actualError = assertThrows(IllegalArgumentException.class, () -> address.addUser(user));

        //Assert
        assertEquals(expectedError, actualError.getMessage());
    }

}
