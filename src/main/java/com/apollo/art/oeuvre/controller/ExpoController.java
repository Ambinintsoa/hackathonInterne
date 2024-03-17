package com.apollo.art.oeuvre.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

import com.apollo.art.oeuvre.entity.ExpoEntity;
import com.apollo.art.oeuvre.entity.ExpoEntity;
import com.apollo.art.oeuvre.service.ExpoService;
import com.apollo.art.response.ApiResponse;
import com.apollo.art.response.Status;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/apollo")
@AllArgsConstructor
public class ExpoController extends Controller {

    private final ExpoService service;

    @GetMapping("/art/expo")
    public ResponseEntity<ApiResponse<List<ExpoEntity>>> getAll() {
        try {
            List<ExpoEntity> entity = service.getAll();
            return createResponseEntity(entity, entity.getClass().getSimpleName() + "retrieved successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @GetMapping("/art/expo/prochain")
    public ResponseEntity<ApiResponse<List<ExpoEntity>>> getAllProchain() {
        try {
            List<ExpoEntity> entity = service.getProchainesExpositions();
            return createResponseEntity(entity, entity.getClass().getSimpleName() + "retrieved successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    // @GetMapping("/actu/categories/pagination")
    // public ResponseEntity<ApiResponse<Long>> getPagination(
    // @RequestParam(name = "limit", defaultValue = "5") int limit) {
    // try {

    // return createResponseEntity(categoryService.pagination(limit), "Categories
    // retrieved successfully");

    // } catch (Exception e) {
    // return ResponseEntity.status(HttpStatus.OK)
    // .body(new ApiResponse<>(null, new Status("error", e.getMessage()),
    // LocalDateTime.now()));
    // }
    // }

    // @GetMapping("/actu/pagination/categories")
    // public ResponseEntity<ApiResponse<List<CategoryEntity>>>
    // getAllCategoriesWithPagination(
    // @RequestParam(name = "offset") int id,
    // @RequestParam(name = "limit", defaultValue = "5") int limit) {
    // try {
    // List<CategoryEntity> expo = categoryService.selectWithPagination(id,
    // limit);
    // return createResponseEntity(expo, "Categories retrieved successfully");

    // } catch (Exception e) {
    // return ResponseEntity.status(HttpStatus.OK)
    // .body(new ApiResponse<>(null, new Status("error", e.getMessage()),
    // LocalDateTime.now()));
    // }
    // }

    @GetMapping("/art/expo/{id}")
    public ResponseEntity<ApiResponse<ExpoEntity>> getById(@PathVariable Long id) {
        try {
            Optional<ExpoEntity> entity = service.getById(id);
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

    @PostMapping(value = "/art/expo")
    public ResponseEntity<ApiResponse<ExpoEntity>> create(HttpServletRequest request,
            @Valid @RequestBody ExpoEntity entity) {
        try {
            ExpoEntity created = service.create(entity);
            return createResponseEntity(created, created.getClass().getSimpleName() + " created successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @PutMapping("/art/expo")
    public ResponseEntity<ApiResponse<ExpoEntity>> update(HttpServletRequest request,
            @Valid @RequestBody ExpoEntity updated) {
        try {
            return createResponseEntity(service.update(updated.getId(), updated).get(),
                    updated.getClass().getSimpleName() + " updated successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @DeleteMapping("/art/expo/{id}")
    public ResponseEntity<ApiResponse<ExpoEntity>> delete(HttpServletRequest request,
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
