package com.apollo.art.oeuvre.config;

import com.apollo.art.chat.service.FileHelper;
import com.apollo.art.oeuvre.controller.Controller;
import com.apollo.art.oeuvre.entity.AuteurEntity;
import com.apollo.art.oeuvre.entity.ChatEntity;
import com.apollo.art.response.ApiResponse;
import com.google.cloud.vertexai.api.GenerateContentResponse;
import com.google.cloud.vertexai.generativeai.preview.ChatSession;
import com.google.cloud.vertexai.generativeai.preview.ContentMaker;
import com.google.cloud.vertexai.generativeai.preview.GenerativeModel;
import com.google.cloud.vertexai.generativeai.preview.PartMaker;
import com.google.cloud.vertexai.generativeai.preview.ResponseHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

@RestController
@RequestMapping("/gemini-pro-vision")
public class GeminiProVisionController extends Controller {

    private final GenerativeModel generativeModel;

    public GeminiProVisionController(@Qualifier("geminiProVisionGenerativeModel") GenerativeModel generativeModel) {
        this.generativeModel = generativeModel;
    }

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<ChatEntity>> uploadBase64File(@Valid @RequestBody ChatEntity entity)
            throws IOException {

        if (entity.getPhoto() == null) {
            ChatSession chatSession = new ChatSession(generativeModel);
            GenerateContentResponse generateContentResponse = chatSession.sendMessage(entity.getQuestion());
            entity.setReponse(ResponseHandler.getText(generateContentResponse));
            entity.setDateBot(Timestamp.from(Instant.now()));
        } else {
            FileHelper fileHelper = new FileHelper();
            byte[] fileBytes = Base64.getDecoder().decode(entity.getPhoto());
            GenerateContentResponse generateContentResponse = this.generativeModel.generateContent(
                    ContentMaker.fromMultiModalData(
                            PartMaker.fromMimeTypeAndData("image/png", fileBytes),
                            entity.getQuestion()));
            entity.setReponse(ResponseHandler.getText(generateContentResponse));
            entity.setPhoto(fileHelper.upload(entity.getPhoto()));
            System.out.println(entity.getPhoto());

        }

        return createResponseEntity(entity, entity.getClass().getSimpleName() + " created successfully");
    }
}