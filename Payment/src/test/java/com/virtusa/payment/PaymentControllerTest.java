package com.virtusa.payment;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.telemedicine.payment.controller.PaymentController;
import com.telemedicine.payment.dto.NotificationResponseDto;
import com.telemedicine.payment.dto.PaymentRequest;
import com.telemedicine.payment.entity.Payment;
import com.telemedicine.payment.service.PaymentService;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PaymentControllerTest {

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
    	MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(paymentController).build();
    }

    @Test
    void testGetAllPayments_Positive() throws Exception {
        // Given
        List<Payment> payments = Arrays.asList(new Payment(), new Payment());
        when(paymentService.getAllPayments()).thenReturn(payments);

        // When
        mockMvc.perform(MockMvcRequestBuilders.get("/payments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testDoPayment_Positive() throws Exception {
        // Given
        int billingId = 1;
        PaymentRequest paymentRequest = new PaymentRequest(/* provide necessary data */);
        NotificationResponseDto responseDto = new NotificationResponseDto(/* provide necessary data */);
        when(paymentService.doPayment(eq(billingId), any())).thenReturn(responseDto);

        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/payments/dopayment/{billingId}", billingId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(paymentRequest)))
                .andExpect(status().isOk());
    }


    @Test
    void testGetPaymentById_Positive() throws Exception {
        // Given
        int paymentId = 1;
        Payment payment = new Payment(/* provide necessary data */);
        when(paymentService.getPaymentById(paymentId)).thenReturn(payment);

        // When
        mockMvc.perform(MockMvcRequestBuilders.get("/payments/{paymentId}", paymentId))
                .andExpect(status().isOk());
    }

    @Test
    void testSavePayment_Positive() throws Exception {
        // Given
        Payment paymentToSave = new Payment(/* provide necessary data */);
        when(paymentService.savePayment(any())).thenReturn(paymentToSave);

        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(paymentToSave)))
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdatePayment_Positive() throws Exception {
        // Given
        int paymentId = 1;
        Payment updatedPayment = new Payment(/* provide necessary data */);
        when(paymentService.updatePayment(anyInt(), any())).thenReturn(updatedPayment);

        // When
        mockMvc.perform(MockMvcRequestBuilders.put("/payments/{paymentId}", paymentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedPayment)))
                .andExpect(status().isOk());
    }

    @Test
    void testDeletePayment_Positive() throws Exception {
        // When
        mockMvc.perform(MockMvcRequestBuilders.delete("/payments/{paymentId}", 1))
                .andExpect(status().isNoContent());
    }
}
