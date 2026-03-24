package com.Ai.SpringAIDemo.service;

import com.Ai.SpringAIDemo.exception.AIChatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * Service for direct Gemini API integration using RestTemplate.
 */
@Service
public class AIService {

    private static final Logger logger = LoggerFactory.getLogger(AIService.class);

    // ✅ Updated working Gemini model
    private static final String GEMINI_API_URL =
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent";

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${gemini.api.key}")
    private String apiKey;

    /**
     * Send a question to Gemini API and get the response.
     */
    public String getResponse(String question) {

        if (question == null || question.trim().isEmpty()) {
            throw new AIChatException("Question cannot be empty");
        }

        if (apiKey == null || apiKey.trim().isEmpty()) {
            throw new AIChatException("Gemini API key is not configured");
        }

        try {
            String url = GEMINI_API_URL + "?key=" + apiKey;

            // ✅ Clean request body
            Map<String, Object> requestBody = Map.of(
                    "contents", List.of(
                            Map.of(
                                    "parts", List.of(
                                            Map.of("text", question)
                                    )
                            )
                    )
            );

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> request =
                    new HttpEntity<>(requestBody, headers);

            ResponseEntity<Map> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    request,
                    Map.class
            );

            return extractResponse(response.getBody());

        } catch (Exception e) {
            logger.error("Gemini API Error", e);
            throw new AIChatException("Failed to get response: " + e.getMessage(), e);
        }
    }

    /**
     * Extract response text safely
     */
    private String extractResponse(Map<String, Object> body) {

        if (body == null) {
            throw new AIChatException("Empty response from Gemini API");
        }

        try {
            List<Map<String, Object>> candidates =
                    (List<Map<String, Object>>) body.get("candidates");

            if (candidates == null || candidates.isEmpty()) {
                throw new AIChatException("No response from Gemini");
            }

            Map<String, Object> content =
                    (Map<String, Object>) candidates.get(0).get("content");

            List<Map<String, Object>> parts =
                    (List<Map<String, Object>>) content.get("parts");

            return parts.get(0).get("text").toString();

        } catch (Exception e) {
            throw new AIChatException("Error parsing Gemini response", e);
        }
    }
}