package com.example.Architecture.controller;

import com.example.Architecture.dto.ProductRequestDTO;
import com.example.Architecture.dto.ProductResponseDTO;
import com.example.Architecture.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductRequestDTO request) {
        ProductResponseDTO response = productService.createProduct(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public List<ProductResponseDTO> getAllProducts() {
        return productService.getAllProducts();
    }

}
