package com.telemedicine.portal.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.security.auth.login.AccountNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class GlobalExceptionalHandlerTest {
    private GlobalExceptionalHandler globalExceptionalHandler;
    @Mock
    private BindingResult mockBindingResult;
    @BeforeEach
    void setUp() {
        globalExceptionalHandler = new GlobalExceptionalHandler();
    }

    @Test
    void handleMethodArgumentNotValidException() {
        Map<String,String> validationErrors = new HashMap<>();
        List<FieldError> fieldErrors = new ArrayList<>();
        fieldErrors.add(new FieldError("Object","key","error-Message"));
        validationErrors.put("key","error-Message");
        Mockito.when(mockBindingResult.getFieldErrors()).thenReturn(fieldErrors);
        ResponseEntity<Map<String,String>> response = globalExceptionalHandler.handleMethodArgumentNotValidException(
                (new MethodArgumentNotValidException(null,mockBindingResult)));
        assertEquals(406,response.getStatusCode().value());
        assertEquals(validationErrors,response.getBody());
    }

    @Test
    void handleNoComplaintsException() {
        String noComplaintsExceptionMessage = "no complaints";
        ResponseEntity<String> response = globalExceptionalHandler.handleNoComplaintsException(new
                NoComplaintsException(noComplaintsExceptionMessage));
        assertEquals(204,response.getStatusCode().value());
        assertEquals(noComplaintsExceptionMessage,response.getBody());
    }

    @Test
    void handleInvalidTicketNumberException() {
        String invalidTicketNumberMessage = "invalid ticket Number";
        ResponseEntity<String> response = globalExceptionalHandler.handleInvalidTicketNumberException(new
                InvalidTicketNumberException(invalidTicketNumberMessage));
        assertEquals(404,response.getStatusCode().value());
        assertEquals(invalidTicketNumberMessage,response.getBody());
    }

    @Test
    void handleMalFormedJwtException() {
        String malFormedExceptionMessage = "malformed";
        ResponseEntity<String> response = globalExceptionalHandler.handleMalFormedJwtException(new
                MalformedJwtException(malFormedExceptionMessage));
        assertEquals(401,response.getStatusCode().value());
        assertEquals(malFormedExceptionMessage,response.getBody());
    }

    @Test
    void handleExpiredJwtException() {
        ResponseEntity<String> response = globalExceptionalHandler.handleExpiredJwtException(new
                ExpiredJwtException(null,null,"message"));
        assertEquals(HttpStatus.UNAUTHORIZED,response.getStatusCode());
        assertEquals("token is expired",response.getBody());
    }

    @Test
    void handleSignatureException() {
        String signatureExceptionMessage = "Please Enter Valid Token";
        ResponseEntity<String> response = globalExceptionalHandler.handleSignatureException(new
                SignatureException(signatureExceptionMessage));
        assertEquals(HttpStatus.UNAUTHORIZED,response.getStatusCode());
        assertEquals("signature is invalid",response.getBody());
    }
    @Test
    void handleAccessDeniedException() {
        String accessDeniedMessage = "access denied";
        ResponseEntity<String> response = globalExceptionalHandler.handleAccessDeniedException(new
                AccessDeniedException(accessDeniedMessage));
        assertEquals(HttpStatus.UNAUTHORIZED,response.getStatusCode());
        assertEquals("access is denied",response.getBody());
    }

    @Test
    void handleAccountNotFoundException() {
        String accountNotFoundMessage = "account not found";
        ResponseEntity<String> response = globalExceptionalHandler.handleAccountNotFoundException(new
                AccountNotFoundException(accountNotFoundMessage));
        assertEquals(HttpStatus.UNAUTHORIZED,response.getStatusCode());
        assertEquals("account not found",response.getBody());
    }
}