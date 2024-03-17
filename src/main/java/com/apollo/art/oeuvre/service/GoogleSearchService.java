package com.apollo.art.oeuvre.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Service
public class GoogleSearchService {

    private static final String GOOGLE_SEARCH_URL = "https://api.google.com/search";

    public Map<String, String> search(String query) {
        Map<String, String> parameter = new HashMap<>();
        parameter.put("engine", "google_images");
        parameter.put("q", query);
        parameter.put("location", "Austin, TX, Texas, United States");
        parameter.put("key", "AIzaSyDM0bg2ZWFEpx70GiscKNvPFzSx_VT9sv8"); // Remplacez YOUR_API_KEY par votre clé API
                                                                         // Google valide

        RestTemplate restTemplate = new RestTemplate();
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(GOOGLE_SEARCH_URL);
            parameter.forEach(builder::queryParam);
            URI uri = builder.build().toUri();
            return restTemplate.getForObject(uri, Map.class);
        } catch (Exception ex) {
            // Gérer l'exception
            ex.printStackTrace();
            return null;
        }
    }
}
