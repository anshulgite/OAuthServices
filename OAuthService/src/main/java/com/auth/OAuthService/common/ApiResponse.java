package com.auth.OAuthService.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private String message;
    private boolean success;
    private LocalDateTime timestamp;
    private T data;
    private int statusCode;
    private String path;

    // Success response with data
    public static <T> ApiResponse<T> success(T data, String message) {
        return ApiResponse.<T>builder()
                .message(message)
                .success(true)
                .timestamp(LocalDateTime.now())
                .data(data)
                .statusCode(HttpStatus.OK.value())
                .build();
    }

    // Success response with data and custom status
    public static <T> ApiResponse<T> success(T data, String message, HttpStatus status) {
        return ApiResponse.<T>builder()
                .message(message)
                .success(true)
                .timestamp(LocalDateTime.now())
                .data(data)
                .statusCode(status.value())
                .build();
    }

    // Success response without data
    public static <T> ApiResponse<T> success(String message) {
        return ApiResponse.<T>builder()
                .message(message)
                .success(true)
                .timestamp(LocalDateTime.now())
                .statusCode(HttpStatus.OK.value())
                .build();
    }

    // Error response
    public static <T> ApiResponse<T> error(String message, HttpStatus status) {
        return ApiResponse.<T>builder()
                .message(message)
                .success(false)
                .timestamp(LocalDateTime.now())
                .statusCode(status.value())
                .build();
    }

    // Error response with data
    public static <T> ApiResponse<T> error(String message, HttpStatus status, T data) {
        return ApiResponse.<T>builder()
                .message(message)
                .success(false)
                .timestamp(LocalDateTime.now())
                .data(data)
                .statusCode(status.value())
                .build();
    }

    // Set path for better error tracking
    public ApiResponse<T> withPath(String path) {
        this.path = path;
        return this;
    }
}
