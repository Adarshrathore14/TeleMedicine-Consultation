package com.telemedicine.appointment.controller;

import com.telemedicine.appointment.dto.ScheduleResponse;
import com.telemedicine.appointment.service.ScheduleService;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScheduleControllerTest {
    @Mock
    private ScheduleService scheduleService;
    @InjectMocks
    private ScheduleController scheduleController;
    private MockMvc mockMvc;
    private List<ScheduleResponse> scheduleResponseList;
    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(scheduleController)
                .build();
    }

    @Test
    void getByDate() throws Exception {
        when(scheduleService.getAllSchedulesByDate(LocalDate.now())).thenReturn(scheduleResponseList);
        mockMvc.perform(MockMvcRequestBuilders.get("/doctorSchedules/date")
                        .param("date", String.valueOf(LocalDate.now())))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()));
    }

    @Test
    void getSchedulesByDoctorId()throws Exception {
        when(scheduleService.getSchedulesByDoctorId(1)).thenReturn(new ScheduleResponse());
        mockMvc.perform(MockMvcRequestBuilders.get("/doctorSchedules/{doctorId}",1))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()));
    }
    @Test
    void getSchedulesByDoctorIdAndDate()throws Exception {
        when(scheduleService.getSchedulesByDoctorIdAndDate(1,LocalDate.now())).thenReturn(new ScheduleResponse());
        mockMvc.perform(MockMvcRequestBuilders.get("/doctorSchedules/{doctorId}",1)
                        .param("date",String.valueOf(LocalDate.now())))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()));
    }
}