package com.Ai.SpringAIDemo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for incoming chat requests.
 * Used for POST /api/chat endpoint.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request body for chat operations")
public class ChatRequest {

    /**
     * The user's question/message
     */
    @NotBlank(message = "Question cannot be blank")
    @Schema(description = "The question or message to send to the AI assistant", 
            example = "What is the capital of France?", requiredMode = Schema.RequiredMode.REQUIRED)
    private String question;

    /**
     * Unique identifier for the user session
     * Used for maintaining chat history per session
     */
    @Schema(description = "Unique identifier for the user session", 
            example = "user123", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String userId;
}
