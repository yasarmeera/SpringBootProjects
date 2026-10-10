package com.logisticsapp.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

// error response - JSON body sent when an exception happens
@Data
@AllArgsConstructor
public class ErrorResponse {
    private int status;
    private String message;
    private LocalDateTime time;
}