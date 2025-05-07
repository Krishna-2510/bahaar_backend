package com.example.demo;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.UserDetails;
import com.example.demo.service.UserService;

@Controller
@ResponseBody
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
	
	@Autowired
	private UserService userService;
	
    @GetMapping("/test")
    public String Hello() {
    	return "Hello there";
    }
    
    @PostMapping("/signin")
    public ResponseEntity<Map<String, Object>> signin(@RequestBody UserDetails user) {
        UserDetails dbUser = userService.findUserByEmail(user.getEmail());
        if (dbUser != null) {
            String dbpass = dbUser.getPassword();
            String userpass = user.getPassword();
            if (userService.validatePass(dbpass.trim(), userpass.trim())) {
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("message", "Login successful");
                responseBody.put("user", dbUser);
                return ResponseEntity.ok(responseBody);
            } else {
                return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Invalid password"));
            }
        } else {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "No account found with this email"));
        }
    }
    
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody UserDetails user) {
        UserDetails existingUser = userService.findUserByEmail(user.getEmail());
        Map<String, Object> responseBody = new HashMap<>();
        if (existingUser != null) {
        	return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Email already exixts"));
        } else {
        	responseBody.put("message", "Registration successful");
        	responseBody.put("user", user);
        	userService.createUser(user);
            return ResponseEntity.ok(responseBody);
        }
    }
}
