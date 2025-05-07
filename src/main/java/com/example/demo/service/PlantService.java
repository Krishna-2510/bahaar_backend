package com.example.demo.service;

import com.example.demo.entity.Plant;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PlantService {
    public Plant addPlant(String name, MultipartFile image, String gardenId, String water, String fertilizer, String sunlight, String note);
    public List<Plant> findPlantsByGardenId(String gardenId);
    public String deletePlant(String plantId, String gardenId);
    public List<Plant> findMostRecentPlantByGardenId(String gardenId);
    public List<Plant> findByNameAndGardenId(String name, String gardenId);
    public List<String> deleteAllPlantByName(String name, String gardenId);
}
