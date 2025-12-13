/**
 * @author Abhaydev and Mathew
 * 
 * 
 */

package com.cinebook.dto;

import java.time.LocalDate;
import java.util.List;

import com.cinebook.model.Certification;
import com.cinebook.model.Genre;

public class MovieRequest {

	private String movieTitle;
	private String synopsis;
	private Integer runTimeMinutes;
	private LocalDate releaseDate;
	private String posterUrl;
	private Certification certificate;
	private Genre genre;
	private List<Integer> formatIds;
	private List<Integer> languageIds;
	private List<Integer> subtitleLanguageIds;

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

	public LocalDate getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}

	public List<Integer> getFormatIds() {
		return formatIds;
	}

	public void setFormatIds(List<Integer> formatIds) {
		this.formatIds = formatIds;
	}

	public List<Integer> getLanguageIds() {
		return languageIds;
	}

	public void setLanguageIds(List<Integer> languageIds) {
		this.languageIds = languageIds;
	}

	public List<Integer> getSubtitleLanguageIds() {
		return subtitleLanguageIds;
	}

	public void setSubtitleLanguageIds(List<Integer> subtitleLanguageIds) {
		this.subtitleLanguageIds = subtitleLanguageIds;
	}

	public String getPosterUrl() {
		return posterUrl;
	}

	public void setPosterUrl(String posterUrl) {
		this.posterUrl = posterUrl;
	}

	public Certification getCertificate() {
		return certificate;
	}

	public void setCertificate(Certification certificate) {
		this.certificate = certificate;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

}
