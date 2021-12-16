package com.rudyah.demo1.api.response.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter @Setter
public class ApiResponse<T>{
    Boolean success;
    HttpStatus status;
    LocalDateTime timestamp;
    String message;
    T data;

    public ApiResponse(Boolean success, HttpStatus status, String message) {
        this.success = success;
        this.status = status;
        this.timestamp = LocalDateTime.now();
        this.message = message;
    }

    public ApiResponse(Boolean success, HttpStatus status, String message, T data) {
        this.success = success;
        this.status = status;
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.data = data;
    }
}
