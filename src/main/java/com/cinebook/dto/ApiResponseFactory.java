package com.cinebook.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ApiResponseFactory implements ResponseFactory {

    @Override
    public <T> ResponseEntity<ApiResponse<T>> success(T data, String message) {
        return ResponseEntity.ok(new ApiResponse<>("success", data, message));
    }

    @Override
    public <T> ResponseEntity<ApiResponse<T>> failure(String message, HttpStatus status) {
        return ResponseEntity
                .status(status)
                .body(new ApiResponse<>("failure", null, message));
    }
}

