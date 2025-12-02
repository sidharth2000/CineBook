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
        // Get currently authenticated user
        UserDetails userDetails = (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        String email = userDetails.getUsername();

        ApiResponse<Void> response = theatreService.onboardTheatre(email, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/movies")
    public ResponseEntity<ApiResponse<List<MovieResponse>>> getAllMovies(
            @RequestParam(required = false) String titleFilter) {

        List<MovieResponse> movies = theatreService.getAllMovies(titleFilter);

        ApiResponse<List<MovieResponse>> apiResponse =
                new ApiResponse<>("success", movies, "Movies retrieved successfully");

        return ResponseEntity.ok(apiResponse);
    }


    @GetMapping("/movies/{movieId}/availablelanguages")
    public ResponseEntity<ApiResponse<List<LanguageDto>>> getLanguagesForMovie(
            @PathVariable Long movieId) {

        List<LanguageDto> langs = theatreService.getLanguagesForMovie(movieId);

        ApiResponse<List<LanguageDto>> apiResponse =
                new ApiResponse<>("success", langs, "Languages retrieved successfully");

        return ResponseEntity.ok(apiResponse);
    }


    @GetMapping("/movies/{movieId}/availableformats")
    public ResponseEntity<ApiResponse<List<FormatDto>>> getFormatsForMovie(
            @PathVariable Long movieId) {

        List<FormatDto> formats = theatreService.getFromatsForMovie(movieId);

        ApiResponse<List<FormatDto>> apiResponse = new ApiResponse<>("success", formats, "Formats retrieved successfully");

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/movies/{movieId}/availablesubtitles")
    public ResponseEntity<ApiResponse<List<LanguageDto>>> getSubtitlesForMovie(
            @PathVariable Long movieId) {

        List<LanguageDto> langs = theatreService.getSubtitlesForMovie(movieId);

        ApiResponse<List<LanguageDto>> apiResponse =
                new ApiResponse<>("success", langs, "Subtitles retrieved successfully");

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/owned")
    public ResponseEntity<ApiResponse<List<TheatreResponse>>> getOwnedTheatres() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        String ownerEmail = userDetails.getUsername();
        List<TheatreResponse> response = theatreService.getOwnedTheatres(ownerEmail);

        ApiResponse<List<TheatreResponse>> apiResponse =
                new ApiResponse<>("success", response, "Owned theatres retrieved successfully");

        return ResponseEntity.ok(apiResponse);
    }


    @GetMapping("/{theatreId}/screens")
    public ResponseEntity<ApiResponse<List<ScreenResponse>>> getScreensByTheatre(
            @PathVariable Long theatreId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        String ownerEmail = userDetails.getUsername();

        List<ScreenResponse> response = theatreService.getScreensByTheatreId(theatreId, ownerEmail);

        ApiResponse<List<ScreenResponse>> apiResponse =
                new ApiResponse<>("success", response, "Screens retrieved successfully");

        return ResponseEntity.ok(apiResponse);
    }


    @PostMapping("/showtime/check")
    public ResponseEntity<ApiResponse<ShowTimeCheckResponse>> checkShowTime(
            @RequestBody ShowTimeCheckRequest request) {

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
    }


    @PostMapping("/showtime/schedule")
    public ResponseEntity<ApiResponse<List<LocalDate>>> scheduleShowTime(@RequestBody ShowTimeScheduleRequest request) {

        List<LocalDate> scheduledDates = theatreService.scheduleShowTime(request);

        if (scheduledDates == null || scheduledDates.isEmpty()) {
            throw new RuntimeException("Cannot Schedule showtimes - overlap in showtime schedule");
        }

        return ResponseEntity.ok(new ApiResponse<>("success", scheduledDates, "Showtimes scheduled successfuly"));
    }

}
