package com.cinebook.builder;

import java.time.LocalDateTime;

import com.cinebook.model.*;

public class ShowTimeBuilder {

    private final ShowTime showTime;

    private ShowTimeBuilder() {
        this.showTime = new ShowTime();
        this.showTime.setCreatedAt(LocalDateTime.now());
        this.showTime.setModifiedAt(LocalDateTime.now());
    }

    public static ShowTimeBuilder builder() {
        return new ShowTimeBuilder();
    }

    public ShowTimeBuilder movie(Movie movie) {
        showTime.setMovie(movie);
        return this;
    }

    public ShowTimeBuilder format(Format format) {
        showTime.setFormat(format);
        return this;
    }

    public ShowTimeBuilder language(Language language) {
        showTime.setLanguage(language);
        return this;
    }

    public ShowTimeBuilder subtitleLanguage(Language subtitleLanguage) {
        showTime.setSubtitleLanguage(subtitleLanguage);
        return this;
    }

    public ShowTimeBuilder screen(Screen screen) {
        showTime.setScreen(screen);
        return this;
    }

    public ShowTimeBuilder startTime(LocalDateTime start) {
        showTime.setStartTime(start);
        return this;
    }

    public ShowTimeBuilder endTime(LocalDateTime end) {
        showTime.setEndTime(end);
        return this;
    }

    public ShowTimeBuilder price(Float price) {
        showTime.setPrice(price);
        return this;
    }

    public ShowTime build() {
        return showTime;
    }
}