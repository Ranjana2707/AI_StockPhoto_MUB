package com.Ai.SpringAIDemo.controller;

import com.Ai.SpringAIDemo.dto.ChatRequest;
import com.Ai.SpringAIDemo.dto.ChatResponse;
import com.Ai.SpringAIDemo.exception.AIChatException;
import com.Ai.SpringAIDemo.service.AIService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * REST Controller for AI Chat operations.
 * Provides endpoints for chatting with the AI assistant using Gemini API.
 */
@RestController
@RequestMapping("/api")
@Tag(name = "AI Chat", description = "Endpoints for interacting with the AI Chat Assistant powered by Gemini API")
public class ChatController {

    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);
    
    private final AIService aiService;

    public ChatController(AIService aiService) {
        this.aiService = aiService;
    }

    /**
     * GET endpoint for simple question answering.
     * 
     * @param question The user's question
     * @return ChatResponse with the AI's answer
     */
    @Operation(
            summary = "Ask a question (GET)",
            description = "Simple GET endpoint for asking questions to the AI assistant. " +
                         "Returns the AI's response as a chat message.",
            tags = {"AI Chat"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully received answer from AI",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ChatResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request - Question parameter is missing or empty",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error - AI service failed",
                    content = @Content(mediaType = "application/json")
            )
    })
    @GetMapping("/ask")
    public ResponseEntity<ChatResponse> askQuestion(
            @Parameter(description = "The question to ask the AI assistant", required = true)
            @RequestParam String question) {
        
        logger.info("GET /api/ask - Question: {}", question);
        
        if (question == null || question.trim().isEmpty()) {
            throw new AIChatException("Question parameter is required");
        }
        
        String answer = aiService.getResponse(question);
        
        ChatResponse response = ChatResponse.builder()
                .answer(answer)
                .timestamp(LocalDateTime.now())
                .userId("default")
                .build();
        
        return ResponseEntity.ok(response);
    }

    /**
     * POST endpoint for structured chat requests.
     * 
     * @param request The chat request containing question
     * @return ChatResponse with the AI's answer
     */
    @Operation(
            summary = "Send a chat message (POST)",
            description = "Structured POST endpoint for sending chat messages to the AI assistant. " +
                         "Supports custom user IDs for session management.",
            tags = {"AI Chat"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully received answer from AI",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ChatResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request - Invalid request body or missing question",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error - AI service failed",
                    content = @Content(mediaType = "application/json")
            )
    })
    @PostMapping("/chat")
    public ResponseEntity<ChatResponse> chat(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Chat request containing question and optional user ID",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ChatRequest.class))
            )
            @Valid @RequestBody ChatRequest request) {
        
        logger.info("POST /api/chat - Question: {}", request.getQuestion());
        
        String answer = aiService.getResponse(request.getQuestion());
        
        ChatResponse response = ChatResponse.builder()
                .answer(answer)
                .timestamp(LocalDateTime.now())
                .userId(request.getUserId() != null ? request.getUserId() : "default")
                .build();
        
        return ResponseEntity.ok(response);
    }

    /**
     * Health check endpoint.
     * 
     * @return Status message
     */
    @Operation(
            summary = "Health check",
            description = "Check if the AI Chat service is running properly",
            tags = {"AI Chat"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Service is healthy and running"
            )
    })
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("AI Chat Service is running with Gemini API");
    }
}
