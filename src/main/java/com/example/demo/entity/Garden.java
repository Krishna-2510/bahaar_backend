package com.example.demo.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

import org.springframework.data.annotation.Id;

@Document(collection = "gardens")
public class Garden {
	@Id
    private String id;
	private String userId;
	private String name;
	private String createdOn;
	private String imageUrl;
	private int numberOfPlants;
	
	public String getId() {
		return id;
	}
	public Garden(String name, String imageUrl, String userId, String createdOn, int numberOfPlants) {
		super();
		this.name = name;
		this.imageUrl = imageUrl;
		this.userId = userId;
		this.createdOn = createdOn;
		this.numberOfPlants = numberOfPlants;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public int getNumberOfPlants() {
		return numberOfPlants;
	}

	public void setNumberOfPlants(int numberOfPlants) {
		this.numberOfPlants = numberOfPlants;
	}
}
