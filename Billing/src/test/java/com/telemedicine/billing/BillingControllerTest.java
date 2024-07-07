package com.telemedicine.billing;
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

import com.telemedicine.billing.controller.BillingController;
import com.telemedicine.billing.dto.BillingResponseDto;
import com.telemedicine.billing.entity.Billing;
import com.telemedicine.billing.service.BillingService;

class BillingControllerTest {

    @Mock
    private BillingService billingService;

    @InjectMocks
    private BillingController billingController;
    
    @BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

    @Test
    void testGetAllBillings() {
        // Given
        List<BillingResponseDto> billingList = Arrays.asList(
                new BillingResponseDto(1, 1,"Pending", 100),
                new BillingResponseDto(2, 1,"Pending", 150)
        );
        when(billingService.getAllBillings()).thenReturn(billingList);

        // When
        ResponseEntity<List<BillingResponseDto>> response = billingController.getAllBillings();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(billingList, response.getBody());
    }

    @Test
    void testGetAllBillingsEmptyList() {
        // Given
        when(billingService.getAllBillings()).thenReturn(Collections.emptyList());

        // When
        ResponseEntity<List<BillingResponseDto>> response = billingController.getAllBillings();

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetBillingByAppointmentId() {
        // Given
        int appointmentId = 123;
        BillingResponseDto billingResponse = new BillingResponseDto(1, appointmentId,"Pending", 100);
        when(billingService.getBillingByAppointmentId(appointmentId)).thenReturn(billingResponse);

        // When
        ResponseEntity<BillingResponseDto> response = billingController.getBillingByAppointmentId(appointmentId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(billingResponse, response.getBody());
    }

    @Test
    void testGetFeeByBid() {
        // Given
        int billingId = 1;
        double consultationFees = 100.0;

        // Mock the behavior of billingService.getBillingById
        Billing billingResponse = new Billing(billingId, 1,"Paid",LocalDate.now(), consultationFees);
        when(billingService.getBillingById(billingId)).thenReturn(billingResponse);

        // When
        Double result = billingController.getFeeByBid(billingId).getBody();

        // Then
        assertEquals(consultationFees, result);
    }

    @Test
    void testGetFeeByBidNotFound() {
        // Given
        int billingId = 1;
        when(billingService.getBillingById(billingId)).thenReturn(null);
        // Then
        assertEquals(HttpStatus.NOT_FOUND, billingController.getFeeByBid(billingId).getStatusCode());
    }
}
