package com.cinebook.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieId;

    @Column(nullable = false)
    private String movieTitle;

    @Column(length = 2000)
    private String synopsis;

    private Integer runTimeMinutes;
    
    @Column(name = "release_date")
    private LocalDate releaseDate;
    
    @Column(name = "poster_url")
    private String posterUrl;

    @ManyToMany
    @JoinTable(
        name = "movie_formats",
        joinColumns = @JoinColumn(name = "movie_id"),
        inverseJoinColumns = @JoinColumn(name = "format_id")
    )
    private List<Format> availableFormats;

    @ManyToMany
    @JoinTable(
        name = "movie_languages",
        joinColumns = @JoinColumn(name = "movie_id"),
        inverseJoinColumns = @JoinColumn(name = "language_id")
    )
    private List<Language> availableLanguages;

    @ManyToMany
    @JoinTable(
        name = "movie_subtitle_languages",
        joinColumns = @JoinColumn(name = "movie_id"),
        inverseJoinColumns = @JoinColumn(name = "language_id")
    )
    private List<Language> subtitleLanguages;
    
    
    @ManyToOne
    @JoinColumn(name = "created_by_admin_id")
    private Admin createdBy;

    // Getters and setters
    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public Integer getRunTimeMinutes() {
        return runTimeMinutes;
    }

    public void setRunTimeMinutes(Integer runTimeMinutes) {
        this.runTimeMinutes = runTimeMinutes;
    }

    public List<Format> getAvailableFormats() {
        return availableFormats;
    }

    public void setAvailableFormats(List<Format> availableFormats) {
        this.availableFormats = availableFormats;
    }

    public List<Language> getAvailableLanguages() {
        return availableLanguages;
    }

    public void setAvailableLanguages(List<Language> availableLanguages) {
        this.availableLanguages = availableLanguages;
    }

    public List<Language> getSubtitleLanguages() {
        return subtitleLanguages;
    }

    public void setSubtitleLanguages(List<Language> subtitleLanguages) {
        this.subtitleLanguages = subtitleLanguages;
    }

	public LocalDate getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Admin getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Admin createdBy) {
		this.createdBy = createdBy;
	}
    
	public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }
    
}
