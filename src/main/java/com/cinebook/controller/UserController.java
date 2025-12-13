/**
 * @author Sidharthan Jayavelu
 * 
 * Description:
 * UserController facilitates register , login and the upgrade to tehatre owner usecase
 * 
 */

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

	// API to register a user
	@PostMapping("/register")
	public ResponseEntity<ApiResponse<?>> registerUser(@RequestBody RegisterRequest request) {

		userService.registerUser(request);
		return ResponseEntity.ok(new ApiResponse<>("success", null, "User registered successfully"));

	}

	// API to login and get bearer token
	@PostMapping("/login")
	public ResponseEntity<ApiResponse<?>> loginUser(@RequestBody LoginRequest request) {

		String token = userService.loginUser(request);
		Map<String, String> payload = new HashMap<>();
		payload.put("token", token);

		return ResponseEntity.ok(new ApiResponse<>("success", payload, "Login successful"));

	}

	// API to upgrade to theare owner from customer
	@PostMapping("/upgrade-to-theatre-owner")
	public ResponseEntity<ApiResponse<Void>> upgradeToTheatreOwner(@RequestBody UpgradeToTheatreOwnerRequest request) {

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String email = userDetails.getUsername();
		ApiResponse<Void> response = userService.upgradeToTheatreOwner(email, request);
		return ResponseEntity.ok(response);

	}

}
