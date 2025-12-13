/**
 * @author Mathew and Abhaydev
 * 
 * 
 */

package com.cinebook.service;

import java.util.List;

import com.cinebook.dto.MovieRequest;
import com.cinebook.dto.OnboardingRequestResponse;
import com.cinebook.dto.StatusUpdateRequest;
import com.cinebook.model.Certification;
import com.cinebook.model.Format;
import com.cinebook.model.Genre;
import com.cinebook.model.Language;
import com.cinebook.model.Movie;
import com.cinebook.model.OnboardingRequest;

public interface AdminService {

	List<OnboardingRequestResponse> getOnboardingRequests(String statusName);

	OnboardingRequest getOnboardingRequestById(Long id);

	OnboardingRequest updateOnboardingRequestStatus(StatusUpdateRequest statusUpdateRequest);

	List<Format> getAllFormats();

	List<Language> getAllLanguages();

	List<Genre> getAllGenres();

	List<Certification> getAllCertifications();

	Movie addMovie(MovieRequest dto, String adminEmail);
}
