package com.rudyah.demo1.api.error.exceptions;

public class StudentNotFoundException extends RuntimeException{
    public StudentNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
