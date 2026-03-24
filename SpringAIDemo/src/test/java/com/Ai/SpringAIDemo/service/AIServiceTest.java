package com.Ai.SpringAIDemo.service;

import com.Ai.SpringAIDemo.exception.AIChatException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for AIService.
 * Tests the chat functionality with mocking of the Gemini API.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("AIService Unit Tests")
class AIServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private AIService aiService;

    private static final String TEST_API_KEY = "test-api-key-123";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(aiService, "apiKey", TEST_API_KEY);
    }

    @Test
    @DisplayName("getResponse - Should return answer when valid question and API key provided")
    void getResponse_WithValidQuestion_ShouldReturnAnswer() {
        // Arrange
        String question = "What is Java?";
        String expectedAnswer = "Java is a programming language.";

        Map<String, Object> responseBody = createMockResponseBody(expectedAnswer);
        ResponseEntity<Map> responseEntity = new ResponseEntity<>(responseBody, HttpStatus.OK);

        when(restTemplate.exchange(
                contains("generateContent"),
                eq(org.springframework.http.HttpMethod.POST),
                any(),
                eq(Map.class))
        ).thenReturn(responseEntity);

        // Act
        String result = aiService.getResponse(question);

        // Assert
        assertEquals(expectedAnswer, result);
        verify(restTemplate).exchange(
                contains("generateContent"),
                eq(org.springframework.http.HttpMethod.POST),
                any(),
                eq(Map.class)
        );
    }

    @Test
    @DisplayName("getResponse - Should throw exception when question is null")
    void getResponse_WithNullQuestion_ShouldThrowException() {
        // Act & Assert
        AIChatException exception = assertThrows(
                AIChatException.class,
                () -> aiService.getResponse(null)
        );

        assertEquals("Question cannot be empty", exception.getMessage());
        verifyNoInteractions(restTemplate);
    }

    @Test
    @DisplayName("getResponse - Should throw exception when question is empty")
    void getResponse_WithEmptyQuestion_ShouldThrowException() {
        // Act & Assert
        AIChatException exception = assertThrows(
                AIChatException.class,
                () -> aiService.getResponse("   ")
        );

        assertEquals("Question cannot be empty", exception.getMessage());
        verifyNoInteractions(restTemplate);
    }

    @Test
    @DisplayName("getResponse - Should throw exception when API key is null")
    void getResponse_WithNullApiKey_ShouldThrowException() {
        // Arrange
        ReflectionTestUtils.setField(aiService, "apiKey", null);

        // Act & Assert
        AIChatException exception = assertThrows(
                AIChatException.class,
                () -> aiService.getResponse("Test question")
        );

        assertEquals("Gemini API key is not configured", exception.getMessage());
        verifyNoInteractions(restTemplate);
    }

    @Test
    @DisplayName("getResponse - Should throw exception when API key is empty")
    void getResponse_WithEmptyApiKey_ShouldThrowException() {
        // Arrange
        ReflectionTestUtils.setField(aiService, "apiKey", "   ");

        // Act & Assert
        AIChatException exception = assertThrows(
                AIChatException.class,
                () -> aiService.getResponse("Test question")
        );

        assertEquals("Gemini API key is not configured", exception.getMessage());
        verifyNoInteractions(restTemplate);
    }

    @Test
    @DisplayName("getResponse - Should throw exception when API call fails")
    void getResponse_WhenApiCallFails_ShouldThrowException() {
        // Arrange
        String question = "Test question";
        when(restTemplate.exchange(
                contains("generateContent"),
                eq(org.springframework.http.HttpMethod.POST),
                any(),
                eq(Map.class))
        ).thenThrow(new RestClientException("Connection refused"));

        // Act & Assert
        AIChatException exception = assertThrows(
                AIChatException.class,
                () -> aiService.getResponse(question)
        );

        assertTrue(exception.getMessage().contains("Failed to get response"));
        assertTrue(exception.getCause() instanceof RestClientException);
    }

    @Test
    @DisplayName("getResponse - Should throw exception when response body is null")
    void getResponse_WithNullResponseBody_ShouldThrowException() {
        // Arrange
        String question = "Test question";
        ResponseEntity<Map> responseEntity = new ResponseEntity<>(null, HttpStatus.OK);

        when(restTemplate.exchange(
                contains("generateContent"),
                eq(org.springframework.http.HttpMethod.POST),
                any(),
                eq(Map.class))
        ).thenReturn(responseEntity);

        // Act & Assert
        AIChatException exception = assertThrows(
                AIChatException.class,
                () -> aiService.getResponse(question)
        );

        assertEquals("Empty response from Gemini API", exception.getMessage());
    }

    @Test
    @DisplayName("getResponse - Should throw exception when response has no candidates")
    void getResponse_WithNoCandidates_ShouldThrowException() {
        // Arrange
        String question = "Test question";
        Map<String, Object> responseBody = Map.of("candidates", List.of());
        ResponseEntity<Map> responseEntity = new ResponseEntity<>(responseBody, HttpStatus.OK);

        when(restTemplate.exchange(
                contains("generateContent"),
                eq(org.springframework.http.HttpMethod.POST),
                any(),
                eq(Map.class))
        ).thenReturn(responseEntity);

        // Act & Assert
        AIChatException exception = assertThrows(
                AIChatException.class,
                () -> aiService.getResponse(question)
        );

        assertEquals("No response from Gemini", exception.getMessage());
    }

    @Test
    @DisplayName("getResponse - Should throw exception when response parsing fails")
    void getResponse_WithInvalidResponseFormat_ShouldThrowException() {
        // Arrange
        String question = "Test question";
        // Missing required fields in response
        Map<String, Object> responseBody = Map.of("candidates", List.of(
                Map.of("invalid", "structure")
        ));
        ResponseEntity<Map> responseEntity = new ResponseEntity<>(responseBody, HttpStatus.OK);

        when(restTemplate.exchange(
                contains("generateContent"),
                eq(org.springframework.http.HttpMethod.POST),
                any(),
                eq(Map.class))
        ).thenReturn(responseEntity);

        // Act & Assert
        AIChatException exception = assertThrows(
                AIChatException.class,
                () -> aiService.getResponse(question)
        );

        assertEquals("Error parsing Gemini response", exception.getMessage());
    }

    /**
     * Helper method to create a mock Gemini API response body
     */
    private Map<String, Object> createMockResponseBody(String answer) {
        return Map.of(
                "candidates", List.of(
                        Map.of(
                                "content", Map.of(
                                        "parts", List.of(
                                                Map.of("text", answer)
                                        )
                                )
                        )
                )
        );
    }
}
