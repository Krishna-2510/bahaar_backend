package com.example.demo.service;

import com.example.demo.entity.Garden;
import com.example.demo.entity.Image;
import com.example.demo.entity.Plant;
import com.example.demo.repository.GardenRepository;
import com.example.demo.repository.ImageRepository;
import com.example.demo.repository.PlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PlantServiceImpl implements PlantService {
    @Autowired
    private PlantRepository plantRepo;

    @Autowired
    private GardenRepository gardenRepo;

    @Autowired
    private ImageRepository imageRepo;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Plant addPlant(String name, MultipartFile image, String gardenId, String water, String fertilizer, String sunlight, String note) {
        String imageUrl = saveImage(image, name);
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = currentDate.format(formatter);
        Plant plant  = new Plant(gardenId, name, water, sunlight, fertilizer, note, imageUrl, formattedDate);
        plantRepo.save(plant);
        List<Plant> existingPlants = plantRepo.findByNameAndGardenId(name, gardenId, Sort.by(Sort.Direction.DESC, "createdAt"));
//        System.out.println("The list of plants you want is = " + existingPlants);
        if(existingPlants.size() == 1) {
            System.out.println("List is empty");
            Garden garden = gardenRepo.findById(gardenId).orElseThrow();
            garden.setNumberOfPlants(garden.getNumberOfPlants() + 1);
            gardenRepo.save(garden);
        }
        return plant;
    }

    private String saveImage(MultipartFile image, String name) {
        try {
            byte[] bytes = image.getBytes();
            String imageId = UUID.randomUUID().toString();
            String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/images/")
                    .path(imageId)
                    .toUriString();
            Image imageObject = new Image(imageId, bytes);
            imageObject.setName(name+"_Image");
            imageRepo.save(imageObject);
            return imageUrl;
        } catch (IOException e) {
            throw new RuntimeException("Could not save image", e);
        }
    }

    @Override
    public List<Plant> findPlantsByGardenId(String gardenId) {
        List<Plant> plants = plantRepo.findByGardenId(gardenId);
        return plants;
    }

    @Override
    public String deletePlant(String plantId, String gardenId) {
        Optional<Plant> plant = plantRepo.findById(plantId);
//        System.out.println("The Plant you are lookinh for is this = " + plant);
        String name = plant.get().getName();
        List<Plant> existingPlants = plantRepo.findByNameAndGardenId(name, gardenId, Sort.by(Sort.Direction.DESC, "createdAt"));
        if(existingPlants.size() == 1) {
            Garden garden = gardenRepo.findById(gardenId).orElseThrow();
            garden.setNumberOfPlants(garden.getNumberOfPlants() - 1);
            gardenRepo.save(garden);
        }
        plantRepo.deleteById(plantId);
        return plantId;
    }

    @Override
    public List<Plant> findMostRecentPlantByGardenId(String gardenId) {
        Aggregation aggregation = Aggregation.newAggregation(
                // Step 1: Match by gardenId
                Aggregation.match(Criteria.where("gardenId").is(gardenId)),

                // Step 2: Sort by createdAt descending to get the latest first
                Aggregation.sort(Sort.by(Sort.Direction.DESC, "createdAt")),

                // Step 3: Group by name
                Aggregation.group("name")
                        .first("$$ROOT").as("latestPlant") // Get the latest plant
                        .last("$$ROOT").as("oldestPlant"), // Get the oldest plant

                // Step 5: Project to include the latest plant's details and the imageUrl from the oldest plant
                Aggregation.project()
                        .and("oldestPlant.id").as("id") // Include the id of the latest plant
                        .and("oldestPlant.gardenId").as("gardenId") // Include the gardenId of the latest plant
                        .and("oldestPlant.name").as("name") // Include the name
                        .and("oldestPlant.addedOn").as("addedOn") // Include addedOn of the latest plant
                        .and("oldestPlant.water").as("water") // Include water of the latest plant
                        .and("oldestPlant.sunlight").as("sunlight") // Include sunlight of the latest plant
                        .and("oldestPlant.fertilizer").as("fertilizer") // Include fertilizer of the latest plant
                        .and("oldestPlant.note").as("note") // Include note of the latest plant
                        .and("latestPlant.imageUrl").as("imageUrl") // Include imageUrl from the oldest plant
                        .and("oldestPlant.createdAt").as("createdAt"),// Include the createdAt of the latest plant

                // Step 6: Sort again if needed
                Aggregation.sort(Sort.by(Sort.Direction.DESC, "createdAt"))
        );

        AggregationResults<Plant> results = mongoTemplate.aggregate(aggregation, Plant.class, Plant.class);
        return results.getMappedResults();
    }

    @Override
    public List<Plant> findByNameAndGardenId(String name, String gardenId) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        return plantRepo.findByNameAndGardenId(name, gardenId, sort);
    }

    @Override
    public List<String> deleteAllPlantByName(String name, String gardenId) {
        List<Plant> allPlants = plantRepo.findByNameAndGardenId(name, gardenId, Sort.by(Sort.Direction.DESC, "createdAt"));
        List<String> result = new ArrayList<String>();
        for (Plant allPlant : allPlants) {
            String plantId = allPlant.getId();
            plantRepo.deleteById(plantId);
            result.add(plantId);
        }
        Garden garden = gardenRepo.findById(gardenId).orElseThrow();
        garden.setNumberOfPlants(garden.getNumberOfPlants() - 1);
        gardenRepo.save(garden);
        return result;
    }
}
