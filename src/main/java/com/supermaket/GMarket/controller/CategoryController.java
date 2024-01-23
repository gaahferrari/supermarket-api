package com.supermaket.GMarket.controller;

import com.supermaket.GMarket.DTO.AddressDTO;
import com.supermaket.GMarket.DTO.CategoryProductDTO;
import com.supermaket.GMarket.entity.Category;
import com.supermaket.GMarket.repository.CategoryRepository;
import com.supermaket.GMarket.request.AddressRequest;
import com.supermaket.GMarket.request.CategoryRequest;
import com.supermaket.GMarket.responses.BaseBodyError;
import com.supermaket.GMarket.responses.BaseBodyResponse;
import com.supermaket.GMarket.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/categories")
@Validated
public class CategoryController {
    private final CategoryService categoryService;

    @Operation(summary = "Get All categories", description = "Get All categories", tags = {"category"}, responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = BaseBodyResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = BaseBodyError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = BaseBodyError.class)))})
    @GetMapping
    public ResponseEntity<BaseBodyResponse<List<CategoryProductDTO>>> getAllCategories(){
        return ResponseEntity.status(200).body(categoryService.getAll());
    }
    @Operation(summary = "Get category by ID", description = "Get category by ID", tags = {"category"}, responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = BaseBodyResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = BaseBodyError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = BaseBodyError.class)))})
    @GetMapping("/{id}")
    public ResponseEntity<BaseBodyResponse<Category>> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.status(200).body(categoryService.getById(id));
    }

    @Operation(summary = "Create category", description = "Create category", tags = {"category"}, responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = BaseBodyResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = BaseBodyError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = BaseBodyError.class)))})
    @PostMapping
    public ResponseEntity<BaseBodyResponse<Category>> createCategory(@RequestBody @Valid CategoryRequest request){
        return ResponseEntity.status(201).body(categoryService.create(request));
    }

    @Operation(summary = "Delete category", description = "Delete category", tags = {"category"}, responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = BaseBodyResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = BaseBodyError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = BaseBodyError.class)))})
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        categoryService.deleteCategoryAndProducts(id);
        return ResponseEntity.status(200).body("A categoria com o ID: " + id + " foi excluído com sucesso!");
    }

    @Operation(summary = "Update category", description = "Update category", tags = {"category"}, responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = BaseBodyResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = BaseBodyError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = BaseBodyError.class)))})

    @PutMapping("/{categoryId}/products/{productId}")
    public ResponseEntity<BaseBodyResponse<CategoryProductDTO>> addProductToCategory(@PathVariable Long categoryId, @PathVariable Long productId) {
        return ResponseEntity.status(201).body(categoryService.addProductsToCategory(categoryId, productId));
    }
}
