package com.telemedicine.admin;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.telemedicine.admin.controller.SlotTimingController;
import com.telemedicine.admin.entity.SlotTiming;
import com.telemedicine.admin.service.SlotTimingService;

class SlotTimingControllerTest {

    @Mock
    private SlotTimingService slotTimingService;

    @InjectMocks
    private SlotTimingController slotTimingController;
    
    
    @BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

    @Test
    void testGetAllSlotTimings() {
        
        List<SlotTiming> slotTimingList = Arrays.asList(
                new SlotTiming(1,LocalTime.now(),LocalTime.now()),
                new SlotTiming(2,LocalTime.now(),LocalTime.now())
        );
        when(slotTimingService.getAllSlotTimings()).thenReturn(slotTimingList);

        
        ResponseEntity<List<SlotTiming>> response = slotTimingController.getAllSlotTimings();

        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(slotTimingList, response.getBody());
    }

    @Test
    void testGetAllSlotTimingsEmptyList() {
        
        when(slotTimingService.getAllSlotTimings()).thenReturn(Arrays.asList());

        
        ResponseEntity<List<SlotTiming>> response = slotTimingController.getAllSlotTimings();

        
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetSlotTimingById() {
        
        int slotTimingId = 1;
        SlotTiming slotTiming = new SlotTiming(slotTimingId,LocalTime.now(),LocalTime.now());
        when(slotTimingService.getSlotTimingById(slotTimingId)).thenReturn(slotTiming);

        
        ResponseEntity<SlotTiming> response = slotTimingController.getSlotTimingById(slotTimingId);

        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(slotTiming, response.getBody());
    }

    @Test
    void testGetSlotTimingByIdNotFound() {
        
        int slotTimingId = 1;
        when(slotTimingService.getSlotTimingById(slotTimingId)).thenReturn(null);

        
        ResponseEntity<SlotTiming> response = slotTimingController.getSlotTimingById(slotTimingId);

        
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }
    @Test
    void testSaveSlotTiming() {
        
        SlotTiming slotTimingToSave = new SlotTiming(1,LocalTime.now(),LocalTime.now());
        when(slotTimingService.saveSlotTiming(slotTimingToSave)).thenReturn(new SlotTiming(1,LocalTime.now(),LocalTime.now()));

        
        ResponseEntity<SlotTiming> response = slotTimingController.saveSlotTiming(slotTimingToSave);

        
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
       
    }

    @Test
    void testUpdateSlotTiming() {
        
        int slotTimingId = 1;
        SlotTiming updatedSlotTiming = new SlotTiming(slotTimingId,LocalTime.now(),LocalTime.now());
        when(slotTimingService.updateSlotTiming(slotTimingId, updatedSlotTiming)).thenReturn(updatedSlotTiming);

        
        ResponseEntity<SlotTiming> response = slotTimingController.updateSlotTiming(slotTimingId, updatedSlotTiming);

        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
       
    }

    @Test
    void testUpdateSlotTimingNotFound() {
        
        int slotTimingId = 1;
        SlotTiming updatedSlotTiming = new SlotTiming(slotTimingId,LocalTime.now(),LocalTime.now());
        when(slotTimingService.updateSlotTiming(slotTimingId, updatedSlotTiming)).thenReturn(null);

        
        ResponseEntity<SlotTiming> response = slotTimingController.updateSlotTiming(slotTimingId, updatedSlotTiming);

        
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testDeleteSlotTiming() {
        
        int slotTimingId = 1;
        doNothing().when(slotTimingService).deleteSlotTiming(slotTimingId);

        
        ResponseEntity<Void> response = slotTimingController.deleteSlotTiming(slotTimingId);

        
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    
}
