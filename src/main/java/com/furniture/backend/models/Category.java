package com.furniture.backend.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

// Makes sure its mapped to a database table
@Entity
// Specifying the database table name
@Table(name = "categories")
public class Category {
    // Generating a primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "category")
    private List<Furniture> furniture = new ArrayList<>();

    @Column(nullable=false)
    private String categoryName;

    // Default constructor
    public Category() {
    }

    // Getters
    public Long getId() {
        return id;
    }

    public List<Furniture> getFurniture() {
        return furniture;
    }

    public String getCategoryName() {
        return categoryName;
    }

    // Setters
    public void setFurniture(List<Furniture> furniture) {
        this.furniture = furniture;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
