package com.telemedicine.authentication.exceptions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

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
    private String deserializationMessage;
    private String badCredentialsMessage;
    @BeforeEach
    void setUp() {
        globalExceptionalHandler = new GlobalExceptionalHandler();
        deserializationMessage="deserialized";
        badCredentialsMessage="badCredentials";

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
    void handleDeserializationException() {
        ResponseEntity<String> response = globalExceptionalHandler.handleDeserializationException(new
                DeserializationException(deserializationMessage));
        assertEquals(422,response.getStatusCode().value());
        assertEquals(deserializationMessage,response.getBody());
    }

    @Test
    void handleBadCredentialsException() {
        ResponseEntity<String> response = globalExceptionalHandler.handleBadCredentialsException(new
                BadCredentialsException(badCredentialsMessage));
        assertEquals(401,response.getStatusCode().value());
        assertEquals("enter a valid credentials",response.getBody());
    }
}