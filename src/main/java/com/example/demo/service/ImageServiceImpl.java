package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Image;
import com.example.demo.repository.ImageRepository;
@Service
public class ImageServiceImpl implements ImageService{
	
	@Autowired
	private ImageRepository imageRepo;

	@Override
	public Optional<Image> getImage(String imageId) {
		return imageRepo.findById(imageId);
	}

}
