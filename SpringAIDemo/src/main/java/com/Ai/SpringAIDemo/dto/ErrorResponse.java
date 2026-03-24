package com.Ai.SpringAIDemo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Standard error response format for the API.
 * Used by GlobalExceptionHandler for consistent error responses.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Error response for API errors")
public class ErrorResponse {

    /**
     * HTTP status code
     */
    @Schema(description = "HTTP status code", example = "400")
    private int status;

    /**
     * Error message
     */
    @Schema(description = "Error message", example = "Bad Request")
    private String message;

    /**
     * Detailed error description
     */
    @Schema(description = "Detailed error description", 
            example = "Question parameter is required")
    private String details;

    /**
     * Timestamp when the error occurred
     */
    @Schema(description = "Timestamp when the error occurred", 
            example = "2024-01-15T10:30:00")
    private LocalDateTime timestamp;
}
