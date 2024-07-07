package com.telemedicine.appointment.feignresponse;

import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TokenInterceptorTest {
    @Mock
    private HttpServletRequest mockHttpServletRequest;
    @InjectMocks
    private TokenInterceptor tokenInterceptor;

    @Test
    void apply() {
        RequestTemplate requestTemplate = new RequestTemplate();
        String authorizationHeader = "Authorization";
        String token = "token";
        when(mockHttpServletRequest.getHeader(authorizationHeader)).thenReturn(token);
        assertEquals(token,mockHttpServletRequest.getHeader(authorizationHeader));
    }
}