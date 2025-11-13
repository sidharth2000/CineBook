package com.cinebook.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cinebook.dto.ApiResponse;
import com.cinebook.dto.LoginRequest;
import com.cinebook.dto.RegisterRequest;
import com.cinebook.dto.UpgradeToTheatreOwnerRequest;
import com.cinebook.model.User;
import com.cinebook.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<ApiResponse<?>> registerUser(@RequestBody RegisterRequest request) {
	    try {
	        userService.registerUser(request);
	        return ResponseEntity.ok(
	                new ApiResponse<>("success", null, "User registered successfully")
	        );
	    } catch (RuntimeException e) {
	        return ResponseEntity.badRequest().body(
	                new ApiResponse<>("failure", null, e.getMessage())
	        );
	    }
	}
	
	
	
	@PostMapping("/login")
	public ResponseEntity<ApiResponse<?>> loginUser(@RequestBody LoginRequest request) {
	    try {
	        String token = userService.loginUser(request);

	        // Prepare payload as a JSON-like map
	        Map<String, String> payload = new HashMap<>();
	        payload.put("token", token);

	        return ResponseEntity.ok(
	                new ApiResponse<>("success", payload, "Login successful")
	        );
	    } catch (RuntimeException e) {
	        return ResponseEntity.badRequest().body(
	                new ApiResponse<>("failure", null, e.getMessage())
	        );
	    }
	}
	
	@PostMapping("/upgrade-to-theatre-owner")
	public ResponseEntity<ApiResponse<Void>> upgradeToTheatreOwner(
	        @RequestBody UpgradeToTheatreOwnerRequest request
	) {
	    try {
	        // Get currently authenticated user
	        UserDetails userDetails = (UserDetails) SecurityContextHolder
	                                        .getContext()
	                                        .getAuthentication()
	                                        .getPrincipal();
	        String email = userDetails.getUsername();
//	        System.out.print(email);
	        ApiResponse<Void> response = userService.upgradeToTheatreOwner(email, request);
	        return ResponseEntity.ok(response);
	    } catch (RuntimeException e) {
	        return ResponseEntity.ok(new ApiResponse<>("failure", null, e.getMessage()));
	    }
	}

}
