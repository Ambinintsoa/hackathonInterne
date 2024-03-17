package com.apollo.art.oeuvre.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.apollo.art.UserAuth.Config.JwtService;
import com.apollo.art.UserAuth.Models.User;
import com.apollo.art.UserAuth.Service.AuthService;
import com.apollo.art.response.ApiResponse;
import com.apollo.art.response.Status;

public class Controller {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthService authService;

    public Optional<User> getUserByToken(String token) {
        String username = jwtService.extractUsername(token);
        Optional<User> user = authService.findByEmail(username);
        return user;

    }

    public <T> ResponseEntity<ApiResponse<T>> createResponseEntity(T data, String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setData(data);
        response.setStatus(new Status("ok", message));
        response.setTimestamp(LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

}
