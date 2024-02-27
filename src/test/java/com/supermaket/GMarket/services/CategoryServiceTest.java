package com.supermaket.GMarket.services;

import com.supermaket.GMarket.entity.Category;
import com.supermaket.GMarket.entity.Product;
import com.supermaket.GMarket.exceptions.BadRequestException;
import com.supermaket.GMarket.exceptions.NotFoundException;
import com.supermaket.GMarket.mapper.CategoryMapper;
import com.supermaket.GMarket.repository.CategoryRepository;
import com.supermaket.GMarket.repository.ProductRepository;
import com.supermaket.GMarket.request.CategoryRequest;
import com.supermaket.GMarket.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    @Mock
    CategoryRepository categoryRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    @Spy
    CategoryService categoryService;

    @Test
    public void shouldReturnAllCategories_whenListIsNotEmpty() {
        try (MockedStatic<CategoryMapper> categoryMapper = mockStatic(CategoryMapper.class)) {
            // Arrange
            Category category = mock(Category.class);
            List<Category> categories = Collections.singletonList(category);


            when(categoryRepository.findAll()).thenReturn(categories);
            categoryMapper.when(() -> CategoryMapper.toCategory(any(CategoryRequest.class))).thenReturn(category);

            // Act
            categoryService.getAll();

            // Assert

            verify(categoryRepository).findAll();
        }
    }

    @Test
    public void shouldReturnAllCategories_whenListIsEmpty() {

        //Arrange
        when(categoryRepository.findAll()).thenReturn(Collections.emptyList());
        String expectedError = "A lista de categorias está vazia";
        //Act
        BadRequestException actualError = assertThrows(BadRequestException.class, () -> categoryService.getAll());

        //Assert
        assertEquals(expectedError, actualError.getMessage());
    }

    @Test
    public void shouldFindCategoryById_whenRequestIsValid() {
        try (MockedStatic<CategoryMapper> categoryMapper = mockStatic(CategoryMapper.class)) {
            //Arrange
            Category category = mock(Category.class);


            when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
            categoryMapper.when(() -> CategoryMapper.toCategory(any(CategoryRequest.class))).thenReturn(category);

            //Act
            categoryService.getById(category.getId());

            //Assert
            verify(categoryRepository).findById(category.getId());
        }
    }

    @Test
    public void shouldFindCategoryById_whenRequestIsNotValid() {

        //Arrange
        Category category = mock(Category.class);

        when(categoryRepository.findById(any())).thenReturn(Optional.empty());
        String expectedError = "Categoria com o ID " + category.getId() + " não encontrado";
        //Act
        BadRequestException actualError = assertThrows(BadRequestException.class, () -> categoryService.getById(category.getId()));

        //Assert
        assertEquals(expectedError, actualError.getMessage());

    }

    @Test
    public void shouldCreateCategory_whenRequestIsValid() {

        try (MockedStatic<CategoryMapper> categoryMapper = mockStatic(CategoryMapper.class)) {
            //Arrange
            CategoryRequest categoryRequest = mock(CategoryRequest.class);

            Category category = mock(Category.class);

            when(categoryRepository.save(any(Category.class))).thenReturn(category);
            categoryMapper.when(() -> CategoryMapper.toCategory(any(CategoryRequest.class))).thenReturn(category);

            //Act
            categoryService.create(categoryRequest);

            //Assert
            verify(categoryRepository).save(category);
        }
    }

    @Test
    public void shouldCreateCategory_whenRequestIsNotValid() {

        //Arrange

        CategoryRequest categoryRequest = mock(CategoryRequest.class);

        String expectedError = "Erro ao cadastrar o usuário";
        //Act
        BadRequestException actualError = assertThrows(BadRequestException.class, () -> categoryService.create(categoryRequest));

        //Assert
        assertEquals(expectedError, actualError.getMessage());

    }

    @Test
    public void shouldAddProductToCategory_whenRequestIsValid() {
        try (MockedStatic<CategoryMapper> categoryMapper = mockStatic(CategoryMapper.class)) {
            //Arrange
            Category category = mock(Category.class);

            Product product = mock(Product.class);

            when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
            when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
            categoryMapper.when(() -> CategoryMapper.toCategory(any(CategoryRequest.class))).thenReturn(category);

            //Act
            categoryService.addProductsToCategory(category.getId(), product.getId());

            //Assert
            verify(categoryRepository).save(category);
            verify(category).addProduct(product);
            verify(categoryRepository).findById(category.getId());
            verify(productRepository).findById(product.getId());
        }
    }

    @Test
    public void shouldAddProductToCategory_whenRequestIsNotValid() {

        //Arrange
        Category category = mock(Category.class);
        Product product = mock(Product.class);


        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        String expectedError = "Categoria ou ingrediente não foi encontrado";
        //Act
        NotFoundException actualError = assertThrows(NotFoundException.class, () -> categoryService.addProductsToCategory(category.getId(), product.getId()));

        //Assert
        assertEquals(expectedError, actualError.getMessage());

    }

    @Test
    public void shouldRemoveProductToCategory_whenRequestIsValid() {

            //Arrange
            Category category = mock(Category.class);

            Product product = mock(Product.class);

            when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
            when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
            category.addProduct(product);
            //Act

            categoryService.removeProduct(category.getId(), product.getId());

            //Assert
        verify(category).removeProduct(product);
        verify(categoryRepository).save(category);
        verify(categoryRepository).findById(category.getId());
        verify(productRepository).findById(product.getId());
        }


    @Test
    public void shouldRemoveProductToCategory_whenRequestIsNotValid() {

        //Arrange
        Category category = mock(Category.class);
        Product product = mock(Product.class);


        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        String expectedError = "A lista de produtos favoritos está vazia";
        //Act
        BadRequestException actualError = assertThrows(BadRequestException.class, () -> categoryService.removeProduct(category.getId(), product.getId()));

        //Assert
        assertEquals(expectedError, actualError.getMessage());

    }


    @Test
    public void shouldDeleteProductAndCategory_whenRequestIsValid() {

        //Arrange
        Category category = mock(Category.class);
        Long categoryId = 1L;

        Product product = mock(Product.class);
        List<Product> products = new ArrayList<>();
        products.add(product);

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(category.getProducts()).thenReturn(products);

        //Act

        categoryService.deleteCategoryAndProducts(categoryId);

        //Assert
        verify(categoryRepository).findById(categoryId);
        verify(category).getProducts();
        verify(categoryRepository).delete(category);
    }


    @Test
    public void shouldDeleteProductAndCategory_whenRequestIsNotValid() {

        //Arrange
        Category category = mock(Category.class);

        Product product = mock(Product.class);
        List<Product> products = new ArrayList<>();
        products.add(product);

        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());


        String expectedError = "Categoria com o ID " + category.getId() + " não encontrada";
        //Act
       EntityNotFoundException actualError = assertThrows(EntityNotFoundException.class, () -> categoryService.deleteCategoryAndProducts(category.getId()));

        //Assert
        assertEquals(expectedError, actualError.getMessage());

    }

    @Test
    public void shouldFindCategoryByName_whenRequestIsValid() {

        try (MockedStatic<CategoryMapper> categoryMapper = mockStatic(CategoryMapper.class)) {
            //Arrange
            Category category = mock(Category.class);
            String categoryName = "Bebidas";


            when(categoryRepository.findCategoryByName(categoryName)).thenReturn((category));
            categoryMapper.when(() -> CategoryMapper.toCategory(any(CategoryRequest.class))).thenReturn(category);


            //Act
            categoryService.getByCategory(categoryName);

            //Assert
            verify(categoryRepository).findCategoryByName(categoryName);
        }
    }

    @Test
    public void shouldFindCategoryByName_whenRequestIsNotValid() {

        //Arrange

        Category category = mock(Category.class);

        String expectedError = "O nome desta categoria não existe";
        //Act
        NotFoundException actualError = assertThrows(NotFoundException.class, () -> categoryService.getByCategory(category.getName()));

        //Assert
        assertEquals(expectedError, actualError.getMessage());

    }


}
