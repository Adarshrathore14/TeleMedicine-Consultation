package com.telemedicine.authentication.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionalHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        Map<String,String> validationErrors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(err-> validationErrors.put(err.getField(),
                err.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(validationErrors);
    }
    @ExceptionHandler(DeserializationException.class)
    public ResponseEntity<String> handleDeserializationException(DeserializationException exception){
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(exception.getMessage());
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentialsException(BadCredentialsException exception){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("enter a valid credentials");
    }
}
