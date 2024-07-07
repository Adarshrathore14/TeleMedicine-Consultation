package com.telemedicine.registration.exceptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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
    private String deserializationExceptionMessage;
    private String accountActivationExceptionMessage;
    private String activationCodeExceptionMessage;
    private String invalidDateExceptionMessage;
    private String serializationExceptionMessage;
    private String invalidMobileNumberMessage;
    private String accountAlreadyExistsMessage;
    private String mobileNumberExistsMessage;
    @BeforeEach
    void setUp() {
        globalExceptionalHandler = new GlobalExceptionalHandler();
        deserializationExceptionMessage="deserialized exception";
        accountActivationExceptionMessage="account activation exception";
        activationCodeExceptionMessage="activation code is expired";
        invalidDateExceptionMessage="invalid date exception";
        serializationExceptionMessage="serialized exception";
        invalidMobileNumberMessage="invalid mobile number exception";
        mobileNumberExistsMessage="mobile number exists";
        accountAlreadyExistsMessage="account exists";
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
    void handleAccountActivationException() {
        ResponseEntity<String> response = globalExceptionalHandler.handleAccountActivationException(new
                AccountActivationException(accountActivationExceptionMessage));
        assertEquals(401,response.getStatusCode().value());
        assertEquals(accountActivationExceptionMessage,response.getBody());
    }

    @Test
    void handleActivationCodeException() {
        ResponseEntity<String> response = globalExceptionalHandler.handleActivationCodeException(new
                ActivationCodeException(activationCodeExceptionMessage));
        assertEquals(404,response.getStatusCode().value());
        assertEquals(activationCodeExceptionMessage,response.getBody());
    }

    @Test
    void handleHttpMessageNotReadableException() {
        ResponseEntity<String> response = globalExceptionalHandler.handleHttpMessageNotReadableException(new
                HttpMessageNotReadableException(invalidDateExceptionMessage));
        assertEquals(404,response.getStatusCode().value());
        assertEquals(invalidDateExceptionMessage,response.getBody());
    }

    @Test
    void handleDeserializationException() {
        ResponseEntity<String> response = globalExceptionalHandler.handleDeserializationException(new
                DeserializationException(deserializationExceptionMessage));
        assertEquals(422,response.getStatusCode().value());
        assertEquals(deserializationExceptionMessage,response.getBody());
    }

    @Test
    void handleSerializationException() {
        ResponseEntity<String> response = globalExceptionalHandler.handleSerializationException(new
                SerializationException(serializationExceptionMessage));
        assertEquals(422,response.getStatusCode().value());
        assertEquals(serializationExceptionMessage,response.getBody());
    }

    @Test
    void handleInvalidMobileNumberException() {
        ResponseEntity<String> response = globalExceptionalHandler.handleInvalidMobileNumberException(new
                InvalidMobileNumberException(invalidMobileNumberMessage));
        assertEquals(404,response.getStatusCode().value());
        assertEquals(invalidMobileNumberMessage,response.getBody());
    }
    @Test
    void handleMobileNumberAlreadyExistsException(){
        ResponseEntity<String> response = globalExceptionalHandler.handleMobileNumberAlreadyExistsException(new
                MobileNumberAlreadyExistsException(mobileNumberExistsMessage));
        assertEquals(409,response.getStatusCode().value());
        assertEquals(mobileNumberExistsMessage,response.getBody());
    }
    @Test
    void handleUserNameAndEmailAlreadyExistsException(){
        ResponseEntity<String> response = globalExceptionalHandler.handleUserNameAndEmailAlreadyExistsException(new
                UserNameAndEmailAlreadyExistsException(accountAlreadyExistsMessage));
        assertEquals(409,response.getStatusCode().value());
        assertEquals(accountAlreadyExistsMessage,response.getBody());
    }
}