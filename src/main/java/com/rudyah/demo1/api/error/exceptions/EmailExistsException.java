package com.rudyah.demo1.api.error.exceptions;

public class EmailExistsException extends RuntimeException{
    public EmailExistsException(String errorMessage){
        super(errorMessage);
    }
}
