package com.cinebook.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cinebook.model.Theatre;

@Repository
public interface TheatreRepository extends JpaRepository<Theatre, Long> {

//	List<Theatre> findByOwnerAndIsActiveTrue(User owner);

//	List<Theatre> findByTheatreOwnerAndIsActiveTrue(TheatreOwner theatreOwner);
	List<Theatre> findByTheatreOwnerUserIdAndIsActiveTrue(Long ownerId);
	Optional<Theatre> findByTheatreIdAndTheatreOwnerUserId(Long theatreId, Long ownerId);
    
}
