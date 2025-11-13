package com.cinebook.service;

import com.cinebook.dto.ApiResponse;
import com.cinebook.dto.TheatreOnboardingRequest;

public interface TheatreService {
	ApiResponse<Void> onboardTheatre(String userEmail, TheatreOnboardingRequest request);
}
