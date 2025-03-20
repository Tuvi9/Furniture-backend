package com.furniture.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.furniture.backend.models.Furniture;

@Repository
public interface ProductRepository extends JpaRepository<Furniture, Long> {
    List<Furniture> findByNameContaining(String nameFragment);
} 