package com.apollo.art.oeuvre.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

import com.apollo.art.UserAuth.Models.User;
import com.apollo.art.UserAuth.Service.AuthService;
import com.apollo.art.UserAuth.Service.RefreshTokenService;
import com.apollo.art.oeuvre.entity.OeuvreEntity;
import com.apollo.art.oeuvre.entity.ReactionEntity;
import com.apollo.art.oeuvre.service.OeuvreService;
import com.apollo.art.oeuvre.service.ReactionService;
import com.apollo.art.response.ApiResponse;
import com.apollo.art.response.Status;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/apollo")
@AllArgsConstructor
public class OeuvreController extends Controller {

    private final OeuvreService service;
    private final AuthService authService;
    private final ReactionService reactionservice;
    private final RefreshTokenService refreshTokenService;

    // @GetMapping("/art/oeuvres")
    // public ResponseEntity<ApiResponse<List<OeuvreEntity>>> getAll() {
    // try {
    // List<OeuvreEntity> entity = service.getAll();
    // return createResponseEntity(entity, entity.getClass().getSimpleName() +
    // "retrieved successfully");

    // } catch (Exception e) {
    // return ResponseEntity.status(HttpStatus.OK)
    // .body(new ApiResponse<>(null, new Status("error", e.getMessage()),
    // LocalDateTime.now()));
    // }
    // }

    @GetMapping("/art/oeuvres/pagination")
    public ResponseEntity<ApiResponse<Long>> getPagination(
            @RequestParam(name = "limit", defaultValue = "5") int limit) {
        try {

            return createResponseEntity(service.paginationByStatus(limit, 1), " retrieved successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()),
                            LocalDateTime.now()));
        }
    }

    @GetMapping("/art/oeuvresNonValide/pagination")
    public ResponseEntity<ApiResponse<Long>> getNonValidePagination(
            @RequestParam(name = "limit", defaultValue = "5") int limit) {
        try {

            return createResponseEntity(service.paginationByStatus(limit, 0), " retrieved successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()),
                            LocalDateTime.now()));
        }
    }

    @GetMapping("/art/oeuvres")
    public ResponseEntity<ApiResponse<List<OeuvreEntity>>> getAllPaginer(
            @RequestParam(name = "offset", defaultValue = "0") int id,
            @RequestParam(name = "limit", defaultValue = "5") int limit) {
        try {
            List<OeuvreEntity> oeuvres = service.selectWithPagination(id,
                    limit);
            return createResponseEntity(oeuvres, "oeuvres retrieved successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()),
                            LocalDateTime.now()));
        }
    }

    @GetMapping("/art/oeuvres/nonValide")
    public ResponseEntity<ApiResponse<List<OeuvreEntity>>> getAllNotValidPaginer(
            @RequestParam(name = "offset", defaultValue = "0") int id,
            @RequestParam(name = "limit", defaultValue = "5") int limit) {
        try {
            List<OeuvreEntity> oeuvres = service.selectNotValidWithPagination(id,
                    limit);
            return createResponseEntity(oeuvres, "oeuvres retrieved successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()),
                            LocalDateTime.now()));
        }
    }

    @GetMapping("/art/oeuvres/user")
    public ResponseEntity<ApiResponse<List<OeuvreEntity>>> getMyPublication(HttpServletRequest request,
            @RequestParam(name = "offset", defaultValue = "0") int id,
            @RequestParam(name = "limit", defaultValue = "5") int limit) {
        try {
            User user = getUserByToken(refreshTokenService.splitToken(request.getHeader("Authorization"))).get();
            List<OeuvreEntity> oeuvres = service.selectMyPublication(id,
                    limit, user);
            return createResponseEntity(oeuvres, "oeuvres retrieved successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()),
                            LocalDateTime.now()));
        }
    }

    @GetMapping("/art/oeuvres/user/{id}")
    public ResponseEntity<ApiResponse<List<OeuvreEntity>>> getOwnPublication(@PathVariable Long id,
            @RequestParam(name = "offset", defaultValue = "0") int offset,
            @RequestParam(name = "limit", defaultValue = "5") int limit) {
        try {
            User user = authService.getUser(id).get();
            List<OeuvreEntity> oeuvres = service.selectMyPublication(offset,
                    limit, user);
            return createResponseEntity(oeuvres, "oeuvres retrieved successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()),
                            LocalDateTime.now()));
        }
    }

    @GetMapping("/art/oeuvres/{id}")
    public ResponseEntity<ApiResponse<OeuvreEntity>> getById(@PathVariable Long id) {
        try {
            Optional<OeuvreEntity> entity = service.getById(id);
            return entity
                    .map(c -> createResponseEntity(c,
                            entity.getClass().getSimpleName() + " retrieved successfully for this id"))
                    .orElseGet(
                            () -> ResponseEntity.status(HttpStatus.OK)
                                    .body(new ApiResponse<>(null, new Status("error", "NOT FOUND"),
                                            LocalDateTime.now())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @PostMapping(value = "/art/oeuvres")
    public ResponseEntity<ApiResponse<OeuvreEntity>> create(HttpServletRequest request,
            @Valid @RequestBody OeuvreEntity entity) {
        try {
            entity.setDatepublication(new Date(System.currentTimeMillis()));
            entity.setUser(getUserByToken(refreshTokenService.splitToken(request.getHeader("Authorization"))).get());
            OeuvreEntity created = service.create(entity);
            return createResponseEntity(created, created.getClass().getSimpleName() + " created successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @PostMapping(value = "/art/oeuvres/reagir")
    public ResponseEntity<ApiResponse<ReactionEntity>> create(HttpServletRequest request,
            @Valid @RequestBody ReactionEntity entity) {
        try {
            entity.setUser(getUserByToken(refreshTokenService.splitToken(request.getHeader("Authorization"))).get());

            ReactionEntity created = reactionservice.create(entity);
            return createResponseEntity(created, created.getClass().getSimpleName() + " created successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @PutMapping("/art/oeuvres")
    public ResponseEntity<ApiResponse<OeuvreEntity>> update(HttpServletRequest request,
            @Valid @RequestBody OeuvreEntity updated) {
        try {
            return createResponseEntity(service.update(updated.getId(), updated).get(),
                    updated.getClass().getSimpleName() + " updated successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @PutMapping("/art/oeuvres/validation")
    public ResponseEntity<ApiResponse<OeuvreEntity>> updateValidity(HttpServletRequest request,
            @Valid @RequestBody OeuvreEntity updated) {
        try {
            updated.setStatus(1);
            return createResponseEntity(service.update(updated.getId(), updated).get(),
                    updated.getClass().getSimpleName() + " updated successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @DeleteMapping("/art/oeuvres/{id}")
    public ResponseEntity<ApiResponse<OeuvreEntity>> delete(HttpServletRequest request,
            @PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("ok", "delete successfully"), LocalDateTime.now()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

}
