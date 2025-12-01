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

	@GetMapping("/onboarding-requests")
	public ResponseEntity<ApiResponse<List<OnboardingRequestResponse>>> getOnboardingRequests(
			@RequestParam(required = false, defaultValue = "all") String statusName) {
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();

			boolean isAdmin = userDetails.getAuthorities().stream()
					.anyMatch(auth -> "ADMIN".equalsIgnoreCase(auth.getAuthority()));

			if (!isAdmin) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(new ApiResponse<>("failure", null, "Access denied: Admins only"));
			}

			List<OnboardingRequestResponse> response = adminService.getOnboardingRequests(statusName);

			ApiResponse<List<OnboardingRequestResponse>> apiResponse = new ApiResponse<>("success", response,
					"Onboarding requests retrieved successfully");

			return ResponseEntity.ok(apiResponse);

		} catch (Exception e) {
			ApiResponse<List<OnboardingRequestResponse>> apiResponse = new ApiResponse<>("failure", null,
					e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
		}
	}

	@GetMapping("/onboarding-request/{id}")
	public ResponseEntity<ApiResponse<OnboardingRequest>> getOnboardingRequestById(@PathVariable Long id) {
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();

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

			ApiResponse<OnboardingRequest> apiResponse = new ApiResponse<>("success", request,
					"Onboarding request details retrieved successfully");

			return ResponseEntity.ok(apiResponse);

		} catch (Exception e) {
			ApiResponse<OnboardingRequest> apiResponse = new ApiResponse<>("failure", null, e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
		}
	}

	@PostMapping("/onboarding-request/approval")
	public ResponseEntity<ApiResponse<OnboardingRequest>> updateOnboardingRequestStatus(
			@RequestBody StatusUpdateRequest statusUpdateRequest) {
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();

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

			ApiResponse<OnboardingRequest> apiResponse = new ApiResponse<>("success", null,
					"Onboarding request " + statusUpdateRequest.getAction().toLowerCase() + " successfully");

			return ResponseEntity.ok(apiResponse);

		} catch (Exception e) {
			ApiResponse<OnboardingRequest> apiResponse = new ApiResponse<>("failure", null, e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
		}
	}

	@GetMapping("/formats")
	public ResponseEntity<ApiResponse<List<Format>>> getFormats() {
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			boolean isAdmin = userDetails.getAuthorities().stream()
					.anyMatch(auth -> "ADMIN".equalsIgnoreCase(auth.getAuthority()));
			if (!isAdmin) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(new ApiResponse<>("failure", null, "Access denied: Admins only"));
			}
			List<Format> formats = adminService.getAllFormats();
			ApiResponse<List<Format>> response = new ApiResponse<>("success", formats,
					"Formats retrieved successfully");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			ApiResponse<List<Format>> error = new ApiResponse<>("failure", null, e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
		}
	}

	@GetMapping("/languages")
	public ResponseEntity<ApiResponse<List<Language>>> getLanguages() {
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			boolean isAdmin = userDetails.getAuthorities().stream()
					.anyMatch(auth -> "ADMIN".equalsIgnoreCase(auth.getAuthority()));
			if (!isAdmin) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(new ApiResponse<>("failure", null, "Access denied: Admins only"));
			}
			List<Language> languages = adminService.getAllLanguages();
			ApiResponse<List<Language>> response = new ApiResponse<>("success", languages,
					"Languages retrieved successfully");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			ApiResponse<List<Language>> error = new ApiResponse<>("failure", null, e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
		}
	}
	
	
	@GetMapping("/genres")
	public ResponseEntity<ApiResponse<List<Genre>>> getGenres() {
	    try {
	        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
	                .getAuthentication().getPrincipal();

	        boolean isAdmin = userDetails.getAuthorities().stream()
	                .anyMatch(auth -> "ADMIN".equalsIgnoreCase(auth.getAuthority()));

	        if (!isAdmin) {
	            return ResponseEntity.status(HttpStatus.FORBIDDEN)
	                    .body(new ApiResponse<>("failure", null, "Access denied: Admins only"));
	        }

	        List<Genre> genres = adminService.getAllGenres();
	        ApiResponse<List<Genre>> response = new ApiResponse<>("success", genres,
	                "Genres retrieved successfully");

	        return ResponseEntity.ok(response);

	    } catch (Exception e) {
	        ApiResponse<List<Genre>> error = new ApiResponse<>("failure", null, e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	    }
	}

	
	@GetMapping("/certifications")
	public ResponseEntity<ApiResponse<List<Certification>>> getCertifications() {
	    try {
	        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
	                .getAuthentication().getPrincipal();

	        boolean isAdmin = userDetails.getAuthorities().stream()
	                .anyMatch(auth -> "ADMIN".equalsIgnoreCase(auth.getAuthority()));

	        if (!isAdmin) {
	            return ResponseEntity.status(HttpStatus.FORBIDDEN)
	                    .body(new ApiResponse<>("failure", null, "Access denied: Admins only"));
	        }

	        List<Certification> certifications = adminService.getAllCertifications();
	        ApiResponse<List<Certification>> response = new ApiResponse<>(
	                "success",
	                certifications,
	                "Certifications retrieved successfully"
	        );

	        return ResponseEntity.ok(response);

	    } catch (Exception e) {
	        ApiResponse<List<Certification>> error =
	                new ApiResponse<>("failure", null, e.getMessage());

	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	    }
	}


	@PostMapping("/add-movie")
	public ResponseEntity<ApiResponse<Movie>> addMovie(@RequestBody MovieRequest dto) {
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			boolean isAdmin = userDetails.getAuthorities().stream()
					.anyMatch(auth -> "ADMIN".equalsIgnoreCase(auth.getAuthority()));
			if (!isAdmin) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(new ApiResponse<>("failure", null, "Access denied: Admins only"));
			}
			String adminEmail = userDetails.getUsername();

			Movie savedMovie = adminService.addMovie(dto, adminEmail);

			ApiResponse<Movie> response = new ApiResponse<>("success", null, "Movie added successfully");

			return ResponseEntity.ok(response);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApiResponse<>("failure", null, e.getMessage()));
		}
	}

}
