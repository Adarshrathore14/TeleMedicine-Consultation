package com.virtusa.payment;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.telemedicine.payment.dto.NotificationResponseDto;
import com.telemedicine.payment.dto.PaymentRequest;
import com.telemedicine.payment.entity.Payment;
import com.telemedicine.payment.exception.ResourceNotFoundException;
import com.telemedicine.payment.external.BillingFeignClient;
import com.telemedicine.payment.external.NotificationFeignClient;
import com.telemedicine.payment.repo.PaymentRepository;
import com.telemedicine.payment.serviceimpl.PaymentServiceImpl;

class PaymentServiceImplTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private BillingFeignClient billingFeignClient;

    @Mock
    private NotificationFeignClient notificationFeignClient;

    @InjectMocks
    private PaymentServiceImpl paymentService;
    
    @BeforeEach
   	public void setup() {
   		MockitoAnnotations.openMocks(this);
   	}

    @Test
    void testGetAllPayments() {
        
    	List<Payment> payments1= Arrays.asList(new Payment(),new Payment());
        when(paymentRepository.findAll()).thenReturn(payments1);

        
        List<Payment> payments = paymentService.getAllPayments();

        
        assertNotNull(payments);
        
    }

    @Test
    void testGetPaymentById() {
        
        int paymentId = 1;
        Payment expectedPayment = new Payment();
        when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(expectedPayment));

        
        Payment payment = paymentService.getPaymentById(paymentId);

        
        assertNotNull(payment);
        assertEquals(expectedPayment, payment);
        
    }

    

    @Test
    void testDoPaymentPositive() {
        
        int billingId = 1;
        PaymentRequest paymentRequest = new PaymentRequest(123456);
        Payment payment = new Payment(2,123456,6000);
        when(paymentRepository.findById(2)).thenReturn(Optional.of(payment));
        when(billingFeignClient.getFeeByBid(billingId)).thenReturn(100.0);
        when(notificationFeignClient.sendNotification(1)).thenReturn(new ResponseEntity<>(new NotificationResponseDto(), HttpStatus.OK));

        
        NotificationResponseDto response = paymentService.doPayment(billingId, paymentRequest);

        
        assertNotNull(response);
        
    }
    @Test
    void testDoPaymentAccountNumberwrong() {
        
        int billingId = 1;
        PaymentRequest paymentRequest = new PaymentRequest(12345);
        Payment payment = new Payment(2,123456,6000);
        when(paymentRepository.findById(2)).thenReturn(Optional.of(payment));
        when(billingFeignClient.getFeeByBid(billingId)).thenReturn(100.0);
        when(notificationFeignClient.sendNotification(1)).thenReturn(new ResponseEntity<>(new NotificationResponseDto(), HttpStatus.OK));

        
        assertThrows(ResourceNotFoundException.class, () -> paymentService.doPayment(billingId, paymentRequest));
        
    }

    @Test
    void testDoPaymentInsufficientBalance() {
        
        int billingId = 1;
        PaymentRequest paymentRequest = new PaymentRequest();
        Payment payment = new Payment();
        payment.setAccountBalance(100);
        when(paymentRepository.findById(2)).thenReturn(Optional.of(payment));
        when(billingFeignClient.getFeeByBid(billingId)).thenReturn(500.0);

        
        assertThrows(ResourceNotFoundException.class, () -> paymentService.doPayment(billingId, paymentRequest));

        
        
    }

    @Test
    void testSavePayment() {
        
        Payment paymentToSave = new Payment(1,123456,500);
        when(paymentRepository.save(paymentToSave)).thenReturn(paymentToSave);

        
        Payment savedPayment = paymentService.savePayment(paymentToSave);

        
        assertNotNull(savedPayment);
        
    }

    @Test
    void testUpdatePayment() {
        
        int paymentId = 1;
        Payment updatedPayment = new Payment(1,123456,100);
        when(paymentRepository.existsById(paymentId)).thenReturn(true);
        when(paymentRepository.save(updatedPayment)).thenReturn(updatedPayment);

        
        Payment result = paymentService.updatePayment(paymentId, updatedPayment);

        
        assertNotNull(result);
        
    }

    @Test
    void testUpdatePaymentNotFound() {
        
        int paymentId = 1;
        Payment updatedPayment = new Payment();
        when(paymentRepository.existsById(paymentId)).thenReturn(false);

        
        assertThrows(ResourceNotFoundException.class, () -> paymentService.updatePayment(paymentId, updatedPayment));

        
        
    }

    @Test
    void testDeletePayment() {
        
        int paymentId = 1;
        when(paymentRepository.existsById(paymentId)).thenReturn(true);

        
        paymentService.deletePayment(paymentId);

        
        
        verify(paymentRepository, times(1)).deleteById(paymentId);
    }

    @Test
    void testDeletePaymentNotFound() {
        
        int paymentId = 1;
        when(paymentRepository.existsById(paymentId)).thenReturn(false);

        
        assertThrows(ResourceNotFoundException.class, () -> paymentService.deletePayment(paymentId));

        
        
    }
}
