package com.telemedicine.authentication.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telemedicine.authentication.dto.LoginDetails;
import com.telemedicine.authentication.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {
    @Mock
    private AuthenticationService authenticationService;
    @InjectMocks
    private AuthenticationController authenticationController;
    private LoginDetails loginDetails;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        loginDetails=new LoginDetails();
        loginDetails.setUserName("userName");
        loginDetails.setPassword("password");
        this.mockMvc = MockMvcBuilders.standaloneSetup(authenticationController)
                .build();
    }

    @Test
    void login() throws Exception{
        when(authenticationService.authenticate(loginDetails)).thenReturn("dummyToken");
        String requestBody = objectMapper.writeValueAsString(loginDetails);
        mockMvc.perform(MockMvcRequestBuilders.post("/authenticate/login")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()));
    }
}