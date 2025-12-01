package com.cinebook.dto;

import java.time.LocalDate;
import java.util.List;

import com.cinebook.model.Certification;

public class MovieBrowseResponse {
    private Long movieId;
    private String title;
    private String genre;
    private Certification certification;
    private String posterUrl;
    private LocalDate releaseDate;
    private List<String> availableLanguages;
    private List<String> availableFormats;

    // Getters and setters
    public Long getMovieId() { return movieId; }
    public void setMovieId(Long movieId) { this.movieId = movieId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public Certification getCertification() { return certification; }
    public void setCertification(Certification certification) { this.certification = certification; }

    public String getPosterUrl() { return posterUrl; }
    public void setPosterUrl(String posterUrl) { this.posterUrl = posterUrl; }

    public LocalDate getReleaseDate() { return releaseDate; }
    public void setReleaseDate(LocalDate releaseDate) { this.releaseDate = releaseDate; }

    public List<String> getAvailableLanguages() { return availableLanguages; }
    public void setAvailableLanguages(List<String> availableLanguages) { this.availableLanguages = availableLanguages; }

    public List<String> getAvailableFormats() { return availableFormats; }
    public void setAvailableFormats(List<String> availableFormats) { this.availableFormats = availableFormats; }
}