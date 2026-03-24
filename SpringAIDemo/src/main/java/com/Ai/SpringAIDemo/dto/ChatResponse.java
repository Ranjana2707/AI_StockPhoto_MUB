package com.Ai.SpringAIDemo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for outgoing chat responses.
 * Used for POST /api/chat and GET /api/ask endpoints.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response body for chat operations")
public class ChatResponse {

    /**
     * The AI's response message
     */
    @Schema(description = "The AI's generated response message", 
            example = "The capital of France is Paris.")
    private String answer;

    /**
     * Timestamp when the response was generated
     */
    @Schema(description = "Timestamp when the response was generated", 
            example = "2024-01-15T10:30:00")
    private LocalDateTime timestamp;

    /**
     * The user ID this response belongs to
     */
    @Schema(description = "The user ID this response belongs to", 
            example = "user123")
    private String userId;
}
