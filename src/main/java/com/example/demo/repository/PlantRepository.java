package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.demo.entity.Plant;

public interface PlantRepository extends MongoRepository<Plant, String> {
    public List<Plant> findByGardenId(String gardenId);
    public List<Plant> findByNameAndGardenId(String name, String gardenId, Sort sort);
}
