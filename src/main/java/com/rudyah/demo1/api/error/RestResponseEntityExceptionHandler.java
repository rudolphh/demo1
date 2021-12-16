package com.rudyah.demo1.api.error;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.rudyah.demo1.api.error.exceptions.EmailExistsException;
import com.rudyah.demo1.api.error.exceptions.StudentNotFoundException;
import com.rudyah.demo1.api.response.models.ApiResponse;
import com.rudyah.demo1.api.response.models.ValidationErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        final List<String> errors = new ArrayList<>();
        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        ValidationErrorResponse error =
                new ValidationErrorResponse(HttpStatus.BAD_REQUEST, "Validation Error", errors);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { EmailExistsException.class })
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {

        ApiResponse<Object> responseBody = new ApiResponse<>(
                false, HttpStatus.CONFLICT, ex.getMessage());

        //return new ResponseEntity<>(apiResponse, HttpStatus.CONFLICT);
        return handleExceptionInternal(ex, responseBody,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = { StudentNotFoundException.class })
    protected ResponseEntity<Object> handleNotFound(RuntimeException ex, WebRequest request) {

        ApiResponse<Object> responseBody = new ApiResponse<>(
                false, HttpStatus.NOT_FOUND, ex.getMessage());

        //return new ResponseEntity<>(apiResponse, HttpStatus.CONFLICT);
        return handleExceptionInternal(ex, responseBody,
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {

        InvalidFormatException invalidFormatException = (InvalidFormatException) ex
                .getCause();

        String fieldName = invalidFormatException.getPath().get(0).getFieldName();

        ValidationErrorResponse errorResponse =
                new ValidationErrorResponse(status, "Bad request" , fieldName);

        return this.handleExceptionInternal(ex, errorResponse, headers, status, request);
    }

}