package com.telemedicine.Notification_Service.exception;

import feign.RetryableException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.security.auth.login.AccountNotFoundException;
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RetryableException.class)
    public ResponseEntity<String> handleRetryAbleException(RetryableException exception){
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("service is down"+exception.toString());
    }

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<String> handlePatientNotFoundException(PatientNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(DoctorNotFoundException.class)
    public ResponseEntity<String> handleDoctorNotFoundException(DoctorNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<String> handleServiceUnavailableException(ServiceUnavailableException e) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.getMessage());
    }
    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<String> handleMalFormedJwtException(MalformedJwtException malformedJwtException){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(malformedJwtException.getMessage());
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<String> handleExpiredJwtException(ExpiredJwtException expiredJwtException){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("token is expired");
    }
    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<String> handleSignatureException(SignatureException signatureException){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("signature is invalid");
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException accessDeniedException){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("access is denied");
    }
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<String> handleAccountNotFoundException(AccountNotFoundException accountNotFoundException){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("account not found");
    }
}