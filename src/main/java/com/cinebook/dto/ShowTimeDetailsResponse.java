package com.cinebook.dto;



import java.util.List;

public class ShowTimeDetailsResponse {

    private Long showTimeId;
    private MovieDto movie;
    private ScreenDto screen;
    private List<SeatDto> seats;

    public Long getShowTimeId() {
        return showTimeId;
    }

    public void setShowTimeId(Long showTimeId) {
        this.showTimeId = showTimeId;
    }

    public MovieDto getMovie() {
        return movie;
    }

    public void setMovie(MovieDto movie) {
        this.movie = movie;
    }

    public ScreenDto getScreen() {
        return screen;
    }

    public void setScreen(ScreenDto screen) {
        this.screen = screen;
    }

    public List<SeatDto> getSeats() {
        return seats;
    }

    public void setSeats(List<SeatDto> seats) {
        this.seats = seats;
    }

    // ============================================================
    // INNER CLASS: MOVIE
    // ============================================================
    public static class MovieDto {
        private Long movieId;
        private String title;
        private int duration;
        private String language;
        private String format;

        public Long getMovieId() { return movieId; }
        public void setMovieId(Long movieId) { this.movieId = movieId; }

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public int getDuration() { return duration; }
        public void setDuration(int duration) { this.duration = duration; }

        public String getLanguage() { return language; }
        public void setLanguage(String language) { this.language = language; }

        public String getFormat() { return format; }
        public void setFormat(String format) { this.format = format; }
    }

    // ============================================================
    // INNER CLASS: SCREEN
    // ============================================================
    public static class ScreenDto {
        private Long screenId;
        private String screenName;
        private int totalSeats;

        public Long getScreenId() { return screenId; }
        public void setScreenId(Long screenId) { this.screenId = screenId; }

        public String getScreenName() { return screenName; }
        public void setScreenName(String screenName) { this.screenName = screenName; }

        public int getTotalSeats() { return totalSeats; }
        public void setTotalSeats(int totalSeats) { this.totalSeats = totalSeats; }
    }

    // ============================================================
    // INNER CLASS: SEAT
    // ============================================================
    public static class SeatDto {
        private Long seatId;
        private String seatName;
        private String rowName;
        private int columnNumber;
        private String categoryName;
        private boolean available;
        private double seatPrice;

        public Long getSeatId() { return seatId; }
        public void setSeatId(Long seatId) { this.seatId = seatId; }

        public String getSeatName() { return seatName; }
        public void setSeatName(String seatName) { this.seatName = seatName; }

        public String getRowName() { return rowName; }
        public void setRowName(String rowName) { this.rowName = rowName; }

        public int getColumnNumber() { return columnNumber; }
        public void setColumnNumber(int columnNumber) { this.columnNumber = columnNumber; }

        public String getCategoryName() { return categoryName; }
        public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

        public boolean isAvailable() { return available; }
        public void setAvailable(boolean available) { this.available = available; }

        public double getSeatPrice() { return seatPrice; }
        public void setSeatPrice(double seatPrice) { this.seatPrice = seatPrice; }
    }
}

