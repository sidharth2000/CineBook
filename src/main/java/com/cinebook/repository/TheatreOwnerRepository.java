package com.cinebook.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cinebook.model.TheatreOwner;

@Repository
public interface TheatreOwnerRepository extends JpaRepository<TheatreOwner, Long>{
	Optional<TheatreOwner> findByEmail(String email);
	
}
