package com.cinebook.dto;

import com.cinebook.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface ResponseFactory {
    <T> ResponseEntity<ApiResponse<T>> success(T data, String message);

    <T> ResponseEntity<ApiResponse<T>> failure(String message, HttpStatus status);
}