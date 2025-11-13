package com.cinebook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinebook.builder.TheatreBuilder;
import com.cinebook.dto.ApiResponse;
import com.cinebook.dto.TheatreOnboardingRequest;
import com.cinebook.model.OnboardingRequest;
import com.cinebook.model.RequestStatus;
import com.cinebook.model.Theatre;
import com.cinebook.model.TheatreOwner;
import com.cinebook.repository.OnboardingRequestRepository;
import com.cinebook.repository.RequestStatusRepository;
import com.cinebook.repository.TheatreOwnerRepository;
import com.cinebook.repository.TheatreRepository;

import jakarta.transaction.Transactional;

@Service
public class TheatreServiceImpl implements TheatreService {
	
	@Autowired
	private TheatreOwnerRepository theatreOwnerRepository;
	
	@Autowired
	private TheatreRepository theatreRepository;
	
	@Autowired
	private OnboardingRequestRepository onboardingRequestRepository;
	
	@Autowired
	private RequestStatusRepository requestStatusRepository;
	
	@Override
    @Transactional
    public ApiResponse<Void> onboardTheatre(String userEmail, TheatreOnboardingRequest request) {

		TheatreOwner owner = theatreOwnerRepository.findByEmail(userEmail)
		        .orElseThrow(() -> new RuntimeException("User is not a theatre owner"));
		
        
        Theatre theatre = TheatreBuilder.builder()
                .theatreName(request.getTheatreName())
                .overview(request.getOverview())
                .contactNumber(request.getContactNumber())
                .address(request.getAddress())    
                .theatreOwner(owner)
                .screens(request.getScreens())   
                .build();

        
        theatreRepository.save(theatre);

       
        OnboardingRequest onboardingRequest = new OnboardingRequest();
        onboardingRequest.setTheatre(theatre);
        onboardingRequest.setTheatreOwner(owner);

        
        RequestStatus pendingStatus = requestStatusRepository.findByStatusName("PENDING");
              
        onboardingRequest.setStatus(pendingStatus);

        onboardingRequestRepository.save(onboardingRequest);

        return new ApiResponse<>("success", null, "Theatre onboarding request created successfully");
    }


}
