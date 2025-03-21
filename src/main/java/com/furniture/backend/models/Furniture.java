package com.furniture.backend.models;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// Makes sure its mapped to a database table
@Entity
// Specifying the database table name
@Table(name = "furniture")
public class Furniture {
    // Generating a primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length=100, nullable= false)
    private String name;

    @Column(precision = 10, scale= 2, nullable = false)
    private BigDecimal price;

    @Column(length=255, nullable=true)
    private String description;

    @Column(nullable=false)
    private String category;

    @Column(name = "user_id", nullable = false, columnDefinition = "uuid")
    private UUID userId;

    
    // Default constructor
    public Furniture() {
    }
    
    // Getters
    public Long getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public UUID getUserId() {
        return userId;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
}
