package com.cinebook.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.cinebook.dto.ApiResponse;
import com.cinebook.dto.FormatDto;
import com.cinebook.dto.LanguageDto;
import com.cinebook.dto.MovieResponse;
import com.cinebook.dto.ScreenResponse;
import com.cinebook.dto.ShowTimeCheckResponse;
import com.cinebook.dto.ShowTimeScheduleRequest;
import com.cinebook.dto.TheatreOnboardingRequest;
import com.cinebook.dto.TheatreResponse;

public interface TheatreService {

	ApiResponse<Void> onboardTheatre(String userEmail, TheatreOnboardingRequest request);

	List<MovieResponse> getAllMovies(String titleFilter);

	List<LanguageDto> getLanguagesForMovie(Long movieId);

	List<FormatDto> getFromatsForMovie(Long movieId);

	List<LanguageDto> getSubtitlesForMovie(Long movieId);

	List<TheatreResponse> getOwnedTheatres(String ownerEmail);

	List<ScreenResponse> getScreensByTheatreId(Long theatreId, String ownerEmail);

	ShowTimeCheckResponse checkShowTime(Long movieId, Long screenId, LocalDate startDate, LocalDate endDate,
			LocalTime selectedStartTime);

	List<LocalDate> scheduleShowTime(ShowTimeScheduleRequest request);

}
