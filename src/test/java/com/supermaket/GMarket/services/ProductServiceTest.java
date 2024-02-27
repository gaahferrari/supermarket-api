package com.supermaket.GMarket.services;

import com.supermaket.GMarket.entity.Product;
import com.supermaket.GMarket.exceptions.BadRequestException;
import com.supermaket.GMarket.exceptions.NotFoundException;
import com.supermaket.GMarket.mapper.ProductMapper;
import com.supermaket.GMarket.repository.ProductRepository;
import com.supermaket.GMarket.request.ProductRequest;
import com.supermaket.GMarket.service.ProductService;
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
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    @Spy
    ProductService productService;

    @Test
    public void shouldReturnAllProducts_whenListIsNotEmpty() {
        try (MockedStatic<ProductMapper> productMapper = mockStatic(ProductMapper.class)) {
            // Arrange
            Product product = mock(Product.class);
            List<Product> products = Collections.singletonList(product);


            when(productRepository.findAll()).thenReturn(products);
            productMapper.when(() -> ProductMapper.toProduct(any(ProductRequest.class))).thenReturn(product);

            // Act
            productService.getAll();

            // Assert

            verify(productRepository).findAll();
        }
    }

    @Test
    public void shouldReturnAllProducts_whenListIsEmpty() {

        //Arrange
        when(productRepository.findAll()).thenReturn(Collections.emptyList());
        String expectedError = "A lista de produtos está vazia";
        //Act
        BadRequestException actualError = assertThrows(BadRequestException.class, () -> productService.getAll());

        //Assert
        assertEquals(expectedError, actualError.getMessage());
    }


    @Test
    public void shouldCreateProduct_whenRequestIsValid() {

        try (MockedStatic<ProductMapper> productMapper = mockStatic(ProductMapper.class)) {
            //Arrange
            ProductRequest productRequest = mock(ProductRequest.class);

            Product product = mock(Product.class);

            when(productRepository.save(any(Product.class))).thenReturn(product);
            productMapper.when(() -> ProductMapper.toProduct(any(ProductRequest.class))).thenReturn(product);

            //Act
            productService.create(productRequest);

            //Assert
            verify(productRepository).save(product);
        }
    }

    @Test
    public void shouldCreateProduct_whenRequestIsNotValid() {

        //Arrange
        ProductRequest productRequest = mock(ProductRequest.class);

        String expectedError = "Erro ao criar um novo produto";
        //Act
        BadRequestException actualError = assertThrows(BadRequestException.class, () -> productService.create(productRequest));

        //Assert
        assertEquals(expectedError, actualError.getMessage());

    }

    @Test
    public void shouldDeleteProduct_whenRequestIsValid(){

        //Arrange
        Product product = mock(Product.class);

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        //Act
        productService.deleteProduct(product.getId());

        //Assert
        verify(productRepository).delete(product);

    }

    @Test
    public void shouldDeleteProduct_whenRequestIsNotValid(){

        //Arrange
        Product product = mock(Product.class);

        when(productRepository.findById(any())).thenReturn(Optional.empty());
        String expectedError = "Erro ao deletar o produto. Ele pode estar vinculado a outra entidade";
        //Act
        BadRequestException actualError = assertThrows(BadRequestException.class, () -> productService.deleteProduct(product.getId()));

        //Assert
        assertEquals(expectedError, actualError.getMessage());

    }

    @Test
    public void shouldFindCategoryById_whenRequestIsValid() {
        try (MockedStatic<ProductMapper> productMapper = mockStatic(ProductMapper.class)) {
            //Arrange
            Product product = mock(Product.class);


            when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
            productMapper.when(() -> ProductMapper.toProduct(any(ProductRequest.class))).thenReturn(product);

            //Act
            productService.getById(product.getId());

            //Assert
            verify(productRepository).findById(product.getId());
        }
    }

    @Test
    public void shouldFindCategoryById_whenRequestIsNotValid() {

        //Arrange
        Product product = mock(Product.class);

        when(productRepository.findById(any())).thenReturn(Optional.empty());
        String expectedError = "Produto não encontrado com o ID: " + product.getId();
        //Act
        NotFoundException actualError = assertThrows(NotFoundException.class, () -> productService.getById(product.getId()));

        //Assert
        assertEquals(expectedError, actualError.getMessage());
    }


    @Test
    public void shouldUpdateProduct_whenRequestIsValid(){

        try(MockedStatic<ProductMapper> productMapper = mockStatic(ProductMapper.class)) {
            //Arrange
            ProductRequest productRequest = mock(ProductRequest.class);
            Long productId = 1L;

            Product product = mock(Product.class);
            product.setId(productId);

            when(productRepository.findById(productId)).thenReturn(Optional.of(product));
            when(productRepository.save(product)).thenReturn(product);
            productMapper.when(() -> ProductMapper.toProduct(any(ProductRequest.class))).thenReturn(product);


            //Act
            productService.update(productId, productRequest);

            //Assert

            verify(productRepository).save(product);
        }
    }


    @Test
    public void shouldUpdateProduct_whenRequestIsNotValid(){

        //Arrange
        Long productId = 1L;
        ProductRequest productRequest = mock(ProductRequest.class);


        String expectedError = "Produto com o ID " + productId + " não encontrado";
        //Act
        BadRequestException actualError = assertThrows(BadRequestException.class, () -> productService.update(productId, productRequest));

        //Assert
        assertEquals(expectedError, actualError.getMessage());

    }

}
