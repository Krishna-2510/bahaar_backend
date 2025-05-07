package com.example.demo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.demo.entity.Garden;

public interface GardenRepository extends MongoRepository<Garden, String> {
    public List<Garden> findByUserId(String userId);
}
