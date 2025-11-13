package com.cinebook.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class TheatreOwner extends User {

    private String businessName;
    private String businessId;
    private String taxId;

    @OneToMany(mappedBy = "theatreOwner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Theatre> theatresOwned;

    @OneToMany(mappedBy = "theatreOwner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OnboardingRequest> onboardingRequests;

    private LocalDateTime registeredDate;
    private LocalDateTime modifiedDate;

    public TheatreOwner() {
        this.registeredDate = LocalDateTime.now();
        this.modifiedDate = LocalDateTime.now();
    }

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getTaxId() {
		return taxId;
	}

	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}

	public List<Theatre> getTheatresOwned() {
		return theatresOwned;
	}

	public void setTheatresOwned(List<Theatre> theatresOwned) {
		this.theatresOwned = theatresOwned;
	}

	public List<OnboardingRequest> getOnboardingRequests() {
		return onboardingRequests;
	}

	public void setOnboardingRequests(List<OnboardingRequest> onboardingRequests) {
		this.onboardingRequests = onboardingRequests;
	}

	public LocalDateTime getRegisteredDate() {
		return registeredDate;
	}

	public void setRegisteredDate(LocalDateTime registeredDate) {
		this.registeredDate = registeredDate;
	}

	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

    
}
