package com.telemedicine.authentication.configurations;

import com.telemedicine.authentication.entity.AuthenticationEntity;
import com.telemedicine.authentication.repository.AuthenticationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDetailsAuthenticationTest {
    @Mock
    private AuthenticationRepository authenticationRepository;
    @InjectMocks
    private UserDetailsAuthentication userDetailsAuthentication;
    private AuthenticationEntity authenticationEntity;
    @BeforeEach
    void setUp() {
        authenticationEntity = new AuthenticationEntity("123","userName","user@gmail.com",
                "user","patient");
    }

    @Test
    void loadUserByUsername() {
        when(authenticationRepository.findByUserName("userName")).thenReturn(Optional.of(authenticationEntity));
        assertNotNull(userDetailsAuthentication.loadUserByUsername("userName"));
    }
    @Test
    void loadByUsernameNegativeCase(){
        when(authenticationRepository.findByUserName("userName")).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class,()->userDetailsAuthentication.loadUserByUsername("userName"));
    }
    @Test
    void loadByEmail(){
        when(authenticationRepository.findByEmail("user@gmail.com")).thenReturn(Optional.of(authenticationEntity));
        assertNotNull(userDetailsAuthentication.loadUserByUsername("user@gmail.com"));
    }
}