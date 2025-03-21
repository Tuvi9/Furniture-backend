package com.furniture.backend.models;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

// Makes sure its mapped to a database table
@Entity
// Specifying the database table name
@Table(name = "favourites")
public class Favourite {
    // Generating a primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, columnDefinition = "uuid")
    private UUID userId;

    @ManyToOne
    @JoinColumn(name = "furniture_id", nullable=false)
    private Furniture furniture;

    // Default constructor
    public Favourite() {
    }

    // Getters
    public Long getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public Furniture getFurniture() {
        return furniture;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public void setFurniture(Furniture furniture) {
        this.furniture = furniture;
    }
}
