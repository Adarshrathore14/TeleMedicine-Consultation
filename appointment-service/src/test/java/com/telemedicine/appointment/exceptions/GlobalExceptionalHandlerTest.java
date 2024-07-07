package com.telemedicine.appointment.exceptions;
import feign.Request;
import feign.RetryableException;
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
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class GlobalExceptionalHandlerTest {
    private GlobalExceptionalHandler globalExceptionalHandler;
    @Mock
    private BindingResult mockBindingResult;

    @BeforeEach
    public void setUp(){
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
    void handleInvalidAppointmentIdException() {
        String invalidAppointmentIdExceptionMessage = "invalid appointment Id";
        ResponseEntity<String> response = globalExceptionalHandler.handleInvalidAppointmentIdException(new
                InvalidAppointmentIdException(invalidAppointmentIdExceptionMessage));
        assertEquals(404,response.getStatusCode().value());
        assertEquals(invalidAppointmentIdExceptionMessage,response.getBody());
    }

    @Test
    void handleInvalidDoctorIdException() {
        String invalidDoctorIdExceptionMessage = "invalid doctor Id";
        ResponseEntity<String> response = globalExceptionalHandler.handleInvalidDoctorIdException(new
                InvalidDoctorIdException(invalidDoctorIdExceptionMessage));
        assertEquals(404,response.getStatusCode().value());
        assertEquals(invalidDoctorIdExceptionMessage,response.getBody());
    }
    @Test
    void handleInvalidSlotIdException() {
        String invalidSlotIdExceptionMessage = "invalid slot Id";
        ResponseEntity<String> response = globalExceptionalHandler.handleInvalidSlotIdException(new
                InvalidSlotIdException(invalidSlotIdExceptionMessage));
        assertEquals(404,response.getStatusCode().value());
        assertEquals(invalidSlotIdExceptionMessage,response.getBody());
    }

    @Test
    void handleNoSchedulesAvailableException() {
        String noSchedulesAvailableMessage = "no schedules";
        ResponseEntity<String> response = globalExceptionalHandler.handleNoSchedulesAvailableException(new
                NoSchedulesAvailableException(noSchedulesAvailableMessage));
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
        assertEquals(noSchedulesAvailableMessage,response.getBody());
    }

    @Test
    void handleServiceUnavailableException() {
        String serviceUnavailableMessage = "service unavailable";
        ResponseEntity<String> response = globalExceptionalHandler.handleServiceUnavailableException(new
                ServiceUnavailableException(serviceUnavailableMessage));
        assertEquals(HttpStatus.SERVICE_UNAVAILABLE,response.getStatusCode());
        assertEquals(serviceUnavailableMessage,response.getBody());
    }

    @Test
    void handleSerializationException() {
        String serializationExceptionMessage = "serialization exception";
        ResponseEntity<String> response = globalExceptionalHandler.handleSerializationException(new
                SerializationException(serializationExceptionMessage));
        assertEquals(422,response.getStatusCode().value());
        assertEquals(serializationExceptionMessage,response.getBody());
    }

    @Test
    void handleRetryAbleException() {
        Map<String, Collection<String>> headers=new HashMap<>();
        Request request = Request.create(Request.HttpMethod.GET,"url",headers, new byte[1],null);
        ResponseEntity<String> response = globalExceptionalHandler.handleRetryAbleException(new
                RetryableException(1,"message", Request.HttpMethod.GET, (Throwable) null, 1L,request));
        assertEquals(HttpStatus.SERVICE_UNAVAILABLE,response.getStatusCode());
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
    @Test
    void handleAppointmentAlreadyExistsException(){
        String appointmentAlreadyExistsMessage="appointment already exists";
        ResponseEntity<String> response = globalExceptionalHandler.handleAppointmentAlreadyExistsException(
                new AppointmentAlreadyExistsException(appointmentAlreadyExistsMessage));
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
        assertEquals(appointmentAlreadyExistsMessage,response.getBody());
    }
}