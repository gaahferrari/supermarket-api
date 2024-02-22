package com.supermaket.GMarket.controller;

import com.supermaket.GMarket.DTO.OrderDTO;
import com.supermaket.GMarket.DTO.OrderProductDTO;
import com.supermaket.GMarket.request.OrderRequest;
import com.supermaket.GMarket.responses.BaseBodyError;
import com.supermaket.GMarket.responses.BaseBodyResponse;
import com.supermaket.GMarket.service.OrderService;
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
@RequestMapping("/orders")
@Validated
public class OrderController {
    private final OrderService orderService;

    @Operation(summary = "Get All Orders", description = "Get All Orders", tags = {"Orders"}, responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = BaseBodyResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = BaseBodyError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = BaseBodyError.class)))})
    @GetMapping
    public ResponseEntity<BaseBodyResponse<List<OrderProductDTO>>> getAllOrders(){
        return ResponseEntity.status(200).body(orderService.getAll());
    }

    @Operation(summary = "Create Orders", description = "Create Orders", tags = {"Orders"}, responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = BaseBodyResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = BaseBodyError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = BaseBodyError.class)))})
    @PostMapping
    public ResponseEntity<BaseBodyResponse<OrderDTO>> createOrder(@RequestBody @Valid OrderRequest request){
        return ResponseEntity.status(201).body(orderService.create(request));
    }

    @Operation(summary = "Delete Orders", description = "Delete Orders", tags = {"Orders"}, responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = BaseBodyResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = BaseBodyError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = BaseBodyError.class)))})
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.status(200).body("O pedido com o ID: " + id + " foi excluído com sucesso!");
    }

    @Operation(summary = "Add product from order", description = "Add products to order", tags = {"Orders"}, responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = BaseBodyResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = BaseBodyError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = BaseBodyError.class)))})
    @PutMapping("/{orderId}/product/{productId}")
    public ResponseEntity<BaseBodyResponse<OrderProductDTO>> addProductToOrder(@PathVariable Long orderId, @PathVariable Long productId) {
        return ResponseEntity.status(200).body(orderService.addProductToOrder(orderId,productId));
    }

    @Operation(summary = "Delete product from order", description = "Delete product from order", tags = {"Orders"}, responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = BaseBodyResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = BaseBodyError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = BaseBodyError.class)))})
    @DeleteMapping("/{orderId}/product/{productId}")
    public ResponseEntity<String> removeProduct(@PathVariable Long orderId, @PathVariable Long productId) {
        orderService.removeProduct(orderId, productId);
        return ResponseEntity.status(201).body("O produto com o ID: " + productId + " foi removido do pedido " + orderId + " com sucesso!");
    }

    @Operation(summary = "Get order by ID", description = "Get order by ID", tags = {"Orders"}, responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = BaseBodyResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = BaseBodyError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = BaseBodyError.class)))})
    @GetMapping("/{id}")
    public BaseBodyResponse<OrderDTO> getProductById(@PathVariable Long id) {
        return orderService.getById(id);
    }
}
