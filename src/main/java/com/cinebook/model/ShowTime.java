package com.cinebook.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "show_times")
public class ShowTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "show_time_id")
    private Long showTimeId;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "price", nullable = false)
    private Float price;

    // Each showtime will have one format (2D, 3D, IMAX, etc.)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "format_id", nullable = false)
    private Format format;

    // The language in which the movie is played
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

    // The subtitle language (optional)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subtitle_language_id")
    private Language subtitleLanguage;

    // Which movie is being shown
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    // Which screen it’s shown in
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "screen_id", nullable = false)
    private Screen screen;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    // --- Constructors ---
    public ShowTime() {
        this.createdAt = LocalDateTime.now();
    }

    // --- Getters & Setters ---
    public Long getShowTimeId() {
        return showTimeId;
    }

    public void setShowTimeId(Long showTimeId) {
        this.showTimeId = showTimeId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Language getSubtitleLanguage() {
        return subtitleLanguage;
    }

    public void setSubtitleLanguage(Language subtitleLanguage) {
        this.subtitleLanguage = subtitleLanguage;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Screen getScreen() {
        return screen;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
}
