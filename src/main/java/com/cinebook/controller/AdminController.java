/**
 * @author Abhaydev and Mathew
 * 
 * Description:
 * 
 * AdminController responsible for function performed only by ADMIN
 * Include the approval onboarding requests and manage movie usecases
 */

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
import com.cinebook.dto.MovieRequest;
import com.cinebook.dto.OnboardingRequestResponse;
import com.cinebook.dto.StatusUpdateRequest;
import com.cinebook.model.Certification;
import com.cinebook.model.Format;
import com.cinebook.model.Genre;
import com.cinebook.model.Language;
import com.cinebook.model.Movie;
import com.cinebook.model.OnboardingRequest;
import com.cinebook.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	AdminService adminService;

	// API to View all onboarding requests with status filter
	@GetMapping("/onboarding-requests")
	public ResponseEntity<ApiResponse<List<OnboardingRequestResponse>>> getOnboardingRequests(
			@RequestParam(required = false, defaultValue = "all") String statusName) {

		List<OnboardingRequestResponse> response = adminService.getOnboardingRequests(statusName);

		ApiResponse<List<OnboardingRequestResponse>> apiResponse = new ApiResponse<>("success", response,
				"Onboarding requests retrieved successfully");

		return ResponseEntity.ok(apiResponse);
	}

	// API to View one onboarding request in detail for review
	@GetMapping("/onboarding-request/{id}")
	public ResponseEntity<ApiResponse<OnboardingRequest>> getOnboardingRequestById(@PathVariable Long id) {

		OnboardingRequest request = adminService.getOnboardingRequestById(id);

		if (request == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse<>("failure", null, "Onboarding request not found"));
		}

		ApiResponse<OnboardingRequest> apiResponse = new ApiResponse<>("success", request,
				"Onboarding request details retrieved successfully");

		return ResponseEntity.ok(apiResponse);

	}

	// API to Approve or Reject a onboarding request with its id
	@PostMapping("/onboarding-request/approval")
	public ResponseEntity<ApiResponse<OnboardingRequest>> updateOnboardingRequestStatus(
			@RequestBody StatusUpdateRequest statusUpdateRequest) {

		OnboardingRequest updatedRequest = adminService.updateOnboardingRequestStatus(statusUpdateRequest);

		if (updatedRequest == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse<>("failure", null, "Onboarding request not found"));
		}

		ApiResponse<OnboardingRequest> apiResponse = new ApiResponse<>("success", null,
				"Onboarding request " + statusUpdateRequest.getAction().toLowerCase() + " successfully");

		return ResponseEntity.ok(apiResponse);

	}

	// API to get all formats in the system - to populate format dropdown
	@GetMapping("/formats")
	public ResponseEntity<ApiResponse<List<Format>>> getFormats() {

		List<Format> formats = adminService.getAllFormats();
		ApiResponse<List<Format>> response = new ApiResponse<>("success", formats, "Formats retrieved successfully");
		return ResponseEntity.ok(response);

	}

	// API to get all languages in the system - to populate language and subtitle
	// dropdown
	@GetMapping("/languages")
	public ResponseEntity<ApiResponse<List<Language>>> getLanguages() {

		List<Language> languages = adminService.getAllLanguages();
		ApiResponse<List<Language>> response = new ApiResponse<>("success", languages,
				"Languages retrieved successfully");
		return ResponseEntity.ok(response);

	}

	// API to get all genres in the system - to populate genre dropdown
	@GetMapping("/genres")
	public ResponseEntity<ApiResponse<List<Genre>>> getGenres() {

		List<Genre> genres = adminService.getAllGenres();
		ApiResponse<List<Genre>> response = new ApiResponse<>("success", genres, "Genres retrieved successfully");

		return ResponseEntity.ok(response);

	}

	// API to get all certifications in the system - to populate certification
	// dropdown
	@GetMapping("/certifications")
	public ResponseEntity<ApiResponse<List<Certification>>> getCertifications() {

		List<Certification> certifications = adminService.getAllCertifications();
		ApiResponse<List<Certification>> response = new ApiResponse<>("success", certifications,
				"Certifications retrieved successfully");

		return ResponseEntity.ok(response);

	}

	// API to upload a new movie to the global movie catalog
	@PostMapping("/add-movie")
	public ResponseEntity<ApiResponse<Movie>> addMovie(@RequestBody MovieRequest dto) {

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String adminEmail = userDetails.getUsername();

		Movie savedMovie = adminService.addMovie(dto, adminEmail);

		ApiResponse<Movie> response = new ApiResponse<>("success", savedMovie, "Movie added successfully");

		return ResponseEntity.ok(response);

	}

}
