package com.cinebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cinebook.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer,Long> {

	Customer findByEmail(String customerEmail);

}
