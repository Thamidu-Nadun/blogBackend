package com.nadun.blog.utils.exceptions;

import java.sql.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.nadun.blog.dto.common.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setTimestamp(Date.valueOf(java.time.LocalDate.now()));
        errorResponse.setStatus(404);
        errorResponse.setError("User Not Found");
        errorResponse.setMessage(ex.getMessage());

        return ResponseEntity.status(404).body(errorResponse);
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleItemNotFoundException(ItemNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setTimestamp(Date.valueOf(java.time.LocalDate.now()));
        errorResponse.setStatus(404);
        errorResponse.setError("Item Not Found");
        errorResponse.setMessage(ex.getMessage());

        return ResponseEntity.status(404).body(errorResponse);
    }
}
