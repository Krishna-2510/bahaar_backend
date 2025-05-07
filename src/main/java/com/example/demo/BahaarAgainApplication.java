package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class BahaarAgainApplication {

	public static void main(String[] args) {
		SpringApplication.run(BahaarAgainApplication.class, args);
	}

}
