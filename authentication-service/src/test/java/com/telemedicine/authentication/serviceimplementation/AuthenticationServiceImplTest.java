package com.telemedicine.authentication.serviceimplementation;

import com.telemedicine.authentication.dto.LoginDetails;
import com.telemedicine.authentication.repository.AuthenticationRepository;
import com.telemedicine.authentication.util.GenerateJwtToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.security.auth.login.AccountNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {
    @Mock
    private GenerateJwtToken generateJwtToken;
    @Mock
    private Authentication authentication;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private AuthenticationRepository authenticationRepository;
    @InjectMocks
    private AuthenticationServiceImpl authenticationService;
    private LoginDetails loginWithUserName;
    private LoginDetails loginWithEmail;
    private String dummyTokenCheck;
    @BeforeEach
    void setUp() {
        loginWithUserName = new LoginDetails();
        loginWithUserName.setUserName("user123");
        loginWithUserName.setPassword("pass");
        loginWithEmail = new LoginDetails();
        loginWithEmail.setEmail("user123@gmail.com");
        loginWithEmail.setPassword("pass");
        loginWithEmail.setUserName("user123");
        dummyTokenCheck ="token";
    }
    @Test
    void authenticateWithUserNameAndPassword() throws AccountNotFoundException {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(generateJwtToken.generateToken(loginWithUserName.getUserName())).thenReturn(dummyTokenCheck);
        assertEquals(authenticationService.authenticate(loginWithUserName),dummyTokenCheck);
    }
    @Test
    void authenticateWithEmailAndPassword() throws AccountNotFoundException {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(generateJwtToken.generateToken(loginWithEmail.getUserName())).thenReturn(dummyTokenCheck);
        assertEquals(authenticationService.authenticate(loginWithUserName),dummyTokenCheck);
    }
    @Test
    void authenticateUserNegativeCase(){
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(false);
        assertThrows(AccountNotFoundException.class,()->authenticationService.authenticate(loginWithUserName));

    }
}