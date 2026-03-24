package com.Ai.SpringAIDemo.controller;

import com.Ai.SpringAIDemo.dto.ChatRequest;
import com.Ai.SpringAIDemo.exception.AIChatException;
import com.Ai.SpringAIDemo.exception.GlobalExceptionHandler;
import com.Ai.SpringAIDemo.service.AIService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for ChatController.
 * Tests all REST endpoints using MockMvc.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("ChatController Unit Tests")
class ChatControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private AIService aiService;

    @InjectMocks
    private ChatController chatController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(chatController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }

    @Test
    @DisplayName("GET /api/ask - Should return OK with answer when question is provided")
    void askQuestion_WithValidQuestion_ShouldReturnOk() throws Exception {
        // Arrange
        String question = "What is Spring AI?";
        String expectedAnswer = "Spring AI is a framework for building AI applications.";
        when(aiService.getResponse(question)).thenReturn(expectedAnswer);

        // Act & Assert
        mockMvc.perform(get("/api/ask")
                        .param("question", question))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.answer").value(expectedAnswer))
                .andExpect(jsonPath("$.userId").value("default"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("GET /api/ask - Should return 400 when question is empty")
    void askQuestion_WithEmptyQuestion_ShouldReturnBadRequest() throws Exception {
        // Arrange
        String question = "";
        when(aiService.getResponse(question))
                .thenThrow(new AIChatException("Question parameter is required"));

        // Act & Assert
        mockMvc.perform(get("/api/ask")
                        .param("question", question))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /api/ask - Should return 400 when question is missing")
    void askQuestion_WithMissingQuestion_ShouldReturnBadRequest() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/ask"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/chat - Should return OK with answer when valid request")
    void chat_WithValidRequest_ShouldReturnOk() throws Exception {
        // Arrange
        ChatRequest request = ChatRequest.builder()
                .question("Tell me about Java")
                .userId("user123")
                .build();
        String expectedAnswer = "Java is a high-level programming language.";
        when(aiService.getResponse(request.getQuestion())).thenReturn(expectedAnswer);

        // Act & Assert
        mockMvc.perform(post("/api/chat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.answer").value(expectedAnswer))
                .andExpect(jsonPath("$.userId").value("user123"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("POST /api/chat - Should use default userId when not provided")
    void chat_WithoutUserId_ShouldUseDefaultUserId() throws Exception {
        // Arrange
        ChatRequest request = ChatRequest.builder()
                .question("Hello")
                .build();
        String expectedAnswer = "Hello! How can I help you?";
        when(aiService.getResponse(request.getQuestion())).thenReturn(expectedAnswer);

        // Act & Assert
        mockMvc.perform(post("/api/chat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.answer").value(expectedAnswer))
                .andExpect(jsonPath("$.userId").value("default"));
    }

    @Test
    @DisplayName("POST /api/chat - Should return 400 when question is blank")
    void chat_WithBlankQuestion_ShouldReturnBadRequest() throws Exception {
        // Arrange
        ChatRequest request = ChatRequest.builder()
                .question("   ")
                .userId("user123")
                .build();

        // Act & Assert
        mockMvc.perform(post("/api/chat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/chat - Should return 500 when AI service throws exception")
    void chat_WhenAIServiceThrowsException_ShouldReturnInternalServerError() throws Exception {
        // Arrange
        ChatRequest request = ChatRequest.builder()
                .question("Test question")
                .userId("user123")
                .build();
        when(aiService.getResponse(anyString()))
                .thenThrow(new AIChatException("Gemini API Error", "API_ERROR"));

        // Act & Assert
        mockMvc.perform(post("/api/chat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.errorCode").value("AI_CHAT_ERROR"));
    }

    @Test
    @DisplayName("GET /api/health - Should return OK with status message")
    void health_ShouldReturnOk() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/health"))
                .andExpect(status().isOk())
                .andExpect(content().string("AI Chat Service is running with Gemini API"));
    }
}
