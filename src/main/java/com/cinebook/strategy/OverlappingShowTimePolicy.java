package com.cinebook.strategy;

import com.cinebook.model.Movie;
import com.cinebook.repository.MovieRepository;
import com.cinebook.repository.ShowtimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class OverlappingShowTimePolicy implements ShowTimeConflictPolicy {

    @Autowired
    private ShowtimeRepository repository;

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public boolean hasConflict(Long movieId, Long screenId,
                               LocalDate date, LocalTime startTime) {

        Movie movie = movieRepository.findById(movieId).orElseThrow();
        long runtimeWithBuffer = movie.getRunTimeMinutes() + 15;

        LocalDateTime start = LocalDateTime.of(date, startTime);
        LocalDateTime end = start.plusMinutes(runtimeWithBuffer);

        return !repository.findOverlappingShowTimes(screenId, date, start, end).isEmpty();
    }
}