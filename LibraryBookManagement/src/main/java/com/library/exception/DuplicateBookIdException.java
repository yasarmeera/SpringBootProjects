package com.library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// DuplicateBookIdException — custom error for repeated IDs, automatically returns 409
@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateBookIdException extends RuntimeException {

	public DuplicateBookIdException(String message) {
		super(message);
	}
}