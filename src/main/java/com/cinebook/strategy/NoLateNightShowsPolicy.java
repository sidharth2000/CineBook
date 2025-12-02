package com.cinebook.strategy;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class NoLateNightShowsPolicy implements ShowTimeConflictPolicy {

    private static final LocalTime LAST_ALLOWED_START = LocalTime.of(23, 30);

    @Override
    public boolean hasConflict(Long movieId, Long screenId,
                               LocalDate date, LocalTime startTime) {

        return startTime.isAfter(LAST_ALLOWED_START);
    }
}
