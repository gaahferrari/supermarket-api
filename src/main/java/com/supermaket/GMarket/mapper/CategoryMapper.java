package com.supermaket.GMarket.mapper;

import com.supermaket.GMarket.DTO.*;
import com.supermaket.GMarket.entity.*;
import com.supermaket.GMarket.request.AddressRequest;
import com.supermaket.GMarket.responses.BaseBodyResponse;

import java.util.List;

public class CategoryMapper {

    public static Category toCategory(Category request){
        return Category.builder()
                .name(request.getName())
                .build();
    }

    public static CategoryDTO toDTO(Category category){
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public static CategoryProductDTO toProductsDTO(Category category, List<Product> Products){
        List<ProductDTO> productDTO = Products.stream().map(ProductMapper::toDTO).toList();

        return CategoryProductDTO.builder()
                .name(category.getName())
                .productsIds(productDTO)
                .id(category.getId())
                .build();
    }

    public static BaseBodyResponse<Category> toResponse(Category category){
        return BaseBodyResponse.<Category>builder()
                .company("G-Market")
                .description("Uma nova categoria " + category.getName() + " foi criado com sucesso!" )
                .result(category).build();
    }

    public static BaseBodyResponse<List<CategoryDTO>> toListResponse(List<Category> categories){
        List<CategoryDTO> categoryDTOS = categories.stream().map(CategoryMapper::toDTO).toList();
        return BaseBodyResponse.<List<CategoryDTO>>builder()
                .company("G-Market")
                .description("Lista de endereços do usuários")
                .result(categoryDTOS)
                .build();
    }

    public static BaseBodyResponse<CategoryProductDTO> toResponseProductID(Category category, List<Product> products){
        return BaseBodyResponse.<CategoryProductDTO>builder()
                .company("G-Market")
                .description("Produtos da categoria: " + category.getProducts())
                .result(toProductsDTO(category, products)).build();
    }

}
