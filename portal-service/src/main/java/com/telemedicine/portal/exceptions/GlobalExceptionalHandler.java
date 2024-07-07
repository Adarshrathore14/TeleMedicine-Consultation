package com.telemedicine.portal.exceptions;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.security.auth.login.AccountNotFoundException;
import java.util.HashMap;
import java.util.Map;
@RestControllerAdvice
@Slf4j
/**
 * @author Harish Babu
 * @version 0.0.1
 * <p> This class will take care of the Exceptions related </p>
 * <p> validations</p>
 * <p> no complaints</p>
 * <p>invalid ticket number</p>
 */
public class GlobalExceptionalHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        Map<String,String> validationErrors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error->
                validationErrors.put(error.getField(),error.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(validationErrors);
    }
    @ExceptionHandler(NoComplaintsException.class)
    public ResponseEntity<String> handleNoComplaintsException(NoComplaintsException exception){
               return ResponseEntity.status(HttpStatus.NO_CONTENT).body(exception.getMessage());
    }
    @ExceptionHandler(InvalidTicketNumberException.class)
    public ResponseEntity<String> handleInvalidTicketNumberException(InvalidTicketNumberException exception){
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
