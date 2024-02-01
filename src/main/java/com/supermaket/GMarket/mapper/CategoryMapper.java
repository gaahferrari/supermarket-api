package com.supermaket.GMarket.mapper;

import com.supermaket.GMarket.DTO.*;
import com.supermaket.GMarket.entity.*;
import com.supermaket.GMarket.request.CategoryRequest;
import com.supermaket.GMarket.responses.BaseBodyResponse;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryMapper {

    public static Category toCategory(CategoryRequest request){
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

    public static CategoryProductDTO toProductsDTO(Category category){

        return CategoryProductDTO.builder()
                .name(category.getName())
                .id(category.getId())
                .productsIds(category.getProducts().stream().map(ProductMapper::toDTO).collect(Collectors.toSet()))
                .build();
    }


    public static BaseBodyResponse<Category> toResponse(Category category){
        return BaseBodyResponse.<Category>builder()
                .company("G-Market")
                .description("Uma nova categoria " + category.getName() + " foi criado com sucesso!" )
                .result(category).build();
    }



    public static BaseBodyResponse<CategoryProductDTO> toResponseProducts(Category category){
        return BaseBodyResponse.<CategoryProductDTO>builder()
                .company("Pizza Store")
                .description("Um novo produto foi adicionado a categoria: " + category.getName())
                .result(toProductsDTO(category)).build();
    }

    public static BaseBodyResponse<List<CategoryProductDTO>> toListResponse(List<Category> categories){
        List<CategoryProductDTO> categoryProductDTOS = categories.stream().map(CategoryMapper::toProductsDTO).toList();
        return BaseBodyResponse.<List<CategoryProductDTO>>builder()
                .company("Pizza Store")
                .description("Lista de Pizzas")
                .result(categoryProductDTOS)
                .build();
    }
}
