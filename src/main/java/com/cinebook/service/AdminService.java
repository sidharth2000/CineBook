package com.cinebook.service;

import java.util.List;

import com.cinebook.dto.OnboardingRequestResponse;
import com.cinebook.dto.StatusUpdateRequest;
import com.cinebook.model.OnboardingRequest;

public interface AdminService {
	List<OnboardingRequestResponse> getOnboardingRequests(String statusName);
	OnboardingRequest getOnboardingRequestById(Long id);
	OnboardingRequest updateOnboardingRequestStatus(StatusUpdateRequest statusUpdateRequest);
}
