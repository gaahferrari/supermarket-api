package com.supermaket.GMarket.controller;

import com.supermaket.GMarket.DTO.AddressDTO;
import com.supermaket.GMarket.DTO.OrderDTO;
import com.supermaket.GMarket.DTO.OrderProductDTO;
import com.supermaket.GMarket.entity.Address;
import com.supermaket.GMarket.request.AddressRequest;
import com.supermaket.GMarket.request.OrderRequest;
import com.supermaket.GMarket.responses.BaseBodyError;
import com.supermaket.GMarket.responses.BaseBodyResponse;
import com.supermaket.GMarket.service.AddressService;
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
@RequestMapping("/address")
@Validated
public class AddressController {

    private final AddressService addressService;

    @Operation(summary = "Get All addresses", description = "Get All addresses", tags = {"address"}, responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = BaseBodyResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = BaseBodyError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = BaseBodyError.class)))})
    @GetMapping
    public ResponseEntity<BaseBodyResponse<List<AddressDTO>>> getAllAddress(){
        return ResponseEntity.status(200).body(addressService.getAll());
    }

    @Operation(summary = "Create address", description = "Create address", tags = {"address"}, responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = BaseBodyResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = BaseBodyError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = BaseBodyError.class)))})
    @PostMapping
    public ResponseEntity<BaseBodyResponse<Address>> createAddress(@RequestBody @Valid AddressRequest request){
        return ResponseEntity.status(201).body(addressService.create(request));
    }


    @Operation(summary = "Delete addresses", description = "Delete addresses", tags = {"address"}, responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = BaseBodyResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = BaseBodyError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = BaseBodyError.class)))})
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAddress(@PathVariable Long id) {
        addressService.delete(id);
        return ResponseEntity.status(200).body("O endereço com o ID: " + id + " foi excluído com sucesso!");
    }

    @Operation(summary = "Update addresses", description = "Update addresses", tags = {"address"}, responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = BaseBodyResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = BaseBodyError.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = BaseBodyError.class)))})
    @PatchMapping("/{id}")
    public ResponseEntity<BaseBodyResponse<AddressDTO>> updateAddress(@RequestBody AddressRequest request, @PathVariable Long id) {
        return ResponseEntity.status(200).body(addressService.update( id, request));
    }
}
