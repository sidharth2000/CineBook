package com.cinebook.builder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.cinebook.dto.TheatreOnboardingRequest;
import com.cinebook.model.Address;
import com.cinebook.model.Screen;
import com.cinebook.model.Seat;
import com.cinebook.model.SeatCategory;
import com.cinebook.model.Theatre;
import com.cinebook.model.TheatreOwner;

public class TheatreBuilder {

    private final Theatre theatre;

    private TheatreBuilder() {
        this.theatre = new Theatre();
        this.theatre.setCreatedAt(LocalDateTime.now());
        this.theatre.setUpdatedAt(LocalDateTime.now());
    }

    public static TheatreBuilder builder() {
        return new TheatreBuilder();
    }

    public TheatreBuilder theatreName(String name) {
        theatre.setTheatreName(name);
        return this;
    }

    public TheatreBuilder overview(String overview) {
        theatre.setOverview(overview);
        return this;
    }

    public TheatreBuilder contactNumber(String contactNumber) {
        theatre.setContactNumber(contactNumber);
        return this;
    }

    public TheatreBuilder address(TheatreOnboardingRequest.AddressRequest addressRequest) {
        Address address = new Address();
        address.setAddressLine1(addressRequest.getAddressLine1());
        address.setAddressLine2(addressRequest.getAddressLine2());
        address.setCounty(addressRequest.getCounty());
        address.setEirCode(addressRequest.getEirCode());
        theatre.setAddress(address);
        return this;
    }

    public TheatreBuilder theatreOwner(TheatreOwner owner) {
        theatre.setTheatreOwner(owner);
        return this;
    }

    public TheatreBuilder screens(List<TheatreOnboardingRequest.ScreenRequest> screenRequests) {
        List<Screen> screens = new ArrayList<>();

        for (TheatreOnboardingRequest.ScreenRequest screenReq : screenRequests) {
            Screen screen = new Screen();
            screen.setScreenName(screenReq.getScreenName());
            screen.setCreatedAt(LocalDateTime.now());
            screen.setUpdatedAt(LocalDateTime.now());
            screen.setTheatre(theatre);

            // Seat categories
            List<SeatCategory> seatCategories = new ArrayList<>();
            for (TheatreOnboardingRequest.SeatCategoryRequest catReq : screenReq.getSeatCategories()) {
                SeatCategory category = new SeatCategory();
                category.setCategoryName(catReq.getCategoryName());
                category.setDescription(catReq.getDescription());
                category.setPriceMultiplier(catReq.getPriceMultiplier());
                category.setScreen(screen);
                seatCategories.add(category);
            }

            // Seats
            List<Seat> seats = new ArrayList<>();
            for (char row : screenReq.getRowLabels()) {
                for (int col = 1; col <= screenReq.getNumColumns(); col++) {
                    Seat seat = new Seat();
                    seat.setRowName(String.valueOf(row));
                    seat.setColumnNumber(col);
                    seat.setSeatName("" + row + col);
                    seat.setScreen(screen);

                    // Assign seat category
                    for (TheatreOnboardingRequest.SeatCategoryRequest catReq : screenReq.getSeatCategories()) {
                        if (catReq.getRowsCovered().contains(row)) {
                            Optional<SeatCategory> matched = seatCategories.stream()
                                    .filter(sc -> sc.getCategoryName().equals(catReq.getCategoryName()))
                                    .findFirst();
                            matched.ifPresent(seat::setSeatCategory);
                            break;
                        }
                    }

                    seats.add(seat);
                }
            }

            // Link seats to categories
            for (SeatCategory sc : seatCategories) {
                List<Seat> catSeats = seats.stream()
                        .filter(s -> s.getSeatCategory() == sc)
                        .toList();
                sc.setSeats(catSeats);
            }

            screen.setSeatCategories(seatCategories);
            screen.setTotalSeats(seats.size());
            screens.add(screen);
        }

        theatre.setScreenList(screens);
        return this;
    }

    public Theatre build() {
        return theatre;
    }
}
