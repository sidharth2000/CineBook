package com.cinebook.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinebook.command.ApplyDiscountCommand;
import com.cinebook.command.LockSeatsCommand;
import com.cinebook.dto.BrowseTheatreRequest;
import com.cinebook.dto.LockSeatsResponse;
import com.cinebook.dto.MovieBrowseResponse;
import com.cinebook.dto.ShowTimeDetailsResponse;
import com.cinebook.dto.TheatreBrowseResponse;
import com.cinebook.dto.TheatreShowtimesResponse;
import com.cinebook.model.Booking;
import com.cinebook.model.Customer;
import com.cinebook.model.GlobalConfig;
import com.cinebook.model.Movie;
import com.cinebook.model.Screen;
import com.cinebook.model.Seat;
import com.cinebook.model.ShowTime;
import com.cinebook.model.Theatre;
import com.cinebook.repository.BookingRepository;
import com.cinebook.repository.BookingStatusRepository;
import com.cinebook.repository.CustomerRepository;
import com.cinebook.repository.GlobalConfigRepository;
import com.cinebook.repository.PromoCodeRepository;
import com.cinebook.repository.SeatRepository;
import com.cinebook.repository.ShowtimeRepository;
import com.cinebook.repository.TheatreRepository;
import com.cinebook.repository.VoucherCodeRepository;

import jakarta.transaction.Transactional;

@Service
public class BookingServiceImpl implements BookingService {
	
	 	@Autowired
	    private ShowtimeRepository showTimeRepository;
	 	
	 	@Autowired
	    private TheatreRepository theatreRepository;
	 	
	 	@Autowired
	    private SeatRepository seatRepository;
	 	
	 	@Autowired
	    private BookingRepository bookingRepository;
	 	
	 	@Autowired
	    private BookingStatusRepository bookingStatusRepository;
	 	
	 	@Autowired
	 	private CustomerRepository customerRepository;
	 	
	 	@Autowired
	 	private GlobalConfigRepository globalConfigRepository;
	 	
	 	@Autowired
	 	private VoucherCodeRepository voucherCodeRepository;
	 	
	 	@Autowired
	 	private PromoCodeRepository promoCodeRepository;
	 	
	 	
	 	@Override
	    public List<TheatreBrowseResponse> browseTheatres(BrowseTheatreRequest request) {
	        List<ShowTime> showTimes = showTimeRepository.findByStartTimeAfter(LocalDateTime.now());

	        Map<Long, TheatreBrowseResponse> uniqueTheatres = new HashMap<>();

	        for (ShowTime st : showTimes) {
	            Theatre theatre = st.getScreen().getTheatre();
	            if (theatre == null) continue;

	            if (request.getSearch() != null && !request.getSearch().isBlank()) {
	                if (!theatre.getTheatreName().toLowerCase()
	                        .contains(request.getSearch().toLowerCase())) {
	                    continue;
	                }
	            }

	            if (request.getCounty() != null && !request.getCounty().isBlank()) {
	                if (!theatre.getAddress().getCounty().trim()
	                        .equalsIgnoreCase(request.getCounty().trim())) {
	                    continue;
	                }
	            }

	            if (!uniqueTheatres.containsKey(theatre.getTheatreId())) {
	                TheatreBrowseResponse resp = new TheatreBrowseResponse(
	                        theatre.getTheatreId(),
	                        theatre.getTheatreName(),
	                        theatre.getOverview(),
	                        theatre.getAddress().getCounty()
	                );
	                uniqueTheatres.put(theatre.getTheatreId(), resp);
	            }
	        }

	        return new ArrayList<>(uniqueTheatres.values());
	    }
	 	@Override
	 	public TheatreShowtimesResponse getShowtimesByTheatre(Long theatreId) {

	 	    // 1. Load theatre
	 	    Theatre theatre = theatreRepository.findById(theatreId)
	 	            .orElseThrow(() -> new RuntimeException("Theatre not found"));

	 	    // 2. Collect screen IDs (plain loop)
	 	    List<Long> screenIds = new ArrayList<>();
	 	    if (theatre.getScreenList() != null) {
	 	        for (Screen s : theatre.getScreenList()) {
	 	            if (s != null && s.getScreenId() != null) {
	 	                screenIds.add(s.getScreenId());
	 	            }
	 	        }
	 	    }

	 	    if (screenIds.isEmpty()) {
	 	        throw new RuntimeException("This theatre has no screens");
	 	    }

	 	    // 3. Fetch upcoming showtimes for those screens
	 	    List<ShowTime> showTimes = showTimeRepository.findByScreen_ScreenIdInAndStartTimeAfter(
	 	            screenIds,
	 	            LocalDateTime.now()
	 	    );

	 	    // 4. Grouping structure:
	 	    // Map<LocalDate, Map<movieId, MovieShowtimeGroup>>
	 	    Map<java.time.LocalDate, Map<Long, TheatreShowtimesResponse.MovieShowtimeGroup>> byDate = new HashMap<>();

	 	    for (ShowTime st : showTimes) {
	 	        if (st == null || st.getStartTime() == null || st.getMovie() == null) {
	 	            continue; // defensive
	 	        }

	 	        java.time.LocalDate date = st.getStartTime().toLocalDate();
	 	        Long movieId = st.getMovie().getMovieId();

	 	        // ensure date map exists
	 	        Map<Long, TheatreShowtimesResponse.MovieShowtimeGroup> movieMap = byDate.get(date);
	 	        if (movieMap == null) {
	 	            movieMap = new HashMap<>();
	 	            byDate.put(date, movieMap);
	 	        }

	 	        // ensure MovieShowtimeGroup exists for this movie
	 	        TheatreShowtimesResponse.MovieShowtimeGroup group = movieMap.get(movieId);
	 	        if (group == null) {
	 	            group = new TheatreShowtimesResponse.MovieShowtimeGroup();
	 	            group.setMovieId(movieId);
	 	            group.setMovieTitle(st.getMovie().getMovieTitle());
	 	            group.setPosterUrl(st.getMovie().getPosterUrl());
	 	            group.setShowtimes(new ArrayList<TheatreShowtimesResponse.ShowTimeSlot>());

	 	            movieMap.put(movieId, group);
	 	        }

	 	        // create a ShowTimeSlot for this showtime
	 	        TheatreShowtimesResponse.ShowTimeSlot slot = new TheatreShowtimesResponse.ShowTimeSlot();
	 	        slot.setShowTimeId(st.getShowTimeId());
	 	        slot.setStartTime(st.getStartTime().toLocalTime());
	 	        slot.setEndTime(st.getEndTime().toLocalTime());

	 	        // format / language accessor names used as in your previous snippet:
	 	        // adjust the method names here if your Format/Language classes use different getters.
	 	        String formatName = null;
	 	        if (st.getFormat() != null) {
	 	            try {
	 	                formatName = st.getFormat().getFormat();
	 	            } catch (Exception ex) {
	 	                // fallback if method name is different
	 	                try { formatName = st.getFormat().getFormat(); } catch (Exception ignored) {}
	 	            }
	 	        }
	 	        slot.setFormat(formatName);

	 	        String languageName = null;
	 	        if (st.getLanguage() != null) {
	 	            try {
	 	                languageName = st.getLanguage().getLanguage();
	 	            } catch (Exception ex) {
	 	                try { languageName = st.getLanguage().getLanguage(); } catch (Exception ignored) {}
	 	            }
	 	        }
	 	        slot.setLanguage(languageName);

	 	        String subtitleName = null;
	 	        if (st.getSubtitleLanguage() != null) {
	 	            try {
	 	                subtitleName = st.getSubtitleLanguage().getLanguage();
	 	            } catch (Exception ex) {
	 	                try { subtitleName = st.getSubtitleLanguage().getLanguage(); } catch (Exception ignored) {}
	 	            }
	 	        }
	 	        slot.setSubtitleLanguage(subtitleName);

	 	        slot.setPrice(st.getPrice());

	 	        // add slot to group's list
	 	        group.getShowtimes().add(slot);
	 	    }

	 	    // 5. Convert nested maps to the DTO schedule Map<LocalDate, List<MovieShowtimeGroup>>
	 	    Map<java.time.LocalDate, List<TheatreShowtimesResponse.MovieShowtimeGroup>> schedule = new HashMap<>();
	 	    for (Map.Entry<java.time.LocalDate, Map<Long, TheatreShowtimesResponse.MovieShowtimeGroup>> dateEntry : byDate.entrySet()) {
	 	        java.time.LocalDate date = dateEntry.getKey();
	 	        Map<Long, TheatreShowtimesResponse.MovieShowtimeGroup> movieMap = dateEntry.getValue();

	 	        List<TheatreShowtimesResponse.MovieShowtimeGroup> groups = new ArrayList<>();
	 	        for (Map.Entry<Long, TheatreShowtimesResponse.MovieShowtimeGroup> mEntry : movieMap.entrySet()) {
	 	            groups.add(mEntry.getValue());
	 	        }

	 	        schedule.put(date, groups);
	 	    }

	 	    // 6. Build response
	 	    TheatreShowtimesResponse response = new TheatreShowtimesResponse();
	 	    response.setTheatreId(theatre.getTheatreId());
	 	    response.setTheatreName(theatre.getTheatreName());
	 	    response.setTheatreAddress(theatre.getAddress());
	 	    response.setTheatreOverview(theatre.getOverview());
	 	    response.setSchedule(schedule);

	 	    return response;
	 	}
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	

	    @Override
	    public List<MovieBrowseResponse> getAvailableMovies(
	            List<Long> languageIds,
	            List<Long> formatIds,
	            String genre,
	            String search,
	            boolean sortAsc
	    ) {
	        List<ShowTime> showTimes;

	        if ((languageIds != null && !languageIds.isEmpty()) && (formatIds != null && !formatIds.isEmpty())) {
	            // Both filters
	            showTimes = showTimeRepository.findByStartTimeAfterAndLanguage_LanguageIdInAndFormat_FormatIdIn(
	                    LocalDateTime.now(), languageIds, formatIds
	            );
	        } else if (languageIds != null && !languageIds.isEmpty()) {
	            // Only language filter
	            showTimes = showTimeRepository.findByStartTimeAfterAndLanguage_LanguageIdIn(
	                    LocalDateTime.now(), languageIds
	            );
	        } else if (formatIds != null && !formatIds.isEmpty()) {
	            // Only format filter
	            showTimes = showTimeRepository.findByStartTimeAfterAndFormat_FormatIdIn(
	                    LocalDateTime.now(), formatIds
	            );
	        } else {
	            // No filters
	            showTimes = showTimeRepository.findByStartTimeAfter(LocalDateTime.now());
	        }

	        // Aggregate movies
	        Map<Long, MovieBrowseResponse> movieMap = new HashMap<>();
	        for (ShowTime st : showTimes) {
	            Movie movie = st.getMovie();
	            if (movie == null) continue;

	            // Filter by genre
	            if (genre != null && !genre.isEmpty() && !movie.getGenre().name().equalsIgnoreCase(genre)) {
	                continue;
	            }

	            // Filter by search
	            if (search != null && !search.isEmpty() &&
	                    !movie.getMovieTitle().toLowerCase().contains(search.toLowerCase())) {
	                continue;
	            }

	            MovieBrowseResponse response = movieMap.get(movie.getMovieId());
	            if (response == null) {
	                response = new MovieBrowseResponse();
	                response.setMovieId(movie.getMovieId());
	                response.setTitle(movie.getMovieTitle());
	                response.setGenre(movie.getGenre().name());
	                response.setCertification(movie.getCertification());
	                response.setPosterUrl(movie.getPosterUrl());
	                response.setReleaseDate(movie.getReleaseDate());
	                response.setAvailableLanguages(new ArrayList<>());
	                response.setAvailableFormats(new ArrayList<>());
	                movieMap.put(movie.getMovieId(), response);
	            }

	            // Add language
	            String lang = st.getLanguage().getLanguage();
	            if (!response.getAvailableLanguages().contains(lang)) {
	                response.getAvailableLanguages().add(lang);
	            }

	            // Add format
	            String fmt = st.getFormat().getFormat();
	            if (!response.getAvailableFormats().contains(fmt)) {
	                response.getAvailableFormats().add(fmt);
	            }
	        }

	        List<MovieBrowseResponse> result = new ArrayList<>(movieMap.values());

	        // Sort by title
	        result.sort((a, b) -> sortAsc ? a.getTitle().compareToIgnoreCase(b.getTitle())
	                : b.getTitle().compareToIgnoreCase(a.getTitle()));

	        return result;
	    }
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    @Override
	    public ShowTimeDetailsResponse getShowTimeDetails(Long showTimeId) {

	        ShowTime showTime = showTimeRepository.findById(showTimeId)
	                .orElseThrow(() -> new RuntimeException("ShowTime not found"));

	        double basePrice = showTime.getPrice();
	        Screen screen = showTime.getScreen();

	        List<Seat> allSeats = seatRepository.findByScreen(screen);

	        List<Booking> bookings = bookingRepository
	                .findByShowTime_ShowTimeIdAndStatus_StatusNameIn(
	                        showTimeId,
	                        List.of("SUCCESS", "PENDING")
	                );

	        Set<Long> bookedSeatIds = bookings.stream()
	                .flatMap(b -> b.getSeats().stream())
	                .map(Seat::getSeatId)
	                .collect(Collectors.toSet());

	        // Prepare Main DTO
	        ShowTimeDetailsResponse dto = new ShowTimeDetailsResponse();
	        dto.setShowTimeId(showTime.getShowTimeId());

	        // Movie DTO
	        ShowTimeDetailsResponse.MovieDto movieDto = new ShowTimeDetailsResponse.MovieDto();
	        movieDto.setMovieId(showTime.getMovie().getMovieId());
	        movieDto.setTitle(showTime.getMovie().getMovieTitle());
	        movieDto.setDuration(showTime.getMovie().getRunTimeMinutes());
	        movieDto.setLanguage(showTime.getLanguage().getLanguage());
	        movieDto.setFormat(showTime.getFormat().getFormat());
	        dto.setMovie(movieDto);

	        // Screen DTO
	        ShowTimeDetailsResponse.ScreenDto screenDto = new ShowTimeDetailsResponse.ScreenDto();
	        screenDto.setScreenId(screen.getScreenId());
	        screenDto.setScreenName(screen.getScreenName());
	        screenDto.setTotalSeats(screen.getTotalSeats());
	        dto.setScreen(screenDto);

	        // Seat DTOs
	        List<ShowTimeDetailsResponse.SeatDto> seatDtos = new ArrayList<>();

	        for (Seat s : allSeats) {

	            ShowTimeDetailsResponse.SeatDto seatDto = new ShowTimeDetailsResponse.SeatDto();
	            seatDto.setSeatId(s.getSeatId());
	            seatDto.setSeatName(s.getSeatName());
	            seatDto.setRowName(s.getRowName());
	            seatDto.setColumnNumber(s.getColumnNumber());
	            seatDto.setCategoryName(s.getSeatCategory().getCategoryName());

	            // Price calculation
	            double seatPrice = basePrice * s.getSeatCategory().getPriceMultiplier();
	            seatDto.setSeatPrice(seatPrice);

	            // Availability
	            seatDto.setAvailable(!bookedSeatIds.contains(s.getSeatId()));

	            seatDtos.add(seatDto);
	        }

	        dto.setSeats(seatDtos);

	        return dto;
	    }
	    
	    
	    @Override
	    @Transactional
	    public LockSeatsResponse lockSeats(Long showTimeId, String customerEmail, List<Long> seatIds) {
	    	
	    	GlobalConfig config = globalConfigRepository.findByConfigKey("TAX_PERCENTAGE");
	    	float taxPercentage = Float.parseFloat(config.getConfigValue());
	    	
	    	LockSeatsCommand lockCommand = new LockSeatsCommand(
	    	        showTimeId,
	    	        customerEmail,
	    	        seatIds,
	    	        bookingRepository,
	    	        bookingStatusRepository,
	    	        customerRepository,
	    	        seatRepository,
	    	        showTimeRepository,
	    	        taxPercentage,
	    	        null
	    	);

	        lockCommand.execute();

	        Booking booking = lockCommand.getBooking();

	        LockSeatsResponse response = new LockSeatsResponse();
	        response.setBookingId(booking.getBookingId().toString());
	        response.setTotalPrice(booking.getTotalPrice());
	        response.setTaxAmount(booking.getTaxAmount());
	        response.setTaxPercentage(booking.getTaxPercentage());
	        response.setLockedSeats(
	                booking.getSeats().stream().map(Seat::getSeatName).toList()
	        );

	        return response;
	    }
	    
	    
	    
	    @Override
	    public void applyDiscount(UUID bookingId, String discountCode, String customerEmail) {
	        // Fetch customer
	        Customer customer = customerRepository.findByEmail(customerEmail);
	        if (customer == null) {
	            throw new RuntimeException("Customer not found");
	        }

	        // Create command
	        ApplyDiscountCommand command = new ApplyDiscountCommand(
	                bookingId,
	                discountCode,
	                customer,
	                bookingRepository,
	                bookingStatusRepository,
	                promoCodeRepository,
	                voucherCodeRepository
	        );

	        command.execute();
	    }
	    
}
