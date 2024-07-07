package com.telemedicine.authorization.securityconfig;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JwtFilterTest {
    private ValidateToken mockvalidateToken;
    private HttpServletRequest mockHttpServletRequest;
    private HttpServletResponse mockHttpServletResponse;
    private FilterChain mockFilterChain;
    private String actuatorUrl;
    private String swaggerUrl;
    private String authorizedUrl;
    private JwtFilter jwtFilter;
    private String dummyToken;
    private String dummyUserName;
    private String dummyRole;
    private HandlerExceptionResolver mockExceptionalResolver;
    @Before
    public void setUp() throws Exception {
        mockvalidateToken = Mockito.mock(ValidateToken.class);
        mockHttpServletRequest=Mockito.mock(HttpServletRequest.class);
        mockHttpServletResponse = Mockito.mock(HttpServletResponse.class);
        mockFilterChain = Mockito.mock(FilterChain.class);
        mockExceptionalResolver = Mockito.mock(HandlerExceptionResolver.class);
        actuatorUrl = "/actuator/health";
        swaggerUrl = "/apiDocs";
        authorizedUrl ="/context/requestMapping/request";
        jwtFilter = new JwtFilter(mockvalidateToken);
        dummyUserName = "userName";
        dummyToken="token";
        dummyRole="admin";
    }

    @Test
    public void testActuatorUrl() throws ServletException, IOException {
        when(mockHttpServletRequest.getRequestURI()).thenReturn(actuatorUrl);
        jwtFilter.doFilterInternal(mockHttpServletRequest,mockHttpServletResponse,mockFilterChain);
        verify(mockFilterChain,times(0)).doFilter(mockHttpServletRequest,mockHttpServletResponse);
    }
    @Test
    public void testSwaggerUrl() throws ServletException, IOException {
        when(mockHttpServletRequest.getRequestURI()).thenReturn(swaggerUrl);
        jwtFilter.doFilterInternal(mockHttpServletRequest,mockHttpServletResponse,mockFilterChain);
        verify(mockFilterChain,times(0)).doFilter(mockHttpServletRequest,mockHttpServletResponse);
    }

    @Test
    public void doFilterInternal() throws ServletException, IOException {
        when(mockHttpServletRequest.getRequestURI()).thenReturn(authorizedUrl);
        when(mockHttpServletRequest.getHeader("Authorization")).thenReturn("Bearer "+dummyToken);
        when(mockvalidateToken.extractUserName(dummyToken)).thenReturn(dummyUserName);
        when(mockvalidateToken.extractRole(dummyToken)).thenReturn(dummyRole);
        when(mockvalidateToken.isTokenExpired(dummyToken)).thenReturn(false);

        jwtFilter.doFilterInternal(mockHttpServletRequest, mockHttpServletResponse, mockFilterChain);
        Assertions.assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
    @Test
    public void noTokenAvailable(){
        when(mockHttpServletRequest.getRequestURI()).thenReturn(authorizedUrl);
        when(mockHttpServletRequest.getHeader("Authorization")).thenReturn(null);
        Assertions.assertThrows(NullPointerException.class,()->jwtFilter.doFilterInternal(mockHttpServletRequest,mockHttpServletResponse,mockFilterChain));
    }

}