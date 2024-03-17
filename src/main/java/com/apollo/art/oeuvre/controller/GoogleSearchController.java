package com.apollo.art.oeuvre.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apollo.art.oeuvre.service.GoogleSearchService;

import java.util.Map;

@RestController
public class GoogleSearchController {

    @Autowired
    private GoogleSearchService googleSearchService;

    // @GetMapping("/searchs")
    // public Map<String, String> searchGoogle(@RequestParam String query) {
    // return googleSearchService.search(query);
    // }

    @GetMapping("/searchs")
    public void searchGoogle(@RequestParam String query) {
        Map<String, String> map = googleSearchService.search(query);
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                // Faites ce que vous voulez avec la clé et la valeur
                System.out.println(key + ": " + value);
            }
        } else {
            // Gérer le cas où aucune donnée n'a été retournée
            System.out.println("Aucun résultat trouvé pour la requête : " + query);
        }
    }
}
