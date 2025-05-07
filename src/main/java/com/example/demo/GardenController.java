package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Garden;
import com.example.demo.service.GardenService;
import com.example.demo.service.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:3000, https://your-netlify-site.netlify.app")
public class GardenController {

  @Autowired	
  private GardenService gardenService;
  
  @Autowired
  private UserService userService;

  public GardenController(GardenService gardenService) {
    this.gardenService = gardenService;
  }

  @PostMapping("/addGarden")
  public ResponseEntity<Garden> addGarden(@RequestParam("name") String name,
                                          @RequestParam("image") MultipartFile image,
                                          @RequestParam("userId") String userId) {
    Garden garden = gardenService.addGarden(name, image, userId);
    return new ResponseEntity<>(garden, HttpStatus.CREATED);
  }
  
  @GetMapping("/{userId}/gardens")
  public ResponseEntity<List<Garden>> getGardensByUserId(@PathVariable String userId) {
      List<Garden> userGardens = userService.findGardensByUserId(userId);
      return new ResponseEntity<>(userGardens, HttpStatus.OK); 
  }
  
  @DeleteMapping("/deleteGarden/{gardenId}")
  public ResponseEntity<String> deleteGarden(@PathVariable String gardenId){
	  //Optional<Garden> garden = gardenService.findGardenById(gardenId);
	  String response = gardenService.deleteGarden(gardenId);
	  return new ResponseEntity<>(response, HttpStatus.OK);
  }
  
  
}