package com.cinebook.dto;

import java.util.List;

public class BrowseMovieRequest {
    private List<Long> languageIds;
    private List<Long> formatIds;
    private String genre;
    private String search;
    private boolean sortAsc = true; // default ascending

    // Getters and setters
    public List<Long> getLanguageIds() { return languageIds; }
    public void setLanguageIds(List<Long> languageIds) { this.languageIds = languageIds; }

    public List<Long> getFormatIds() { return formatIds; }
    public void setFormatIds(List<Long> formatIds) { this.formatIds = formatIds; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public String getSearch() { return search; }
    public void setSearch(String search) { this.search = search; }

    public boolean isSortAsc() { return sortAsc; }
    public void setSortAsc(boolean sortAsc) { this.sortAsc = sortAsc; }
}
