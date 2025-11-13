package com.cinebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cinebook.model.RequestStatus;

public interface RequestStatusRepository extends JpaRepository<RequestStatus, Integer> {
	RequestStatus findByStatusName(String statusName);
}
