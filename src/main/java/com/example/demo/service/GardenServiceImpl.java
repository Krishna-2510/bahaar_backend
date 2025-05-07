package com.example.demo.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.entity.Garden;
import com.example.demo.entity.Image;
import com.example.demo.repository.GardenRepository;
import com.example.demo.repository.ImageRepository;

@Service
public class GardenServiceImpl implements GardenService{
	
	@Autowired
	private GardenRepository gardenRepo;
	
	@Autowired
	private ImageRepository imageRepo;

	@Override
	public Garden addGarden(String name, MultipartFile image, String userId) {
		String imageUrl = saveImage(image, name);
		LocalDate currentDate = LocalDate.now();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = currentDate.format(formatter);
	    Garden garden = new Garden(name, imageUrl, userId, formattedDate, 0);
	    gardenRepo.save(garden); 
	    return garden;
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
	public String deleteGarden(String gardenId) {
		gardenRepo.deleteById(gardenId);
		return gardenId;
	}

	@Override
	public Optional<Garden> findGardenById(String gardenId) {
		Optional<Garden> garden = gardenRepo.findById(gardenId);
		return garden;
	}

}
