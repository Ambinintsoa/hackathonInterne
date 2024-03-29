package com.apollo.art.UserAuth.Auth;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apollo.art.UserAuth.Models.User;
import com.apollo.art.UserAuth.Request.AuthenticationRequest;
import com.apollo.art.UserAuth.Request.RefreshTokenRequest;
import com.apollo.art.UserAuth.Request.RegisterRequest;
import com.apollo.art.UserAuth.Response.AuthenticationResponse;
import com.apollo.art.UserAuth.Response.CheckStatusResponse;
import com.apollo.art.UserAuth.Service.AuthService;
import com.apollo.art.UserAuth.Service.CheckStatusService;
import com.apollo.art.UserAuth.Service.RefreshTokenService;
import com.apollo.art.Utils.Status;
import com.apollo.art.response.ApiResponse;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;
    private final RefreshTokenService refreshTokenService;
    private final CheckStatusService checkStatusService;

    @PostMapping("/register")
    public ResponseEntity<Object> register(
            @RequestBody RegisterRequest request) {
        try {
            return ResponseEntity.ok(service.register(request));
        } catch (Exception e) {
            System.err.println("ok");
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new com.apollo.art.response.Status("error", e.getMessage()),
                            LocalDateTime.now()));
        }

    }

    @PostMapping("/check")
    public ResponseEntity<Object> checkEmailAvailable(
            @PathParam(value = "email") String email) {
        return ResponseEntity.ok(!service.chekcIfAlreadyExist(email));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/checkTokenStatus")
    public ResponseEntity<Object> checkStatus(
            @RequestBody CheckStatusResponse request) {
        ///
        return ResponseEntity.ok(checkStatusService.checkAll(request));
    }

    @DeleteMapping("/refreshToken")
    public ResponseEntity<Object> removeRefreshToken(
            @RequestBody RefreshTokenRequest request) {
        refreshTokenService.removeRefreshToken(request.getRefresh_token());
        return ResponseEntity.ok(Status.builder().status("good").details("refresh token removed").build());
    }

    @PostMapping("/refresh_token")
    public ResponseEntity<Object> refreshToken(
            @RequestBody RefreshTokenRequest request) {
        ///
        try {
            return ResponseEntity.ok(service.useRefreshToken(request));
        } catch (Exception e) {
            return ResponseEntity.ok(Status.builder().status("error").details(e.getMessage()).build());
        }

    }

    @PostMapping("/refresh_token_admin")
    public ResponseEntity<Object> refreshTokenAdmin(
            @RequestBody RefreshTokenRequest request) {
        try {
            return ResponseEntity.ok(service.useRefreshTokenAdmin(request));
        } catch (Exception e) {
            return ResponseEntity.ok(Status.builder().status("error").details(e.getMessage()).build());
        }

    }

}
