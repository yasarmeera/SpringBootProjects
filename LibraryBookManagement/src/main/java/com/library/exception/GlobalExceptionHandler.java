package com.library.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// GlobalExceptionHandler — catches all errors in one place
@RestControllerAdvice
public class GlobalExceptionHandler {

	// Handles BookNotFoundException — returns 404 with a clear message
	@ExceptionHandler(BookNotFoundException.class)
	public String handleBookNotFound(BookNotFoundException ex) {
		return ex.getMessage();
	}

	// Handles DuplicateBookIdException — returns 409 with a clear message
	@ExceptionHandler(DuplicateBookIdException.class)
	public String handleDuplicateBookId(DuplicateBookIdException ex) {
		return ex.getMessage();
	}

	// Handles validation errors (@Valid failures) — returns 400 with field-by-field
	// details
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, String> handleValidationErrors(MethodArgumentNotValidException ex) {

		Map<String, String> errors = new HashMap<>();

		for (var fieldError : ex.getBindingResult().getFieldErrors()) {
			errors.put(fieldError.getField(), fieldError.getDefaultMessage());
		}

		return errors;
	}
}