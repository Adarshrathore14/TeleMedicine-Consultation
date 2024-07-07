package com.telemedicine.admin.exception;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.telemedicine.admin.dto.ErrorModel;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorModel> handleNotFoundException(Exception ex,final HttpServletRequest request) {
    	ErrorModel errorResponse = new ErrorModel();
    	errorResponse.setErrorUrl(request.getRequestURI());
    	errorResponse.setErrorMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
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
