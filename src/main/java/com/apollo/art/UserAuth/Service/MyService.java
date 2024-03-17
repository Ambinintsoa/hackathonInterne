package com.apollo.art.UserAuth.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MyService {

        private final HttpClient httpClient = HttpClient.newBuilder()
                        .version(HttpClient.Version.HTTP_2)
                        .build();

        public String postDataToPythonServer(String param1, String param2) throws IOException, InterruptedException {
                String pythonServerUrl = "http://192.168.88.20:5000/getScore";

                // Créer un objet représentant vos données
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonData = objectMapper.writeValueAsString(Map.of("image1", param1, "image2", param2));

                HttpRequest request = HttpRequest.newBuilder()
                                .POST(HttpRequest.BodyPublishers.ofString(jsonData))
                                .uri(URI.create(pythonServerUrl))
                                .header("Content-Type", "application/json")
                                .build();

                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                return response.body();
        }
}
