package com.telemedicine.admin;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.telemedicine.admin.controller.ScheduleController;
import com.telemedicine.admin.dto.DateRequest;
import com.telemedicine.admin.dto.DoctorDetailsResponse;
import com.telemedicine.admin.dto.ScheduleResponse;
import com.telemedicine.admin.entity.Schedule;
import com.telemedicine.admin.entity.SlotTiming;
import com.telemedicine.admin.service.ScheduleService;

class ScheduleControllerTest {

    @Mock
    private ScheduleService scheduleService;

    @InjectMocks
    private ScheduleController scheduleController;
    
    
    @BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

    @Test
    void testGetAllSchedules() {
        // Given
        List<ScheduleResponse> scheduleList = Arrays.asList(
                new ScheduleResponse(1, new DoctorDetailsResponse(), Arrays.asList(new SlotTiming()), LocalDate.now()),
                new ScheduleResponse(2, new DoctorDetailsResponse(), Arrays.asList(new SlotTiming()), LocalDate.now())
        );
        when(scheduleService.getAllSchedules()).thenReturn(scheduleList);

        // When
        ResponseEntity<List<ScheduleResponse>> response = scheduleController.getAllSchedules();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(scheduleList, response.getBody());
    }

    @Test
    void testGetAllSchedulesEmptyList() {
        // Given
        when(scheduleService.getAllSchedules()).thenReturn(Collections.emptyList());

        // When
        ResponseEntity<List<ScheduleResponse>> response = scheduleController.getAllSchedules();

        // Then
       assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
       assertNull(response.getBody());
    }

    @Test
    void testGetScheduleById() {
        // Given
        int scheduleId = 1;
        ScheduleResponse scheduleResponse = new ScheduleResponse(scheduleId, new DoctorDetailsResponse(), Arrays.asList(new SlotTiming()), LocalDate.now());
        when(scheduleService.getScheduleById(scheduleId)).thenReturn(scheduleResponse);

        // When
        ResponseEntity<ScheduleResponse> response = scheduleController.getScheduleById(scheduleId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(scheduleResponse, response.getBody());
    }

    @Test
    void testGetScheduleByIdNotFound() {
        // Given
        int scheduleId = 1;
        when(scheduleService.getScheduleById(scheduleId)).thenReturn(null);

        // When
        ResponseEntity<ScheduleResponse> response = scheduleController.getScheduleById(scheduleId);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetScheduleByDate() {
        // Given
        DateRequest dateRequest = new DateRequest(LocalDate.now());
        List<ScheduleResponse> scheduleList = Arrays.asList(
                new ScheduleResponse(1, new DoctorDetailsResponse(), Arrays.asList(new SlotTiming()), LocalDate.now())
        );
        when(scheduleService.getScheduleByDate(dateRequest.getDate())).thenReturn(scheduleList);

        // When
        ResponseEntity<List<ScheduleResponse>> response = scheduleController.getScheduleByDate(dateRequest);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(scheduleList, response.getBody());
    }

    @Test
    void testGetScheduleByDateNotFound() {
        // Given
        DateRequest dateRequest = new DateRequest(LocalDate.now());
        when(scheduleService.getScheduleByDate(dateRequest.getDate())).thenReturn(Collections.emptyList());

        // When
        ResponseEntity<List<ScheduleResponse>> response = scheduleController.getScheduleByDate(dateRequest);

        // Then
       assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
       assertNull(response.getBody());
    }

    @Test
    void testGetSchedulesDoctorId() {
        // Given
        int doctorId = 1;
        List<ScheduleResponse> scheduleList = Arrays.asList(
                new ScheduleResponse(1, new DoctorDetailsResponse(), Arrays.asList(new SlotTiming()), LocalDate.now())
        );
        when(scheduleService.getSchedulesDoctorId(doctorId)).thenReturn(scheduleList);

        // When
        ResponseEntity<List<ScheduleResponse>> response = scheduleController.getSchedulesDoctorId(doctorId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(scheduleList, response.getBody());
    }

    @Test
    void testGetSchedulesDoctorIdNotFound() {
        // Given
        int doctorId = 1;
        when(scheduleService.getSchedulesDoctorId(doctorId)).thenReturn(Collections.emptyList());

        // When
        ResponseEntity<List<ScheduleResponse>> response = scheduleController.getSchedulesDoctorId(doctorId);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testSaveSchedule() {
        // Given
        Schedule scheduleToSave = new Schedule(1, 1,Arrays.asList(1, 2, 3), LocalDate.now());
        ScheduleResponse savedSchedule = new ScheduleResponse(1, new DoctorDetailsResponse(), Arrays.asList(new SlotTiming()), LocalDate.now());
        when(scheduleService.saveSchedule(scheduleToSave)).thenReturn(savedSchedule);

        // When
        ResponseEntity<ScheduleResponse> response = scheduleController.saveSchedule(scheduleToSave);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(savedSchedule, response.getBody());
    }

    @Test
    void testUpdateSchedule() {
        // Given
        int scheduleId = 1;
        Schedule updatedSchedule = new Schedule(scheduleId,1, Arrays.asList(4, 5, 6), LocalDate.now());
        ScheduleResponse scheduleResponse = new ScheduleResponse(scheduleId, new DoctorDetailsResponse(), Arrays.asList(new SlotTiming()), LocalDate.now());
        when(scheduleService.updateSchedule(scheduleId, updatedSchedule)).thenReturn(scheduleResponse);

        // When
        ResponseEntity<ScheduleResponse> response = scheduleController.updateSchedule(scheduleId, updatedSchedule);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(scheduleResponse, response.getBody());
    }

    @Test
    void testUpdateScheduleNotFound() {
        // Given
        int scheduleId = 1;
        Schedule updatedSchedule = new Schedule(scheduleId,1, Arrays.asList(4, 5, 6), LocalDate.now());
        when(scheduleService.updateSchedule(scheduleId, updatedSchedule)).thenReturn(null);

        // When
        ResponseEntity<ScheduleResponse> response = scheduleController.updateSchedule(scheduleId, updatedSchedule);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testDeleteSchedule() {
        // Given
        int scheduleId = 1;
        doNothing().when(scheduleService).deleteSchedule(scheduleId);

        // When
        ResponseEntity<Void> response = scheduleController.deleteSchedule(scheduleId);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }
}
