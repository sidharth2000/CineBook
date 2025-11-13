package com.cinebook.model;

import jakarta.persistence.*;

@Entity
public class RequestStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statusId;

    @Column(nullable = false, unique = true)
    private String statusName;

    public RequestStatus() {}

    public RequestStatus(String statusName) {
        this.statusName = statusName;
    }

    // Getters and Setters
    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
