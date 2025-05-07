package com.example.demo;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.entity.Image;
import com.example.demo.service.ImageService;

@Controller
@CrossOrigin(origins = "http://localhost:3000")
public class ImageController {
    @Autowired
    private ImageService imageService;
    
    @GetMapping("/images/{imageId}")
    public ResponseEntity<Image> getImage(@PathVariable String imageId) {
        Optional<Image> image = imageService.getImage(imageId);
        if (image.isPresent()) {
            return ResponseEntity.ok(image.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
