package com.cinebook.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinebook.dto.MovieRequest;
import com.cinebook.dto.OnboardingRequestResponse;
import com.cinebook.dto.StatusUpdateRequest;
import com.cinebook.model.Address;
import com.cinebook.model.Admin;
import com.cinebook.model.Format;
import com.cinebook.model.Language;
import com.cinebook.model.Movie;
import com.cinebook.model.OnboardingRequest;
import com.cinebook.model.RequestStatus;
import com.cinebook.model.Theatre;
import com.cinebook.model.TheatreOwner;
import com.cinebook.repository.FormatRepository;
import com.cinebook.repository.LanguagesRepository;
import com.cinebook.repository.MovieRepository;
import com.cinebook.repository.OnboardingRequestRepository;
import com.cinebook.repository.RequestStatusRepository;
import com.cinebook.repository.TheatreRepository;
import com.cinebook.repository.UserRepository;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	OnboardingRequestRepository onboardingRequestRepository;
	
	@Autowired
	TheatreRepository theatreRepository;
	
	@Autowired
	RequestStatusRepository requestStatusRepository;
	
	@Autowired
	FormatRepository formatRepository;
	
	@Autowired
	LanguagesRepository languageRepository;
	
	@Autowired
	MovieRepository movieRepository;
	
	@Autowired
	UserRepository userRepository;
	
	
	@Override
	public List<OnboardingRequestResponse> getOnboardingRequests(String statusName) {
		
	    List<OnboardingRequest> requests;

	    if (statusName.equals("all")) {
	        requests = onboardingRequestRepository.findAll();
	    } else {
	        requests = onboardingRequestRepository.findByStatus_StatusName(statusName);
	    }

	    List<OnboardingRequestResponse> responseList = new ArrayList<>();
	    for (OnboardingRequest request : requests) {
	        OnboardingRequestResponse dto = new OnboardingRequestResponse();
	        dto.setRequestId(request.getRequestId());

	        // Theatre details
	        Theatre theatre = request.getTheatre();
	        dto.setTheatreName(theatre.getTheatreName());
	        dto.setOverview(theatre.getOverview());
	        dto.setContactNumber(theatre.getContactNumber());

	        // Address details
	        Address address = theatre.getAddress();
	        dto.setAddressLine1(address.getAddressLine1());
	        dto.setAddressLine2(address.getAddressLine2());
	        dto.setCounty(address.getCounty());
	        dto.setEirCode(address.getEirCode());

	        // Theatre owner
	        TheatreOwner owner = request.getTheatreOwner();
	        dto.setOwnerName(owner.getFirstName() + " " + owner.getLastName());
	        dto.setOwnerEmail(owner.getEmail());

	        // Status
	        dto.setStatus(request.getStatus().getStatusName());

	        responseList.add(dto);
	    }
	    return responseList;
	}
	
	@Override
	public OnboardingRequest getOnboardingRequestById(Long id) {
	    return onboardingRequestRepository.findById(id).orElse(null);
	}
	
	@Override
	public OnboardingRequest updateOnboardingRequestStatus(StatusUpdateRequest statusUpdateRequest) {
	    OnboardingRequest request = onboardingRequestRepository.findById(statusUpdateRequest.getId()).orElse(null);

	    if (request == null) {
	        return null;
	    }
	    
	    if (!"PENDING".equalsIgnoreCase(request.getStatus().getStatusName())) {
	        throw new IllegalStateException("Only requests with PENDING status can be updated");
	    }

	    Theatre theatre = request.getTheatre();
	    String action = statusUpdateRequest.getAction();

	    if (action == null) {
	        throw new IllegalArgumentException("Action must be provided (approve/reject)");
	    }
	    
	    RequestStatus approvedStatus = requestStatusRepository.findByStatusName("APPROVED");
	    RequestStatus rejectedStatus = requestStatusRepository.findByStatusName("REJECTED");

	    if (action.equalsIgnoreCase("approve")) {
	        request.setStatus(approvedStatus);
	        if (theatre != null) {
	            theatre.setActive(true);
	            theatreRepository.save(theatre);
	        }
	    } else if (action.equalsIgnoreCase("reject")) {
	        request.setStatus(rejectedStatus);
	        if (theatre != null) {
	            theatre.setActive(false);
	            theatreRepository.save(theatre);
	        }
	    } else {
	        throw new IllegalArgumentException("Invalid action: must be 'approve' or 'reject'");
	    }

	    if (statusUpdateRequest.getComment() != null) {
	        request.setAdminRemarks(statusUpdateRequest.getComment());
	    }

	    onboardingRequestRepository.save(request);

	    return request;
	}
	
	@Override
    public List<Format> getAllFormats() {
        return formatRepository.findAll();
    }
	
	@Override
    public List<Language> getAllLanguages() {
        return languageRepository.findAll();
    }
	
	
	@Override
    public Movie addMovie(MovieRequest dto, String adminEmail) {
        if (movieRepository.existsByMovieTitleIgnoreCase(dto.getMovieTitle())) {
            throw new RuntimeException("Movie with this title already exists!");
        }
        Admin admin = (Admin)userRepository.findByEmail(adminEmail)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        Movie movie = new Movie();
        movie.setMovieTitle(dto.getMovieTitle());
        movie.setSynopsis(dto.getSynopsis());
        movie.setRunTimeMinutes(dto.getRunTimeMinutes());
        movie.setReleaseDate(dto.getReleaseDate());
        movie.setPosterUrl(dto.getPosterUrl());

        List<Format> formats = formatRepository.findAllById(dto.getFormatIds());
        movie.setAvailableFormats(formats);


        List<Language> languages = languageRepository.findAllById(dto.getLanguageIds());
        movie.setAvailableLanguages(languages);


        List<Language> subtitleLanguages = languageRepository.findAllById(dto.getSubtitleLanguageIds());
        movie.setSubtitleLanguages(subtitleLanguages);


        movie.setCreatedBy(admin);

        return movieRepository.save(movie);
    }
}
