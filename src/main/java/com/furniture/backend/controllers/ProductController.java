package com.furniture.backend.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.furniture.backend.models.Furniture;
import com.furniture.backend.repositories.ProductRepository;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;
    
    @GetMapping("/test-connection")
    public ResponseEntity<Map<String, Object>> testConnection() {
        Map<String, Object> response = new HashMap<>();
        try {
            // Try to count products to test the connection
            long count = productRepository.count();
            response.put("success", true);
            response.put("message", "Successfully connected to Supabase!");
            response.put("product_count", count);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to connect to Supabase");
            response.put("error", e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
    
    @PostMapping("/create-sample")
    public ResponseEntity<Map<String, Object>> createSampleProduct() {
        Map<String, Object> response = new HashMap<>();
        try {
            Furniture product = new Furniture();
            product.setName("Chair");
            
            Furniture savedProduct = productRepository.save(product);
            
            response.put("success", true);
            response.put("message", "Sample product created!");
            response.put("product", savedProduct);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to create sample product");
            response.put("error", e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
} 