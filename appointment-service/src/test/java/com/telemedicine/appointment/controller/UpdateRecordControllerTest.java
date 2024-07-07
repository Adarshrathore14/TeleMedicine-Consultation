package com.telemedicine.appointment.controller;

import com.telemedicine.appointment.service.UpdateRecordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateRecordControllerTest {
    @Mock
    private UpdateRecordService updateRecordService;
    @InjectMocks
    private UpdateRecordController updateRecordController;
    private MockMvc mockMvc;
    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(updateRecordController)
                .build();
    }

    @Test
    void updateRecord() throws Exception{
        when(updateRecordService.update(1)).thenReturn("success");
        mockMvc.perform(MockMvcRequestBuilders.put("/update/{appointmentId}",1))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()));
    }
}