package com.cinebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cinebook.model.Language;

public interface LanguagesRepository extends JpaRepository<Language, Integer> {

}
