package com.example.demo.service;

import java.util.Optional;

import com.example.demo.entity.Image;

public interface ImageService {
    public Optional<Image> getImage(String imageId);
}
