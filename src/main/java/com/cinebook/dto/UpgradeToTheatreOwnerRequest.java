package com.cinebook.dto;

public class UpgradeToTheatreOwnerRequest {

    private String businessName;
    private String businessId;
    private String taxId;

    public UpgradeToTheatreOwnerRequest() {}

    public String getBusinessName() { return businessName; }
    public void setBusinessName(String businessName) { this.businessName = businessName; }

    public String getBusinessId() { return businessId; }
    public void setBusinessId(String businessId) { this.businessId = businessId; }

    public String getTaxId() { return taxId; }
    public void setTaxId(String taxId) { this.taxId = taxId; }
}
