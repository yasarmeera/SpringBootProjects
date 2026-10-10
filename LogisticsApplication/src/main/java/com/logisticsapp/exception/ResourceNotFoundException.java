package com.logisticsapp.exception;

// custom exception - thrown when a record is not found (404)
public class ResourceNotFoundException extends RuntimeException {

    // constructor - stores the error message
    public ResourceNotFoundException(String message) {
        super(message);
    }
}