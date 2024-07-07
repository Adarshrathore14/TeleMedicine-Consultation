package com.telemedicine.payment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.telemedicine.payment.apidefinitions.PaymentApiDefinition;
import com.telemedicine.payment.dto.NotificationResponseDto;
import com.telemedicine.payment.dto.PaymentRequest;
import com.telemedicine.payment.entity.Payment;
import com.telemedicine.payment.service.PaymentService;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController implements PaymentApiDefinition{

    private final PaymentService paymentService;
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<List<Payment>> getAllPayments() {
    	return new ResponseEntity<>(paymentService.getAllPayments(), HttpStatus.OK);
    }
    
    @PostMapping("/dopayment/{billingId}")
    @PreAuthorize("hasAuthority('Patient')")
    @CircuitBreaker(fallbackMethod ="doPaymentFallBack", name = "BillingServiceCircuitBreaker")
    public ResponseEntity<NotificationResponseDto> doPayment(@PathVariable int billingId,@RequestBody PaymentRequest paymentRequest){
    	System.out.println("came to payment");
    	return new ResponseEntity<>(paymentService.doPayment(billingId,paymentRequest),HttpStatus.OK);
    }
    
    public ResponseEntity<NotificationResponseDto> doPaymentFallBack(CallNotPermittedException e)
    {
    	NotificationResponseDto response=new NotificationResponseDto();
 	  return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @GetMapping("/{paymentId}")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Payment> getPaymentById(@PathVariable int paymentId) {
        Payment payment = paymentService.getPaymentById(paymentId);
        return new ResponseEntity<>(payment, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Payment> savePayment(@RequestBody Payment payment) {
        Payment savedPayment = paymentService.savePayment(payment);
        return new ResponseEntity<>(savedPayment, HttpStatus.CREATED);
    }

    @PutMapping("/{paymentId}")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Payment> updatePayment(@PathVariable int paymentId, @RequestBody Payment payment) {
        Payment updatedPayment = paymentService.updatePayment(paymentId, payment);
        return new ResponseEntity<>(updatedPayment, HttpStatus.OK);
    }

    @DeleteMapping("/{paymentId}")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Void> deletePayment(@PathVariable int paymentId) {
        paymentService.deletePayment(paymentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
