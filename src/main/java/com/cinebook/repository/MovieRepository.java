package com.cinebook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cinebook.model.Movie;

public interface MovieRepository extends JpaRepository<Movie,Long> {

	boolean existsByMovieTitleIgnoreCase(String movieTitle);

	List<Movie> findByMovieTitleStartingWithIgnoreCase(String titleFilter);

}
