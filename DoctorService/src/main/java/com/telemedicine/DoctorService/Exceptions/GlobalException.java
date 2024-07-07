package com.telemedicine.DoctorService.Exceptions;
import javax.security.auth.login.AccountNotFoundException;

import feign.RetryableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.telemedicine.DoctorService.dtos.ErrorModel;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalException {
	
	@ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorModel> handleNotFoundException(ResourceNotFoundException ex,final HttpServletRequest request) {
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
    @ExceptionHandler(RetryableException.class)
    public ResponseEntity<String> handleRetryAbleException(RetryableException exception){
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("service is down"+exception.toString());
    }
}
