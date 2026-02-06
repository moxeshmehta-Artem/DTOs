package com.example.Architecture.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Architecture.dto.ProductRequestDTO;
import com.example.Architecture.dto.ProductResponseDTO;
import com.example.Architecture.entity.Product;
import com.example.Architecture.repository.ProductRepository;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepo;
    public ProductResponseDTO createProduct(ProductRequestDTO request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        Product savedProduct = productRepo.save(product);
        return new ProductResponseDTO(savedProduct.getId(), savedProduct.getName(), savedProduct.getPrice(), savedProduct.getStock());
        
    }
    public List<ProductResponseDTO> getAllProducts()
    {
        return productRepo.findAllProducts().stream()
        .map(product -> new ProductResponseDTO(product.getId(), product.getName(), product.getPrice(), product.getStock()))
        .collect(java.util.stream.Collectors.toList());
    }
}
