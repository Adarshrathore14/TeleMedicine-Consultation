package com.telemedicine.registration.accountactivationandinitiation;

import com.telemedicine.registration.configurations.AccountActivationConfiguration;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class ValidateAccountActivationTokenTest {
    @Mock
    private AccountActivationConfiguration accountActivationConfiguration;
    @InjectMocks
    private ValidateAccountActivationToken validateAccountActivationToken;
    private String dummyToken;
    @BeforeEach
    void setUp() {
        Map<String,String> claims = new HashMap<>();
        String jwtSecret="secretKeyForTestingPurpose123456789013I+/+/+jvcnvcchbcvb+/+p1234";
        Mockito.when(accountActivationConfiguration.getSecretKey()).thenReturn(jwtSecret);
        claims.put("patientName","patient");
        dummyToken = Jwts
                .builder()
                .setClaims(claims)
                .setSubject("abc123")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+3600000))
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret)), SignatureAlgorithm.HS256)
                .compact();
    }
    @Test
    void extractPatientName() {
        assertEquals("patient",validateAccountActivationToken.extractPatientName(dummyToken));
    }

    @Test
    void validateToken() {
        boolean validateToken = validateAccountActivationToken.validateToken(dummyToken);
        assertTrue(validateToken);
    }
}