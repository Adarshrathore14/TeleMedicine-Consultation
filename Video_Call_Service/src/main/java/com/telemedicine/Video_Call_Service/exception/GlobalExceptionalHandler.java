package com.telemedicine.Video_Call_Service.exception;

import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



import lombok.extern.slf4j.Slf4j;

import javax.security.auth.login.AccountNotFoundException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionalHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        Map<String,String> validationErrors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(err->
                validationErrors.put(err.getField(),err.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(validationErrors);
    }
    @ExceptionHandler(MeetingLinkGenerationException.class)
    public ResponseEntity<String> handleMeetingLinkGenerationException(MeetingLinkGenerationException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }
    @ExceptionHandler(VideoConsultationNotFoundException.class)
    public ResponseEntity<String> handleVideoConsultationNotFoundException(VideoConsultationNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
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
        log.error("Please enter a valid Token : Signature Exception");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("signature is invalid");
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException accessDeniedException){
        log.error(accessDeniedException.getMessage()+" "+"AccessDenied Exception");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("access is denied");
    }
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<String> handleAccountNotFoundException
            (AccountNotFoundException accountNotFoundException){
        log.error(accountNotFoundException.getMessage()+" "+"AccountNotFound Exception");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("account not found");
    }

    
}