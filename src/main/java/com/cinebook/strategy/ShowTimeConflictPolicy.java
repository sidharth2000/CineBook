package com.cinebook.strategy;

import java.time.LocalDate;
import java.time.LocalTime;

public interface ShowTimeConflictPolicy {

    boolean hasConflict(Long movieId,
                        Long screenId,
                        LocalDate date,
                        LocalTime startTime);
}
