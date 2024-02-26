package com.supermaket.GMarket.controller;

import com.supermaket.GMarket.DTO.UserDTO;
import com.supermaket.GMarket.DTO.UserOrderDTO;
import com.supermaket.GMarket.DTO.UserProductsDTO;
import com.supermaket.GMarket.entity.User;
import com.supermaket.GMarket.request.UserRequest;
import com.supermaket.GMarket.responses.BaseBodyError;
import com.supermaket.GMarket.responses.BaseBodyResponse;
import com.supermaket.GMarket.service.UserService;
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
@RequestMapping("/user")
@Validated
public class UserController {

    private final UserService userService;

    @Operation(summary = "Get All Users", description = "Get All Users", tags = {"Users"}, responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = BaseBodyResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = BaseBodyError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = BaseBodyError.class)))})
    @GetMapping
    public ResponseEntity<BaseBodyResponse<List<UserDTO>>> getAllCustomers() {
        return ResponseEntity.status(200).body(userService.getAll());
    }

    @Operation(summary = "Get User by userName and Password", description = "Get User by userName and Password", tags = {"Users"}, responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = BaseBodyResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = BaseBodyError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = BaseBodyError.class)))})
    @GetMapping("/{userName}/{password}")
    public UserDTO getUserLogin(@PathVariable String userName, @PathVariable String password){
        return userService.getByUserNameAndPassword(userName, password);
    }

    @Operation(summary = "Get orders by user ID", description = "Get orders by user ID", tags = {"Users"}, responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = BaseBodyResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = BaseBodyError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = BaseBodyError.class)))})
    @GetMapping("orders/{id}")
    public ResponseEntity<BaseBodyResponse<UserOrderDTO>> getOrderById(@PathVariable Long id) {
        return ResponseEntity.status(200).body(userService.getByOrder(id));
    }

    @Operation(summary = "Get user by  ID", description = "Get user by ID", tags = {"Users"}, responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = BaseBodyResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = BaseBodyError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = BaseBodyError.class)))})
    @GetMapping("/{id}")
    public ResponseEntity<BaseBodyResponse<User>> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.status(200).body(userService.getById(id));
    }
    @Operation(summary = "Delete Users", description = "Delete Users", tags = {"Users"}, responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = BaseBodyResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = BaseBodyError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = BaseBodyError.class)))})
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.status(200).body("O cliente com o ID: " + id + " foi excluído com sucesso!");
    }

    @Operation(summary = "Create Users", description = "Create Users's information", tags = {"Users"}, responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = BaseBodyResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = BaseBodyError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = BaseBodyError.class)))})
    @PostMapping
    public ResponseEntity<BaseBodyResponse<User>> createCustomer(@RequestBody @Valid UserRequest request) {
        return ResponseEntity.status(201).body(userService.create(request));
    }


    @Operation(summary = "Update favorite product list", description = "Update favorite product list", tags = {"Users"}, responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = BaseBodyResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = BaseBodyError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = BaseBodyError.class)))})
    @PutMapping("/{userId}/favorites/{productId}")
    public ResponseEntity<BaseBodyResponse<UserProductsDTO>> addFavoriteProduct(@PathVariable Long userId, @PathVariable Long productId) {
        return ResponseEntity.status(201).body(userService.addFavoriteProduct(userId, productId));
    }
    @Operation(summary = "Remove product from user's list", description = "Remove product from user's list", tags = {"Users"}, responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = BaseBodyResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = BaseBodyError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = BaseBodyError.class)))})
    @DeleteMapping("/{userId}/favorites/{productId}")
    public ResponseEntity<String> removeFavoriteProduct(@PathVariable Long userId, @PathVariable Long productId) {
        userService.removeFavoriteProduct(userId, productId);
        return ResponseEntity.status(201).body("O produto com o ID: " + productId + " foi removido dos favoritos com sucesso!");
    }
    @Operation(summary = "Get Users favorite list By ID", description = "Get Users By ID", tags = {"Users"}, responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = BaseBodyResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = BaseBodyError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = BaseBodyError.class)))})
    @GetMapping("favorites/{id}")
    public ResponseEntity<BaseBodyResponse<UserProductsDTO>> getProductsListById(@PathVariable Long id) {
        return ResponseEntity.status(200).body(userService.getByProducts(id));
    }




    }



