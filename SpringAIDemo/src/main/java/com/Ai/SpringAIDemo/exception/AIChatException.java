package com.Ai.SpringAIDemo.exception;

/**
 * Custom exception class for AI Chat application.
 * Used for handling AI-related errors and validation errors.
 */
public class AIChatException extends RuntimeException {

    private final String errorCode;

    public AIChatException(String message) {
        super(message);
        this.errorCode = "AI_CHAT_ERROR";
    }

    public AIChatException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public AIChatException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "AI_CHAT_ERROR";
    }

    public AIChatException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
