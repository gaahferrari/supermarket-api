package com.supermaket.GMarket.service;

import com.supermaket.GMarket.DTO.AddressDTO;
import com.supermaket.GMarket.DTO.ProductDTO;
import com.supermaket.GMarket.entity.Address;
import com.supermaket.GMarket.entity.Product;
import com.supermaket.GMarket.exceptions.BadRequestException;
import com.supermaket.GMarket.exceptions.NotFoundException;
import com.supermaket.GMarket.mapper.AddressMapper;
import com.supermaket.GMarket.mapper.ProductMapper;
import com.supermaket.GMarket.repository.ProductRepository;
import com.supermaket.GMarket.request.AddressRequest;
import com.supermaket.GMarket.request.ProductRequest;
import com.supermaket.GMarket.responses.BaseBodyResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;


    public BaseBodyResponse<List<ProductDTO>> getAll(){
        List<Product> productList = productRepository.findAll();
        if(productList.isEmpty()){
            throw new BadRequestException("A lista de produtos está vazia");
        }
        return ProductMapper.toListResponse(productList);
    }

    public BaseBodyResponse<ProductDTO> create(@Valid ProductRequest request) {
        Product product = productRepository.save(ProductMapper.toProduct(request));
        if(product != null){
            return ProductMapper.toResponse(product);
        } else {
            throw new BadRequestException("Erro ao criar um novo produto");
        }

    }

    @Transactional
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product != null) {
            productRepository.delete(product);
        } else {
            throw new BadRequestException("Erro ao deletar o produto. Ele pode estar vinculado a outra entidade");
        }

    }

    public BaseBodyResponse<ProductDTO> getById(Long productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product != null) {
            return ProductMapper.toResponse(product);
        } else {
            throw new NotFoundException("Produto não encontrado com o ID: " + productId);
        }
    }

    @Transactional
    public BaseBodyResponse<ProductDTO> update(Long productId, @Valid ProductRequest request) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isEmpty()) {
            throw new BadRequestException("Produto com o ID " + productId + " não encontrado");
        }

        Product existingProduct = productOptional.get();
        existingProduct.setName(request.getName());
        existingProduct.setImage(request.getImage());
        existingProduct.setDescription(request.getDescription());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInStock(request.getInStock());
        existingProduct.setUpdatedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));

        Product updatedProduct = productRepository.save(existingProduct);
        return ProductMapper.toResponse(updatedProduct);
    }

}
