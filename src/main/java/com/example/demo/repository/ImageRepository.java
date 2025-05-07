package com.example.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.entity.Image;

public interface ImageRepository extends MongoRepository<Image, String> {
}
