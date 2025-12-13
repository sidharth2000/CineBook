package com.cinebook.controller;

import java.time.LocalDate;
import java.util.List;

import com.cinebook.dto.ResponseFactory;
import com.cinebook.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.cinebook.service.TheatreService;

@RestController
@RequestMapping("/theatre")
public class TheatreController {

    @Autowired
    private ResponseFactory api;

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

        theatreService.onboardTheatre(email, request);
        return api.success(null, "Theatre onboarding request created successfully");
    }

    @GetMapping("/movies")
    public ResponseEntity<ApiResponse<List<MovieResponse>>> getAllMovies(
            @RequestParam(required = false) String titleFilter) {

        List<MovieResponse> movies = theatreService.getAllMovies(titleFilter);

        return api.success(movies, "Movies retrieved successfully");
    }


    @GetMapping("/movies/{movieId}/availablelanguages")
    public ResponseEntity<ApiResponse<List<LanguageDto>>> getLanguagesForMovie(
            @PathVariable Long movieId) {

        List<LanguageDto> langs = theatreService.getLanguagesForMovie(movieId);

        return api.success(langs, "Languages retrieved successfully");
    }


    @GetMapping("/movies/{movieId}/availableformats")
    public ResponseEntity<ApiResponse<List<FormatDto>>> getFormatsForMovie(
            @PathVariable Long movieId) {

        List<FormatDto> formats = theatreService.getFromatsForMovie(movieId);

        return api.success(formats, "Formats retrieved successfully");
    }

    @GetMapping("/movies/{movieId}/availablesubtitles")
    public ResponseEntity<ApiResponse<List<LanguageDto>>> getSubtitlesForMovie(
            @PathVariable Long movieId) {

        List<LanguageDto> langs = theatreService.getSubtitlesForMovie(movieId);

        return api.success(langs, "Subtitles retrieved successfully");
    }

    @GetMapping("/owned")
    public ResponseEntity<ApiResponse<List<TheatreResponse>>> getOwnedTheatres() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        String ownerEmail = userDetails.getUsername();
        List<TheatreResponse> response = theatreService.getOwnedTheatres(ownerEmail);

        return api.success(response, "Owned theatres retrieved successfully");
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

        return api.success(response, "Screens retrieved successfully");
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

        return api.success(result, result.getMessage());
    }


    @PostMapping("/showtime/schedule")
    public ResponseEntity<ApiResponse<List<LocalDate>>> scheduleShowTime(@RequestBody ShowTimeScheduleRequest request) {

        List<LocalDate> scheduledDates = theatreService.scheduleShowTime(request);

        if (scheduledDates == null || scheduledDates.isEmpty()) {
            throw new RuntimeException("Cannot Schedule showtimes - overlap in showtime schedule");
        }

        return api.success(scheduledDates, "Showtimes scheduled successfuly");
    }

}