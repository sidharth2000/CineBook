package com.cinebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cinebook.model.Theatre;

@Repository
public interface TheatreRepository extends JpaRepository<Theatre, Long> {
    
}
