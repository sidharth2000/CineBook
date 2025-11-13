package com.cinebook.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cinebook.dto.ApiResponse;
import com.cinebook.dto.OnboardingRequestResponse;
import com.cinebook.dto.StatusUpdateRequest;
import com.cinebook.model.OnboardingRequest;
import com.cinebook.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	AdminService adminService;
	
	@GetMapping("/onboarding-requests")
	public ResponseEntity<ApiResponse<List<OnboardingRequestResponse>>> getOnboardingRequests(@RequestParam(required = false, defaultValue = "all") String statusName) {
	    try {
	        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

	        boolean isAdmin = userDetails.getAuthorities().stream()
	                .anyMatch(auth -> "ADMIN".equalsIgnoreCase(auth.getAuthority()));

	        if (!isAdmin) {
	            return ResponseEntity.status(HttpStatus.FORBIDDEN)
	                    .body(new ApiResponse<>("failure", null, "Access denied: Admins only"));
	        }

	        List<OnboardingRequestResponse> response = adminService.getOnboardingRequests(statusName);

	        ApiResponse<List<OnboardingRequestResponse>> apiResponse =
	                new ApiResponse<>("success", response, "Onboarding requests retrieved successfully");

	        return ResponseEntity.ok(apiResponse);

	    } catch (Exception e) {
	        ApiResponse<List<OnboardingRequestResponse>> apiResponse =
	                new ApiResponse<>("failure", null, e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
	    }
	}
	
	
	@GetMapping("/onboarding-request/{id}")
	public ResponseEntity<ApiResponse<OnboardingRequest>> getOnboardingRequestById(@PathVariable Long id) {
	    try {
	        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

	        boolean isAdmin = userDetails.getAuthorities().stream()
	                .anyMatch(auth -> "ADMIN".equalsIgnoreCase(auth.getAuthority()));

	        if (!isAdmin) {
	            return ResponseEntity.status(HttpStatus.FORBIDDEN)
	                    .body(new ApiResponse<>("failure", null, "Access denied: Admins only"));
	        }

	        OnboardingRequest request = adminService.getOnboardingRequestById(id);

	        if (request == null) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                    .body(new ApiResponse<>("failure", null, "Onboarding request not found"));
	        }

	        ApiResponse<OnboardingRequest> apiResponse =
	                new ApiResponse<>("success", request, "Onboarding request details retrieved successfully");

	        return ResponseEntity.ok(apiResponse);

	    } catch (Exception e) {
	        ApiResponse<OnboardingRequest> apiResponse =
	                new ApiResponse<>("failure", null, e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
	    }
	}
	
	@PostMapping("/onboarding-request/approval")
	public ResponseEntity<ApiResponse<OnboardingRequest>> updateOnboardingRequestStatus(@RequestBody StatusUpdateRequest statusUpdateRequest) {
	    try {
	        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

	        boolean isAdmin = userDetails.getAuthorities().stream()
	                .anyMatch(auth -> "ADMIN".equalsIgnoreCase(auth.getAuthority()));

	        if (!isAdmin) {
	            return ResponseEntity.status(HttpStatus.FORBIDDEN)
	                    .body(new ApiResponse<>("failure", null, "Access denied: Admins only"));
	        }

	        OnboardingRequest updatedRequest = adminService.updateOnboardingRequestStatus(statusUpdateRequest);

	        if (updatedRequest == null) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                    .body(new ApiResponse<>("failure", null, "Onboarding request not found"));
	        }

	        ApiResponse<OnboardingRequest> apiResponse = new ApiResponse<>(
	                "success",
	                null,
	                "Onboarding request " + statusUpdateRequest.getAction().toLowerCase() + " successfully"
	        );

	        return ResponseEntity.ok(apiResponse);

	    } catch (Exception e) {
	        ApiResponse<OnboardingRequest> apiResponse =
	                new ApiResponse<>("failure", null, e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
	    }
	}



}
