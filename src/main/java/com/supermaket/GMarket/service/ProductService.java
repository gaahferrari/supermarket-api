package com.supermaket.GMarket.service;

import com.supermaket.GMarket.DTO.ProductDTO;
import com.supermaket.GMarket.entity.Product;
import com.supermaket.GMarket.exceptions.BadRequestException;
import com.supermaket.GMarket.exceptions.NotFoundException;
import com.supermaket.GMarket.mapper.ProductMapper;
import com.supermaket.GMarket.repository.ProductRepository;
import com.supermaket.GMarket.request.ProductRequest;
import com.supermaket.GMarket.responses.BaseBodyResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

}
