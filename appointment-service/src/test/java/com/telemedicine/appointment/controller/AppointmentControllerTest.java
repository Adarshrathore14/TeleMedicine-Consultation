package com.telemedicine.appointment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.telemedicine.appointment.dto.AppointmentResponse;
import com.telemedicine.appointment.dto.NotificationResponse;
import com.telemedicine.appointment.entity.AppointmentDetailsEntity;
import com.telemedicine.appointment.service.AppointmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppointmentControllerTest {
    @Mock
    private AppointmentService appointmentService;
    @InjectMocks
    private AppointmentController appointmentController;
    private ObjectMapper objectMapper;
    private AppointmentDetailsEntity appointmentDetails;
    private NotificationResponse notificationResponse;
    private MockMvc mockMvc;
    private AppointmentResponse appointmentResponse;
    @BeforeEach
    void setUp() {
        objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        this.mockMvc = MockMvcBuilders.standaloneSetup(appointmentController)
                .build();
        appointmentDetails = new AppointmentDetailsEntity(1,"123",1,1, LocalDate.now(),
                "pending",false, LocalDateTime.now(), null);
        appointmentResponse = new AppointmentResponse();
        appointmentResponse.setAppointmentId(1);
        appointmentResponse.setBillNumber(1);
        notificationResponse = new NotificationResponse("123",1);
    }

    @Test
    void addAppointment()throws Exception {
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Authentication authentication = Mockito.mock(Authentication.class);
        UserDetails userDetails = Mockito.mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("patient123");
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(appointmentService.createAppointment(1,"patient123",appointmentDetails)).
                thenReturn(appointmentResponse);
        String requestBody = objectMapper.writeValueAsString(appointmentDetails);
        mockMvc.perform(MockMvcRequestBuilders.post("/appointment/create/{doctorId}",1)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.CREATED.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.appointmentId").value(1));
    }
    @Test
    void getAppointmentDetails() throws Exception{
        when(appointmentService.getAppointmentDetails(1)).thenReturn(notificationResponse);
        mockMvc.perform(MockMvcRequestBuilders.get("/appointment/{appointmentId}",1))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()));
    }
}