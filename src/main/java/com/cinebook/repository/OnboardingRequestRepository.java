package com.cinebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cinebook.model.OnboardingRequest;

@Repository
public interface OnboardingRequestRepository extends JpaRepository<OnboardingRequest, Long> {
    
}
