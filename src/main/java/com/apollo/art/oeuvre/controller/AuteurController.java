package com.apollo.art.oeuvre.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

import com.apollo.art.UserAuth.Service.MyService;
import com.apollo.art.oeuvre.entity.AuteurEntity;
import com.apollo.art.oeuvre.entity.Photo;
import com.apollo.art.oeuvre.service.AuteurService;
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
public class AuteurController extends Controller {

    private final AuteurService service;

    @GetMapping("/art/auteurs")
    public ResponseEntity<ApiResponse<List<AuteurEntity>>> getAll() {
        try {
            List<AuteurEntity> entity = service.getAll();
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
    // List<CategoryEntity> auteurs = categoryService.selectWithPagination(id,
    // limit);
    // return createResponseEntity(auteurs, "Categories retrieved successfully");

    // } catch (Exception e) {
    // return ResponseEntity.status(HttpStatus.OK)
    // .body(new ApiResponse<>(null, new Status("error", e.getMessage()),
    // LocalDateTime.now()));
    // }
    // }

    @GetMapping("/art/auteurs/{id}")
    public ResponseEntity<ApiResponse<AuteurEntity>> getById(@PathVariable Long id) {
        try {
            Optional<AuteurEntity> entity = service.getById(id);
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

    @GetMapping("/art/auteur")
    public void get(@Valid @RequestBody Photo photo) throws Exception {
        MyService service = new MyService();
        service.getDataFromPythonServer(photo.getImage1(), photo.getImage2());
    }

    @PostMapping(value = "/art/auteurs")
    public ResponseEntity<ApiResponse<AuteurEntity>> create(HttpServletRequest request,
            @Valid @RequestBody AuteurEntity entity) {
        try {
            AuteurEntity created = service.create(entity);
            return createResponseEntity(created, created.getClass().getSimpleName() + " created successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @PutMapping("/art/auteurs")
    public ResponseEntity<ApiResponse<AuteurEntity>> update(HttpServletRequest request,
            @Valid @RequestBody AuteurEntity updated) {
        try {
            return createResponseEntity(service.update(updated.getId(), updated).get(),
                    updated.getClass().getSimpleName() + " updated successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @DeleteMapping("/art/auteurs/{id}")
    public ResponseEntity<ApiResponse<AuteurEntity>> delete(HttpServletRequest request,
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
