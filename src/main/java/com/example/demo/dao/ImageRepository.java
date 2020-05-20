package com.example.demo.dao;



import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.ImageModel;

public interface ImageRepository extends JpaRepository<ImageModel, Long> {
}