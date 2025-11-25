package com.cinebook.controller;

import java.time.LocalDate;
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
import com.cinebook.dto.FormatDto;
import com.cinebook.dto.LanguageDto;
import com.cinebook.dto.MovieResponse;
import com.cinebook.dto.ScreenResponse;
import com.cinebook.dto.ShowTimeCheckRequest;
import com.cinebook.dto.ShowTimeCheckResponse;
import com.cinebook.dto.ShowTimeScheduleRequest;
import com.cinebook.dto.TheatreOnboardingRequest;
import com.cinebook.dto.TheatreResponse;
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
	
	@GetMapping("/movies")
	public ResponseEntity<ApiResponse<List<MovieResponse>>> getAllMovies(
	        @RequestParam(required = false) String titleFilter) {

	    try {
	        UserDetails userDetails = (UserDetails) SecurityContextHolder
	                .getContext()
	                .getAuthentication()
	                .getPrincipal();

	        boolean isTheatreOwner = userDetails.getAuthorities().stream()
	                .anyMatch(auth -> "THEATRE_OWNER".equalsIgnoreCase(auth.getAuthority()));

	        if (!isTheatreOwner) {
	            ApiResponse<List<MovieResponse>> error =
	                    new ApiResponse<>("failure", null, "Access denied: Theatre Owners only");
	            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
	        }

	        List<MovieResponse> movies = theatreService.getAllMovies(titleFilter);

	        ApiResponse<List<MovieResponse>> apiResponse =
	                new ApiResponse<>("success", movies, "Movies retrieved successfully");

	        return ResponseEntity.ok(apiResponse);

	    } catch (Exception e) {
	        ApiResponse<List<MovieResponse>> apiResponse =
	                new ApiResponse<>("failure", null, e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
	    }
	}

	
	@GetMapping("/movies/{movieId}/availablelanguages")
	public ResponseEntity<ApiResponse<List<LanguageDto>>> getLanguagesForMovie(
	        @PathVariable Long movieId) {

	    try {
	        UserDetails userDetails = (UserDetails) SecurityContextHolder
	                .getContext()
	                .getAuthentication()
	                .getPrincipal();

	        boolean isTheatreOwner = userDetails.getAuthorities().stream()
	                .anyMatch(auth -> "THEATRE_OWNER".equalsIgnoreCase(auth.getAuthority()));

	        if (!isTheatreOwner) {
	            ApiResponse<List<LanguageDto>> error =
	                    new ApiResponse<>("failure", null, "Access denied: Theatre Owners only");
	            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
	        }

	        List<LanguageDto> langs = theatreService.getLanguagesForMovie(movieId);

	        ApiResponse<List<LanguageDto>> apiResponse =
	                new ApiResponse<>("success", langs, "Languages retrieved successfully");

	        return ResponseEntity.ok(apiResponse);

	    } catch (Exception e) {
	        ApiResponse<List<LanguageDto>> apiResponse =
	                new ApiResponse<>("failure", null, e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
	    }
	}
	
	
	
	@GetMapping("/movies/{movieId}/availableformats")
	public ResponseEntity<ApiResponse<List<FormatDto>>> getFormatsForMovie(
	        @PathVariable Long movieId) {

	    try {
	        UserDetails userDetails = (UserDetails) SecurityContextHolder
	                .getContext()
	                .getAuthentication()
	                .getPrincipal();

	        boolean isTheatreOwner = userDetails.getAuthorities().stream()
	                .anyMatch(auth -> "THEATRE_OWNER".equalsIgnoreCase(auth.getAuthority()));

	        if (!isTheatreOwner) {
	            ApiResponse<List<FormatDto>> error =
	                    new ApiResponse<>("failure", null, "Access denied: Theatre Owners only");
	            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
	        }

	        List<FormatDto> formats = theatreService.getFromatsForMovie(movieId);

	        ApiResponse<List<FormatDto>> apiResponse = new ApiResponse<>("success", formats, "Formats retrieved successfully");

	        return ResponseEntity.ok(apiResponse);

	    } catch (Exception e) {
	        ApiResponse<List<FormatDto>> apiResponse =
	                new ApiResponse<>("failure", null, e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
	    }
	}
	
	@GetMapping("/movies/{movieId}/availablesubtitles")
	public ResponseEntity<ApiResponse<List<LanguageDto>>> getSubtitlesForMovie(
	        @PathVariable Long movieId) {

	    try {
	        UserDetails userDetails = (UserDetails) SecurityContextHolder
	                .getContext()
	                .getAuthentication()
	                .getPrincipal();

	        boolean isTheatreOwner = userDetails.getAuthorities().stream()
	                .anyMatch(auth -> "THEATRE_OWNER".equalsIgnoreCase(auth.getAuthority()));

	        if (!isTheatreOwner) {
	            ApiResponse<List<LanguageDto>> error =
	                    new ApiResponse<>("failure", null, "Access denied: Theatre Owners only");
	            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
	        }

	        List<LanguageDto> langs = theatreService.getSubtitlesForMovie(movieId);

	        ApiResponse<List<LanguageDto>> apiResponse =
	                new ApiResponse<>("success", langs, "Subtitles retrieved successfully");

	        return ResponseEntity.ok(apiResponse);

	    } catch (Exception e) {
	        ApiResponse<List<LanguageDto>> apiResponse =
	                new ApiResponse<>("failure", null, e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
	    }
	}
	
	@GetMapping("/owned")
    public ResponseEntity<ApiResponse<List<TheatreResponse>>> getOwnedTheatres() {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();

            boolean isOwner = userDetails.getAuthorities().stream()
                    .anyMatch(auth -> "THEATRE_OWNER".equalsIgnoreCase(auth.getAuthority()));

            if (!isOwner) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse<>("failure", null, "Access denied: Theatre owners only"));
            }

            String ownerEmail = userDetails.getUsername();
            List<TheatreResponse> response = theatreService.getOwnedTheatres(ownerEmail);

            ApiResponse<List<TheatreResponse>> apiResponse =
                    new ApiResponse<>("success", response, "Owned theatres retrieved successfully");

            return ResponseEntity.ok(apiResponse);

        } catch (Exception e) {
            ApiResponse<List<TheatreResponse>> apiResponse =
                    new ApiResponse<>("failure", null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
	}
	
	
	@GetMapping("/{theatreId}/screens")
	public ResponseEntity<ApiResponse<List<ScreenResponse>>> getScreensByTheatre(
	        @PathVariable Long theatreId) {

	    try {
	        UserDetails userDetails = (UserDetails) SecurityContextHolder
	                .getContext()
	                .getAuthentication()
	                .getPrincipal();

	        boolean isOwner = userDetails.getAuthorities().stream()
	                .anyMatch(auth -> "THEATRE_OWNER".equalsIgnoreCase(auth.getAuthority()));

	        if (!isOwner) {
	            return ResponseEntity.status(HttpStatus.FORBIDDEN)
	                    .body(new ApiResponse<>("failure", null, "Access denied: Theatre owners only"));
	        }

	        String ownerEmail = userDetails.getUsername();

	        List<ScreenResponse> response = theatreService.getScreensByTheatreId(theatreId, ownerEmail);

	        ApiResponse<List<ScreenResponse>> apiResponse =
	                new ApiResponse<>("success", response, "Screens retrieved successfully");

	        return ResponseEntity.ok(apiResponse);

	    } catch (Exception e) {
	        ApiResponse<List<ScreenResponse>> apiResponse =
	                new ApiResponse<>("failure", null, e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
	    }
	}
	
	
	@PostMapping("/showtime/check")
	public ResponseEntity<ApiResponse<ShowTimeCheckResponse>> checkShowTime(
	        @RequestBody ShowTimeCheckRequest request) {

	    try {
	        UserDetails userDetails = (UserDetails) SecurityContextHolder
	                .getContext()
	                .getAuthentication()
	                .getPrincipal();

	        boolean isTheatreOwner = userDetails.getAuthorities().stream()
	                .anyMatch(auth -> "THEATRE_OWNER".equalsIgnoreCase(auth.getAuthority()));

	        if (!isTheatreOwner) {
	            ApiResponse<ShowTimeCheckResponse> error =
	                    new ApiResponse<>("failure", null, "Access denied: Theatre Owners only");
	            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
	        }

	        ShowTimeCheckResponse result = theatreService.checkShowTime(
	                request.getMovieId(),
	                request.getScreenId(),
	                request.getStartDate(),
	                request.getEndDate(),
	                request.getStartTime()
	        );

	        ApiResponse<ShowTimeCheckResponse> apiResponse =
	                new ApiResponse<>("success", result, result.getMessage());

	        return ResponseEntity.ok(apiResponse);

	    } catch (Exception e) {
	        ApiResponse<ShowTimeCheckResponse> apiResponse =
	                new ApiResponse<>("failure", null, e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
	    }
	}
	
	
	@PostMapping("/showtime/schedule")
	public ResponseEntity<ApiResponse<List<LocalDate>>> scheduleShowTime(@RequestBody ShowTimeScheduleRequest request) {

	    try {
	        UserDetails userDetails = (UserDetails) SecurityContextHolder
	                .getContext()
	                .getAuthentication()
	                .getPrincipal();

	        boolean isTheatreOwner = userDetails.getAuthorities().stream()
	                .anyMatch(auth -> "THEATRE_OWNER".equalsIgnoreCase(auth.getAuthority()));

	        if (!isTheatreOwner) {
	            return ResponseEntity.status(HttpStatus.FORBIDDEN)
	                    .body(new ApiResponse<>("failure", null, "Access denied: Theatre Owners only"));
	        }

	        List<LocalDate> scheduledDates = theatreService.scheduleShowTime(request);
	        
	        if (scheduledDates == null || scheduledDates.isEmpty()) {
	            throw new RuntimeException("Cannot Schedule showtimes - overlap in showtime schedule"); 
	        }

	        return ResponseEntity.ok(new ApiResponse<>("success", scheduledDates, "Showtimes scheduled successfuly"));

	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(new ApiResponse<>("failure", null, e.getMessage()));
	    }
	}

	

	
	
}
