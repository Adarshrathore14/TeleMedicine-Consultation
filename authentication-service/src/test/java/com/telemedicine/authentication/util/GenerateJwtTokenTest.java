package com.telemedicine.authentication.util;

import com.telemedicine.authentication.configurations.JwtConfiguration;
import com.telemedicine.authentication.entity.AuthenticationEntity;
import com.telemedicine.authentication.repository.AuthenticationRepository;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GenerateJwtTokenTest {
    @Mock
    private AuthenticationRepository authenticationRepository;
    @Mock
    private JwtConfiguration jwtConfiguration;
    @InjectMocks
    private GenerateJwtToken generateJwtToken;
    private AuthenticationEntity authenticationEntity;
    private String secretKey;
    private byte[] key;

    @BeforeEach
    void setUp() {
        authenticationEntity = new AuthenticationEntity("123","user123","email@gmail.com",
                "123","role");
        secretKey = "secretKeyForTestingPurpose123456789013I+/+/+jvcnvcchbcvb+/+p1234";
    }

    @Test
    void generateToken() {
        when(authenticationRepository.findByEmail("email@gmail.com")).thenReturn(Optional.of(authenticationEntity));
        when(jwtConfiguration.getSecretKey()).thenReturn(secretKey);
        when(jwtConfiguration.getExpirationTime()).thenReturn(15);
        assertNotNull(generateJwtToken.generateToken("email@gmail.com"));
    }

    @Test
    void getsignkey() {
        when(jwtConfiguration.getSecretKey()).thenReturn(secretKey);
        assertNotNull(generateJwtToken.getsignkey());
    }
}