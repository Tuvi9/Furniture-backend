package com.furniture.backend.controllers;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.furniture.backend.models.Furniture;
import com.furniture.backend.repositories.ProductRepository;

// Handles HTTP requests
@RestController
// Base URL for every endpoint
@RequestMapping("api/products")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Connects to supabase
    @Autowired
    private ProductRepository productRepository;

    // Extracting userId from JWT
    private UUID getUserIdFromToken(String authHeader) {
        try {
            String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
            
            // Split the JWT into its parts
            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                throw new RuntimeException("Invalid JWT token format");
            }

            // Decode the payload (second part)
            String payload = new String(Base64.getDecoder().decode(parts[1]));
            logger.info("Decoded payload: {}", payload); // Log the decoded payload
            
            // Parse the JSON payload
            Map<String, Object> payloadMap = objectMapper.readValue(payload, Map.class);
            
            // Get the sub claim which contains the user ID
            String userId = (String) payloadMap.get("sub");
            if (userId == null) {
                throw new RuntimeException("User ID not found in token");
            }

            return UUID.fromString(userId);
        } catch (Exception e) {
            logger.error("Error extracting user ID from token", e);
            throw new RuntimeException("Invalid authentication token");
        }
    }

    @PostMapping("create-furniture")

    // ResponseEntity<Map<String, Object>>: The return type of this method
    // ResponseEntity: A Spring class that wraps your response data with HTTP status codes and headers
    // <Map<String, Object>>: The type of data returned inside the ResponseEntity
    // Map<String, Object>: A key-value structure (like JSON) where keys are strings and values can be any type
    // Map<String, Object> RequestData: The parameter that receives the converted JSON data
    // Map<String, Object> response: Declares a variable named "response" that will hold your response data

    public ResponseEntity<Map<String, Object>> createFurniture(
            @RequestBody Map<String, Object> RequestData,
            @RequestHeader("Authorization") String authHeader) {
        Map<String, Object> response = new HashMap<>();

        try {
            // Get the user ID from the token
            UUID userId = getUserIdFromToken(authHeader);
            logger.info("Extracted user ID: {}", userId);
            
            Furniture furniture = new Furniture();
            furniture.setName((String) RequestData.get("title"));
            furniture.setCategory((String) RequestData.get("category"));
            furniture.setDescription((String) RequestData.get("description"));

            // Convert price to BigDecimal
            furniture.setPrice(new java.math.BigDecimal(RequestData.get("price").toString()));

            furniture.setUserId(userId);
            
            // Set image URL if provided
            if (RequestData.containsKey("imageUrl")) {
                furniture.setImageUrl((String) RequestData.get("imageUrl"));
            }
            
            // Save to database
            Furniture savedFurniture = productRepository.save(furniture);
            
            // Return success response
            response.put("success", true);
            response.put("message", "Furniture created successfully");
            response.put("data", savedFurniture);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error creating furniture", e);
            response.put("success", false);
            response.put("message", "Error creating furniture: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}
