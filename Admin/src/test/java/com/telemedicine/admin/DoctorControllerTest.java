package com.telemedicine.admin;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.telemedicine.admin.controller.DoctorController;
import com.telemedicine.admin.dto.DoctorEntity;
import com.telemedicine.admin.dto.DoctorResponse;
import com.telemedicine.admin.externalservice.DoctorFeignClients;

class DoctorControllerTest {

    @Mock
    private DoctorFeignClients doctorFeignClients;

    @InjectMocks
    private DoctorController doctorController;
    
    @BeforeEach
   	public void setup() {
   		MockitoAnnotations.openMocks(this);
   	}

    @Test
    void testAddDoctor() {
        // Given
    	DoctorEntity doctorRequest = new DoctorEntity(/* provide necessary data */);
        DoctorResponse expectedResponse = new DoctorResponse(/* provide expected response data */);
        when(doctorFeignClients.createDoctor(doctorRequest)).thenReturn(new ResponseEntity<>(expectedResponse, HttpStatus.CREATED));

        // When
        ResponseEntity<DoctorResponse> response = doctorController.addDoctor(doctorRequest);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void testDeleteDoctor() {
        // Given
        int doctorId = 1;
        doNothing().when(doctorFeignClients).deleteDoctorDetailsByDoctorId(doctorId);

        // When
        ResponseEntity<Void> response = doctorController.deleteDoctor(doctorId);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

}
