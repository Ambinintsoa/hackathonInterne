package com.apollo.art.oeuvre.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

import com.apollo.art.oeuvre.entity.PaletteEntity;
import com.apollo.art.oeuvre.entity.PaletteEntity;
import com.apollo.art.oeuvre.service.PaletteService;
import com.apollo.art.oeuvre.service.TypeService;
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
public class PaletteController extends Controller {

    private final PaletteService service;

    @GetMapping("/art/palettes")
    public ResponseEntity<ApiResponse<List<PaletteEntity>>> getAll() {
        try {
            List<PaletteEntity> entity = service.getAll();
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
    // List<CategoryEntity> palettes = categoryService.selectWithPagination(id,
    // limit);
    // return createResponseEntity(palettes, "Categories retrieved successfully");

    // } catch (Exception e) {
    // return ResponseEntity.status(HttpStatus.OK)
    // .body(new ApiResponse<>(null, new Status("error", e.getMessage()),
    // LocalDateTime.now()));
    // }
    // }

    @GetMapping("/art/palettes/{id}")
    public ResponseEntity<ApiResponse<PaletteEntity>> getById(@PathVariable Long id) {
        try {
            Optional<PaletteEntity> entity = service.getById(id);
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

    @PostMapping(value = "/art/palettes")
    public ResponseEntity<ApiResponse<PaletteEntity>> create(HttpServletRequest request,
            @Valid @RequestBody PaletteEntity entity) {
        try {
            PaletteEntity created = service.create(entity);
            return createResponseEntity(created, created.getClass().getSimpleName() + " created successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @PutMapping("/art/palettes")
    public ResponseEntity<ApiResponse<PaletteEntity>> update(HttpServletRequest request,
            @Valid @RequestBody PaletteEntity updated) {
        try {
            return createResponseEntity(service.update(updated.getId(), updated).get(),
                    updated.getClass().getSimpleName() + " updated successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @DeleteMapping("/art/palettes/{id}")
    public ResponseEntity<ApiResponse<PaletteEntity>> delete(HttpServletRequest request,
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
