package com.cinebook.service;

import com.cinebook.dto.ApiResponse;
import com.cinebook.dto.LoginRequest;
import com.cinebook.dto.RegisterRequest;
import com.cinebook.dto.UpgradeToTheatreOwnerRequest;
import com.cinebook.model.User;

public interface UserService {
    User registerUser(RegisterRequest request);
    String loginUser(LoginRequest request);
    ApiResponse<Void> upgradeToTheatreOwner(String email, UpgradeToTheatreOwnerRequest request);
}
