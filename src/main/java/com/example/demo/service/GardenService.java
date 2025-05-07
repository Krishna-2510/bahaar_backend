package com.example.demo.service;

import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Garden;

public interface GardenService {
    public Garden addGarden(String name, MultipartFile image, String userId);
    public String deleteGarden(String gardenId);
    public Optional<Garden> findGardenById(String gardenId);
}
