package com.cinebuzz.apigateway.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
public class UIController {

    private static final Logger logger = LoggerFactory.getLogger(UIController.class);

    private final RestTemplate restTemplate;

    @Value("${cinebuzz.api.base-url}")  // Inject base API URL from properties
    private String baseApiUrl;

    public UIController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/movies")
    public String getMovies(Model model) {
        String apiUrl = baseApiUrl + "/movies";
        logger.info("Fetching movies from API: {}", apiUrl);

        List<Map<String, Object>> movies = fetchApiData(apiUrl);
        model.addAttribute("movies", movies);

        return "movie-list";  // Thymeleaf template
    }

    @GetMapping("/theatres")
    public String getTheatres(Model model) {
        String apiUrl = baseApiUrl + "/theatres";
        logger.info("Fetching theatres from API: {}", apiUrl);

        List<Map<String, Object>> theatres = fetchApiData(apiUrl);
        model.addAttribute("theatres", theatres);

        return "theatre-list";
    }

    @GetMapping("/tickets")
    public String getTickets(Model model) {
        String apiUrl = baseApiUrl + "/tickets";
        logger.info("Fetching tickets from API: {}", apiUrl);

        List<Map<String, Object>> tickets = fetchApiData(apiUrl);
        model.addAttribute("tickets", tickets);

        return "ticket-list";
    }

    private List<Map<String, Object>> fetchApiData(String url) {
        try {
            ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);
            return response.getBody() != null ? response.getBody() : Collections.emptyList();
        } catch (Exception e) {
            logger.error("Error fetching data from API: {}", url, e);
            return Collections.emptyList(); // Return empty list to prevent null pointer exceptions
        }
    }
}
