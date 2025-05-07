package com.example.demo.repository;

//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.UserDetails;

@Repository
public interface UserRepository extends MongoRepository<UserDetails, String> {
    public boolean existsByEmail(String email);
    public boolean existsById(String id);
    public UserDetails findUserByEmail(String email);
}
