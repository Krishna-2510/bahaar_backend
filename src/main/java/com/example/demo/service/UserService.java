package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Garden;
import com.example.demo.entity.Plant;
import com.example.demo.entity.UserDetails;

public interface UserService {
    public UserDetails createUser(UserDetails user);
    public boolean emailExists(String email);
    public UserDetails findUserByEmail(String email);
    public List<Garden> findGardensByUserId(String userId);
    public List<Plant> findPlantsByGardenId(String gardenId);
    public Optional<UserDetails> findById(String Id);

    public boolean validatePass(String hashedPass, String rawPass);
}
