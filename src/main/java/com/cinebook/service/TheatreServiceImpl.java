package com.cinebook.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinebook.builder.TheatreBuilder;
import com.cinebook.dto.ApiResponse;
import com.cinebook.dto.FormatDto;
import com.cinebook.dto.LanguageDto;
import com.cinebook.dto.MovieResponse;
import com.cinebook.dto.ScreenResponse;
import com.cinebook.dto.SeatCategoryResponse;
import com.cinebook.dto.ShowTimeCheckResponse;
import com.cinebook.dto.ShowTimeScheduleRequest;
import com.cinebook.dto.TheatreOnboardingRequest;
import com.cinebook.dto.TheatreResponse;
import com.cinebook.model.Address;
import com.cinebook.model.Format;
import com.cinebook.model.Language;
import com.cinebook.model.Movie;
import com.cinebook.model.OnboardingRequest;
import com.cinebook.model.RequestStatus;
import com.cinebook.model.Screen;
import com.cinebook.model.SeatCategory;
import com.cinebook.model.ShowTime;
import com.cinebook.model.Theatre;
import com.cinebook.model.TheatreOwner;
import com.cinebook.repository.FormatRepository;
import com.cinebook.repository.LanguagesRepository;
import com.cinebook.repository.MovieRepository;
import com.cinebook.repository.OnboardingRequestRepository;
import com.cinebook.repository.RequestStatusRepository;
import com.cinebook.repository.ScreenRepository;
import com.cinebook.repository.ShowtimeRepository;
import com.cinebook.repository.TheatreOwnerRepository;
import com.cinebook.repository.TheatreRepository;
import com.cinebook.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class TheatreServiceImpl implements TheatreService {

	@Autowired
	private TheatreOwnerRepository theatreOwnerRepository;

	@Autowired
	private TheatreRepository theatreRepository;

	@Autowired
	private OnboardingRequestRepository onboardingRequestRepository;

	@Autowired
	private RequestStatusRepository requestStatusRepository;

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ShowtimeRepository showtimeRepository;
	
	@Autowired
	private FormatRepository formatRepository;
	
	@Autowired
	private LanguagesRepository languageRepository;
	
	@Autowired
	private ScreenRepository screenRepository;

	@Override
	@Transactional
	public ApiResponse<Void> onboardTheatre(String userEmail, TheatreOnboardingRequest request) {

		TheatreOwner owner = theatreOwnerRepository.findByEmail(userEmail)
				.orElseThrow(() -> new RuntimeException("User is not a theatre owner"));

		Theatre theatre = TheatreBuilder.builder().theatreName(request.getTheatreName()).overview(request.getOverview())
				.contactNumber(request.getContactNumber()).address(request.getAddress()).theatreOwner(owner)
				.screens(request.getScreens()).build();

		theatreRepository.save(theatre);

		OnboardingRequest onboardingRequest = new OnboardingRequest();
		onboardingRequest.setTheatre(theatre);
		onboardingRequest.setTheatreOwner(owner);

		RequestStatus pendingStatus = requestStatusRepository.findByStatusName("PENDING");

		onboardingRequest.setStatus(pendingStatus);

		onboardingRequestRepository.save(onboardingRequest);

		return new ApiResponse<>("success", null, "Theatre onboarding request created successfully");
	}

	@Override
	public List<MovieResponse> getAllMovies(String titleFilter) {
		List<Movie> movies;
		if (titleFilter != null && !titleFilter.isEmpty()) {
			movies = movieRepository.findByMovieTitleStartingWithIgnoreCase(titleFilter);
		} else {
			movies = movieRepository.findAll();
		}

		List<MovieResponse> response = new ArrayList<>();
		for (Movie movie : movies) {
			MovieResponse mr = new MovieResponse();
			mr.setMovieId(movie.getMovieId());
			mr.setMovieTitle(movie.getMovieTitle());
			mr.setReleaseDate(movie.getReleaseDate());
			mr.setPosterUrl(movie.getPosterUrl());

			response.add(mr);
		}
		return response;
	}

	@Override
	public List<LanguageDto> getLanguagesForMovie(Long movieId) {
		Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new RuntimeException("Movie not found"));

		List<LanguageDto> langs = new ArrayList<>();

		for (Language l : movie.getAvailableLanguages()) {
			LanguageDto lr = new LanguageDto();
			lr.setLanguageId(l.getLanguageId());
			lr.setLanguage(l.getLanguage());
			langs.add(lr);
		}

		return langs;
	}

	@Override
	public List<FormatDto> getFromatsForMovie(Long movieId) {
		Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new RuntimeException("Movie not found"));

		List<FormatDto> formats = new ArrayList<>();

		for (Format l : movie.getAvailableFormats()) {
			FormatDto fr = new FormatDto();
			fr.setFormatId(l.getFormatId());
			fr.setFormat(l.getFormat());
			formats.add(fr);
		}

		return formats;
	}

	@Override
	public List<LanguageDto> getSubtitlesForMovie(Long movieId) {
		Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new RuntimeException("Movie not found"));

		List<LanguageDto> langs = new ArrayList<>();

		for (Language l : movie.getSubtitleLanguages()) {
			LanguageDto lr = new LanguageDto();
			lr.setLanguageId(l.getLanguageId());
			lr.setLanguage(l.getLanguage());
			langs.add(lr);
		}

		return langs;
	}

	@Override
	public List<TheatreResponse> getOwnedTheatres(String ownerEmail) {

		TheatreOwner owner = theatreOwnerRepository.findByEmail(ownerEmail)
				.orElseThrow(() -> new RuntimeException("Theatre owner not found"));

		List<Theatre> theatres = theatreRepository.findByTheatreOwnerUserIdAndIsActiveTrue(owner.getUserId());

		List<TheatreResponse> response = new ArrayList<>();

		for (Theatre t : theatres) {
			TheatreResponse theatreResponse = new TheatreResponse();
			theatreResponse.setTheatreId(t.getTheatreId());
			theatreResponse.setTheatreName(t.getTheatreName());

			// Format Address
			String location = "";
			if (t.getAddress() != null) {
				Address a = t.getAddress();

				StringBuilder sb = new StringBuilder();

				if (a.getAddressLine1() != null)
					sb.append(a.getAddressLine1());
				if (a.getAddressLine2() != null)
					sb.append(", ").append(a.getAddressLine2());
				if (a.getCounty() != null)
					sb.append(", ").append(a.getCounty());
				if (a.getEirCode() != null)
					sb.append(", ").append(a.getEirCode());

				location = sb.toString();
			}

			theatreResponse.setLocation(location);

			response.add(theatreResponse);
		}

		return response;
	}

	@Override
	public List<ScreenResponse> getScreensByTheatreId(Long theatreId, String ownerEmail) {

		TheatreOwner owner = theatreOwnerRepository.findByEmail(ownerEmail)
				.orElseThrow(() -> new RuntimeException("Theatre owner not found"));

		Theatre theatre = theatreRepository.findByTheatreIdAndTheatreOwnerUserId(theatreId, owner.getUserId())
				.orElseThrow(() -> new RuntimeException("Theatre does not belong to this owner"));

		List<Screen> screens = theatre.getScreenList();

		List<ScreenResponse> screenResponses = new ArrayList<>();

		for (Screen s : screens) {
			ScreenResponse sr = new ScreenResponse();
			sr.setScreenId(s.getScreenId());
			sr.setScreenName(s.getScreenName());
			sr.setTotalSeats(s.getTotalSeats());

			List<SeatCategoryResponse> seatCatResp = new ArrayList<>();
			for (SeatCategory sc : s.getSeatCategories()) {
				SeatCategoryResponse scr = new SeatCategoryResponse();
				scr.setCategoryId(sc.getSeatCategoryId());
				scr.setCategoryName(sc.getCategoryName());
				scr.setPriceMultiplier(sc.getPriceMultiplier());
				seatCatResp.add(scr);
			}

			sr.setSeatCategories(seatCatResp);
			screenResponses.add(sr);
		}

		return screenResponses;
	}

	@Override
	public ShowTimeCheckResponse checkShowTime(Long movieId, Long screenId, 
	                                           LocalDate startDate, LocalDate endDate, 
	                                           LocalTime startTime) {

	    ShowTimeCheckResponse response = new ShowTimeCheckResponse();

	    Movie movie = movieRepository.findById(movieId)
	            .orElseThrow(() -> new RuntimeException("Movie not found"));

	    long runTimeWithBuffer = movie.getRunTimeMinutes() + 15; // runtime + buffer
	    LocalTime calculatedEndTime = startTime.plusMinutes(runTimeWithBuffer);

	    // Iterate through all dates in the range
	    for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
	        LocalDateTime startDateTime = LocalDateTime.of(date, startTime);
	        LocalDateTime endDateTime = startDateTime.plusMinutes(runTimeWithBuffer);

	        // Overlap check for this date
	        List<ShowTime> conflicts = showtimeRepository.findOverlappingShowTimes(screenId, date, startDateTime, endDateTime);

	        if (!conflicts.isEmpty()) {
	            response.setValid(false);
	            response.setCalculatedEndTime(null);
	            response.setMessage("Showtime overlaps with an existing show on " + date);
	            return response; // exit on first conflict
	        }
	    }

	    // If no conflicts in the entire range
	    response.setValid(true);
	    response.setCalculatedEndTime(calculatedEndTime); // only time part
	    response.setMessage("Showtime slot is available for the entire range.");

	    return response;
	}
	
	
	
	@Override
	@Transactional
	public List<LocalDate> scheduleShowTime(ShowTimeScheduleRequest request) {


	    Movie movie = movieRepository.findById(request.getMovieId())
	            .orElseThrow(() -> new RuntimeException("Movie not found"));
	    Format format = formatRepository.findById(request.getFormatId())
	            .orElseThrow(() -> new RuntimeException("Format not found"));
	    Language language = languageRepository.findById(request.getLanguageId())
	            .orElseThrow(() -> new RuntimeException("Language not found"));

	    Language subtitleLanguage = null;
	    if (request.getSubtitleLanguageId() != null) {
	        subtitleLanguage = languageRepository.findById(request.getSubtitleLanguageId())
	                .orElseThrow(() -> new RuntimeException("Subtitle language not found"));
	    }

	    Screen screen = screenRepository.findById(request.getScreenId())
	            .orElseThrow(() -> new RuntimeException("Screen not found"));

	    List<LocalDate> allDates = new ArrayList<>();
	    for (LocalDate date = request.getStartDate(); !date.isAfter(request.getEndDate()); date = date.plusDays(1)) {
	        allDates.add(date);
	    }

	    
	    for (LocalDate date : allDates) {
	        ShowTimeCheckResponse check = this.checkShowTime(
	                movie.getMovieId(),
	                screen.getScreenId(),
	                date,
	                date,
	                request.getStartTime()
	        );

	        if (!check.isValid()) {
	            return null;
	        }
	    }

	    LocalTime startTime = request.getStartTime();
	    LocalTime calculatedEndTime = startTime.plusMinutes(movie.getRunTimeMinutes() + 15);

	    for (LocalDate date : allDates) {
	        LocalDateTime startDateTime = LocalDateTime.of(date, startTime);
	        LocalDateTime endDateTime = LocalDateTime.of(date, calculatedEndTime);

	        ShowTime showTime = new ShowTime();
	        showTime.setMovie(movie);
	        showTime.setFormat(format);
	        showTime.setLanguage(language);
	        showTime.setSubtitleLanguage(subtitleLanguage);
	        showTime.setScreen(screen);
	        showTime.setStartTime(startDateTime);
	        showTime.setEndTime(endDateTime);
	        showTime.setPrice(request.getBasePrice());
	        showTime.setCreatedAt(LocalDateTime.now());
	        showTime.setModifiedAt(LocalDateTime.now());

	        showtimeRepository.save(showTime);
	    }

	    return allDates;
	}
}
