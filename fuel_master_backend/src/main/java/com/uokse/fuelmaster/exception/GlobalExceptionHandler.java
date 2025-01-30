package com.uokse.fuelmaster.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.uokse.fuelmaster.response.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundError(NoHandlerFoundException ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            "The requested resource was not found"
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    // Handles invalid enum values for fuelType
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        return ResponseEntity.badRequest().body("Invalid fuel type. Allowed values: PETROL, DIESEL, ELECTRIC, HYBRID");
    }
} 