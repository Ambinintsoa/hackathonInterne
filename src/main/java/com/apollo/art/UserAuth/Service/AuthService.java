package com.apollo.art.UserAuth.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.apollo.art.UserAuth.Config.JwtService;
import com.apollo.art.UserAuth.Enum.Role;
import com.apollo.art.UserAuth.Models.RefreshToken;
import com.apollo.art.UserAuth.Models.User;
import com.apollo.art.UserAuth.Repository.UserRepository;
import com.apollo.art.UserAuth.Request.AuthenticationRequest;
import com.apollo.art.UserAuth.Request.RefreshTokenRequest;
import com.apollo.art.UserAuth.Request.RegisterRequest;
import com.apollo.art.UserAuth.Response.AuthenticationResponse;
import com.apollo.art.Utils.Status;
import com.apollo.art.chat.model.JsonResponse;
import com.apollo.art.chat.service.FileHelper;
import jakarta.el.ELException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
        private final UserRepository repository;
        // private final CountryService countryService;
        private final PasswordEncoder passwordEncoder;
        private final JwtService jwtService;
        private final AuthenticationManager authenticationManager;
        private final RefreshTokenService refreshTokenService;

        public AuthenticationResponse register(RegisterRequest request) throws Exception {
                if (repository.findByEmail(request.getEmail()).isEmpty()) {
                        String pdp = null;
                        User user = User.builder().nom(request.getName())
                                        .genre(request.getGender())
                                        .dtn(request.getDtn())
                                        .profile(request.getProfil())
                                        .email(request.getEmail())
                                        .telephone(request.getTelephone())
                                        .password(passwordEncoder.encode(request.getPassword()))
                                        .roles(Role.USER) // role example
                                        .build();
                        if (user.getGenre() < 0 && user.getGenre() > 1) {
                                throw new Exception("Gender is not valid");
                        }
                        Date now = new Date(System.currentTimeMillis());
                        LocalDate localDate1 = now.toLocalDate();
                        LocalDate localDate2 = user.getDtn().toLocalDate();
                        Period period = Period.between(localDate2, localDate1);
                        if (period.getYears() < 18) {
                                throw new Exception("Age is not valid");
                        }
                        if (request.getPdp() != null) {
                                FileHelper file = new FileHelper();
                                pdp = file.upload(request.getPdp());
                                user.setPdp(pdp);
                        }
                        user = repository.save(user);
                        // user.setCountry(countryService.getCountryById(request.getIdcountry()).get());

                        return getAuthResponse(user);
                }
                throw new Exception("email is already present");

        }

        public AuthenticationResponse authenticate(AuthenticationRequest request) {
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
                User user = repository.findByEmail(request.getEmail()).orElseThrow();

                return getAuthResponse(user);
        }

        public Optional<User> getUser(long request) {
                System.out.println(request);
                return repository.findById(request);

        }

        public Optional<User> findByEmail(String request) {
                return repository.findByEmail(request);

        }

        public AuthenticationResponse authenticateAdmin(AuthenticationRequest request) throws Exception {
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
                User user = repository.findByEmail(request.getEmail()).orElseThrow();
                if (user.getRoles() == Role.ADMIN) {
                        return getAuthResponseAdmin(user);
                } else {
                        throw new ELException("Cannot access");
                }

        }

        public AuthenticationResponse getAuthResponse(User user) {

                String jwtToken = jwtService.generateToken(user);
                RefreshToken tokenRefresh = refreshTokenService.generateRefreshToken(user.getEmail());
                return AuthenticationResponse.builder().token(jwtToken).user(user)
                                .refresh_token(tokenRefresh.getToken())
                                .build();
        }

        public AuthenticationResponse getAuthResponseAdmin(User user) {
                String jwtToken = jwtService.generateTokenAdmin(user);
                RefreshToken tokenRefresh = refreshTokenService.generateRefreshToken(user.getEmail());
                return AuthenticationResponse.builder().token(jwtToken).user(user)
                                .refresh_token(tokenRefresh.getToken())
                                .build();
        }

        public Status useRefreshToken(RefreshTokenRequest refreshTokenRequest) {
                User user = refreshTokenService.findByToken(refreshTokenRequest.getRefresh_token()).map(
                                refreshTokenService::verifyExpiration).map(RefreshToken::getUser).get();
                String accesToken = jwtService.generateToken(user);
                var tokenRefresh = refreshTokenService.generateRefreshToken(user.getEmail());

                return Status.builder()
                                .data(AuthenticationResponse.builder().token(accesToken).user(user)
                                                .refresh_token(tokenRefresh.getToken())
                                                .build())
                                .status("good").details("token removed").build();
        }

        public Status useRefreshTokenAdmin(RefreshTokenRequest refreshTokenRequest) {

                User user = refreshTokenService.findByToken(refreshTokenRequest.getRefresh_token()).map(
                                refreshTokenService::verifyExpiration).map(RefreshToken::getUser).get();

                String accesToken = jwtService.generateTokenAdmin(user);
                var tokenRefresh = refreshTokenService.generateRefreshToken(user.getEmail());

                return Status.builder()
                                .data(AuthenticationResponse.builder().token(accesToken).user(user)
                                                .refresh_token(tokenRefresh.getToken())
                                                .build())
                                .status("good").details("token removed").build();
        }

        public Boolean chekcIfAlreadyExist(String email) {
                Boolean exist = false;
                Optional<User> user = repository.findByEmail(email);
                if (user.isPresent())
                        exist = true;

                return exist;
        }

        public Optional<User> findById(Long id) {

                return repository.findById(id);
        }

}