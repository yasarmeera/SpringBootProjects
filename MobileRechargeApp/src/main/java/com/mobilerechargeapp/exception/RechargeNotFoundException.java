package com.mobilerechargeapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)   // automatically returns 404
public class RechargeNotFoundException extends RuntimeException {
	public RechargeNotFoundException(String message) {
		super(message);
	}
}