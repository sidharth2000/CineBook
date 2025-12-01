package com.cinebook.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cinebook.model.ShowTime;

public interface ShowtimeRepository extends JpaRepository<ShowTime, Long> {
	@Query("""
			    SELECT s FROM ShowTime s
			    WHERE s.screen.screenId = :screenId
			      AND DATE(s.startTime) = :date
			      AND (
			           (s.startTime < :newEnd) AND (s.endTime > :newStart)
			      )
			""")
	List<ShowTime> findOverlappingShowTimes(@Param("screenId") Long screenId, @Param("date") LocalDate date,
			@Param("newStart") LocalDateTime newStart, @Param("newEnd") LocalDateTime newEnd);

	List<ShowTime> findByStartTimeAfterAndLanguage_LanguageIdInAndFormat_FormatIdIn(LocalDateTime fromDate,
			List<Long> languageIds, List<Long> formatIds);

	List<ShowTime> findByStartTimeAfterAndLanguage_LanguageIdIn(LocalDateTime now, List<Long> languageIds);

	List<ShowTime> findByStartTimeAfterAndFormat_FormatIdIn(LocalDateTime now, List<Long> formatIds);

	List<ShowTime> findByStartTimeAfter(LocalDateTime fromDate);

	List<ShowTime> findByScreen_ScreenIdInAndStartTimeAfter(List<Long> screenIds, LocalDateTime now);
	
	ShowTime findByShowTimeId(long showTimeId);

}
