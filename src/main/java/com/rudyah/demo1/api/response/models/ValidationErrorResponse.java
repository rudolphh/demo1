package com.rudyah.demo1.api.response.models;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ValidationErrorResponse {

    private HttpStatus httpStatus;

    private LocalDateTime timestamp;

    private String message;

    private List<String> errors;

    public ValidationErrorResponse(HttpStatus httpStatus, String message, List<String> errors) {
        this.httpStatus = httpStatus;
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.errors = errors;
    }

    public ValidationErrorResponse(HttpStatus httpStatus, String message, String errors) {
        this.httpStatus = httpStatus;
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.errors = List.of(errors);
    }

}