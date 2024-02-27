package com.supermaket.GMarket.service;

import com.supermaket.GMarket.DTO.CategoryDTO;
import com.supermaket.GMarket.DTO.CategoryProductDTO;
import com.supermaket.GMarket.DTO.UserDTO;
import com.supermaket.GMarket.entity.Address;
import com.supermaket.GMarket.entity.Category;
import com.supermaket.GMarket.entity.Product;
import com.supermaket.GMarket.entity.User;
import com.supermaket.GMarket.exceptions.BadRequestException;
import com.supermaket.GMarket.exceptions.NotFoundException;
import com.supermaket.GMarket.mapper.AddressMapper;
import com.supermaket.GMarket.mapper.CategoryMapper;
import com.supermaket.GMarket.mapper.UserMapper;
import com.supermaket.GMarket.repository.CategoryRepository;
import com.supermaket.GMarket.repository.ProductRepository;
import com.supermaket.GMarket.request.CategoryRequest;
import com.supermaket.GMarket.responses.BaseBodyResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Transactional
    public BaseBodyResponse<List<CategoryProductDTO>> getAll() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            throw new BadRequestException("A lista de categorias está vazia");
        }
        return CategoryMapper.toListResponse(categories);
    }

    @Transactional
    public BaseBodyResponse<Category> getById(Long categoryId) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if (categoryOptional.isEmpty()) {
            throw new BadRequestException("Categoria com o ID " + categoryId + " não encontrado");
        }
        Category category = categoryOptional.get();
        return CategoryMapper.toResponse(category);
    }

    public BaseBodyResponse<Category> create(@Valid CategoryRequest request) {
        Category category = categoryRepository.save(CategoryMapper.toCategory(request));
        if (category != null) {
            return CategoryMapper.toResponse(category);
        } else {
            throw new BadRequestException("Erro ao cadastrar o usuário");
        }

    }

    @Transactional
    public void deleteCategoryAndProducts(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Categoria com o ID " + categoryId + " não encontrada"));

        List<Product> products = category.getProducts();
        for (Product product : products) {
            product.getCategories().remove(category);
        }
        products.clear();

        categoryRepository.delete(category);
    }

    @Transactional
    public BaseBodyResponse<CategoryProductDTO> addProductsToCategory(Long categoryId, Long productId) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        Optional<Product> productOptional = productRepository.findById(productId);

        if (categoryOptional.isEmpty() || productOptional.isEmpty()) {
            throw new NotFoundException("Categoria ou ingrediente não foi encontrado");
        }
        Category category = categoryOptional.get();
        Product product = productOptional.get();
        category.addProduct(product);

        Category categories = categoryRepository.save(category);
        return CategoryMapper.toResponseProducts(categories);
    }

    public void removeProduct(Long CategoryId, Long productId) {
        Optional<Category> categoryOptional = categoryRepository.findById(CategoryId);
        Optional<Product> productOptional = productRepository.findById(productId);
        if (categoryOptional.isEmpty()) {
            throw new BadRequestException("A lista de produtos favoritos está vazia");
        }
        Category category = categoryOptional.get();
        Product product = productOptional.get();
        category.removeProduct(product);
        categoryRepository.save(category);
    }

    public CategoryProductDTO getByCategory(String name) {
        if (name != null) {
            return CategoryMapper.toProductsDTO(categoryRepository.findCategoryByName(name));
        } else {
            throw new NotFoundException("O nome desta categoria não existe");
        }
    }
}


