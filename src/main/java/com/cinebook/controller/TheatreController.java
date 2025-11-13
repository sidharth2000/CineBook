package com.cinebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cinebook.dto.ApiResponse;
import com.cinebook.dto.TheatreOnboardingRequest;
import com.cinebook.service.TheatreService;

@RestController
@RequestMapping("/theatre")
public class TheatreController {
	
	@Autowired
	private TheatreService theatreService;
	
	@PostMapping("/onboard")
    public ResponseEntity<ApiResponse<Void>> onboardTheatre(
            @RequestBody TheatreOnboardingRequest request) {

        try {
            // Get currently authenticated user
            UserDetails userDetails = (UserDetails) SecurityContextHolder
                                            .getContext()
                                            .getAuthentication()
                                            .getPrincipal();
            String email = userDetails.getUsername();

            ApiResponse<Void> response = theatreService.onboardTheatre(email, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.ok(new ApiResponse<>("failure", null, e.getMessage()));
        }
    }

}
