package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.entity.Garden;
import com.example.demo.entity.Plant;
import com.example.demo.entity.UserDetails;
import com.example.demo.repository.GardenRepository;
import com.example.demo.repository.PlantRepository;
import com.example.demo.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private GardenRepository gardenRepo;
	@Autowired
	private PlantRepository plantRepo;

	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Override
	public UserDetails createUser(UserDetails user) {
		String hashedPass = passwordEncoder.encode(user.getPassword());
		user.setPassword(hashedPass);
		return userRepo.save(user);
	}

	@Override
	public boolean emailExists(String email) {
		return userRepo.existsByEmail(email);
	}

	@Override
	public UserDetails findUserByEmail(String email) {
		return userRepo.findUserByEmail(email);
	}

	@Override
	public List<Garden> findGardensByUserId(String userId) {
	    return gardenRepo.findByUserId(userId);
	}

	@Override
	public List<Plant> findPlantsByGardenId(String gardenId) {
		return plantRepo.findByGardenId(gardenId);
	}

	@Override
	public Optional<UserDetails> findById(String Id) {
		return userRepo.findById(Id);
	}

	@Override
	public boolean validatePass(String hashedPass, String rawPass) {
		return passwordEncoder.matches(rawPass, hashedPass);
	}
}
