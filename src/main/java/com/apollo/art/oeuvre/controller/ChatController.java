package com.apollo.art.oeuvre.controller;

import okhttp3.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apollo.art.UserAuth.Models.User;
import com.apollo.art.UserAuth.Service.RefreshTokenService;
import com.apollo.art.oeuvre.config.ChatControllerV2;
import com.apollo.art.oeuvre.config.GeminiConfiguration;
import com.apollo.art.oeuvre.config.GeminiProVisionController;
import com.apollo.art.oeuvre.entity.ChatEntity;
import com.apollo.art.oeuvre.entity.OeuvreEntity;
import com.apollo.art.oeuvre.service.ChatService;
import com.apollo.art.response.ApiResponse;
import com.apollo.art.response.Status;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.google.api.client.util.DateTime;
import com.google.auth.Credentials;

@RestController
@RequestMapping("/apollo")
@AllArgsConstructor
public class ChatController extends Controller {
    private final ChatService service;
    private final GeminiProVisionController geminipProVisionController;
    private final RefreshTokenService refreshTokenService;

    @GetMapping("/art/chats")
    public ResponseEntity<ApiResponse<List<ChatEntity>>> getAll(HttpServletRequest request) {
        try {
            User user = getUserByToken(refreshTokenService.splitToken(request.getHeader("Authorization"))).get();
            List<ChatEntity> entity = service.getByUserId(user.getId());
            return createResponseEntity(entity, entity.getClass().getSimpleName() + "retrieved successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @GetMapping("/art/chats/{id}")
    public ResponseEntity<ApiResponse<ChatEntity>> getById(@PathVariable Long id) {
        try {
            Optional<ChatEntity> entity = service.getById(id);
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

    @PostMapping(value = "/art/chats")
    public ResponseEntity<ApiResponse<ChatEntity>> create(HttpServletRequest request,
            @Valid @RequestBody ChatEntity entity) {
        try {
            entity.setDate(Timestamp.from(Instant.now()));
            geminipProVisionController.uploadBase64File(entity);
            entity.setUser(getUserByToken(refreshTokenService.splitToken(request.getHeader("Authorization"))).get());
            System.out.println(entity.getPhoto());
            ChatEntity created = service.create(entity);
            return createResponseEntity(created, created.getClass().getSimpleName() + " created successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @PutMapping("/art/chats")
    public ResponseEntity<ApiResponse<ChatEntity>> update(HttpServletRequest request,
            @Valid @RequestBody ChatEntity updated) {
        try {
            return createResponseEntity(service.update(updated.getId(), updated).get(),
                    updated.getClass().getSimpleName() + " updated successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @DeleteMapping("/art/chats/{id}")
    public ResponseEntity<ApiResponse<ChatEntity>> delete(HttpServletRequest request,
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

    // @PostMapping("/chat")
    // public String chatWithOpenAI(@RequestBody String content) {
    // OkHttpClient client = new OkHttpClient();

    // String json = "{\"model\": \"gpt-3.5-turbo\", \"messages\": [{\"role\":
    // \"user\", \"content\": \"" + content
    // + "\"}]}";

    // // Création de la requête HTTP POST vers l'API OpenAI
    // okhttp3.RequestBody body = okhttp3.RequestBody.create(json,
    // MediaType.parse("application/json"));
    // Request request = new Request.Builder()
    // .url("https://api.openai.com/v1/chat/completions")
    // .post(body)
    // .addHeader("Authorization", "Bearer
    // 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")
    // .addHeader("Content-Type", "application/json")
    // .build();

    // // Envoi de la requête et récupération de la réponse
    // try {
    // Response response = client.newCall(request).execute();

    // if (response.isSuccessful()) {
    // // ObjectMapper objectMapper = new ObjectMapper();
    // // JsonNode jsonNode = objectMapper.readTree(response.body().string());

    // // // Vérifier si le contenu de la réponse est présent
    // // if (jsonNode.has("choices") && jsonNode.get("choices").isArray()
    // // && jsonNode.get("choices").size() > 0) {
    // // JsonNode firstChoice = jsonNode.get("choices").get(0);
    // // if (firstChoice.has("message") && !firstChoice.get("message").isNull()
    // // && firstChoice.get("message").has("content")) {
    // // String assistantMessageContent =
    // // firstChoice.get("message").get("content").asText();
    // // System.out.println("Assistant's message: " + assistantMessageContent);

    // // }
    // // }
    // System.out.println(response.body().string());
    // return "response.body().string()";
    // } else {
    // return "Error: " + response.code() + " " + response.body().string();
    // }
    // } catch (IOException e) {
    // e.printStackTrace();
    // return "An error occurred: " + e.getMessage();
    // }
    // }

}
