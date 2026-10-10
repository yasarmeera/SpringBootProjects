package com.logisticsapp.exception;

// custom exception - thrown for wrong or duplicate data (400)
public class BadRequestException extends RuntimeException {

    // constructor - stores the error message
    public BadRequestException(String message) {
        super(message);
    }
}