package com.supermaket.GMarket.controller;

import com.supermaket.GMarket.DTO.WalletDTO;
import com.supermaket.GMarket.request.WalletRequest;
import com.supermaket.GMarket.responses.BaseBodyError;
import com.supermaket.GMarket.responses.BaseBodyResponse;
import com.supermaket.GMarket.service.WalletService;
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
@RequestMapping("/wallet")
@Validated
public class WalletController {

    private final WalletService walletService;

    @Operation(summary = "Get All wallets", description = "Get All wallets", tags = {"wallet"}, responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = BaseBodyResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = BaseBodyError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = BaseBodyError.class)))})
    @GetMapping
    public ResponseEntity<BaseBodyResponse<List<WalletDTO>>> getAllWallets(){
        return ResponseEntity.status(200).body(walletService.getAll());
    }

    @Operation(summary = "Create wallet", description = "Create wallet", tags = {"wallet"}, responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = BaseBodyResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = BaseBodyError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = BaseBodyError.class)))})
    @PostMapping
    public ResponseEntity<BaseBodyResponse<WalletDTO>> createWallet(@RequestBody @Valid WalletRequest request){
        return ResponseEntity.status(201).body(walletService.create(request));
    }

    @Operation(summary = "Get wallet by ID", description = "Get wallet by ID", tags = {"wallet"}, responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = BaseBodyResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = BaseBodyError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = BaseBodyError.class)))})
    @GetMapping("/{id}")
    public BaseBodyResponse<WalletDTO> getProductById(@PathVariable Long id) {
        return walletService.getById(id);
    }
}
