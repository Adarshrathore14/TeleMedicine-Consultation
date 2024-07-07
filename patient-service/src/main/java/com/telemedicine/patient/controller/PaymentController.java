package com.telemedicine.patient.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
 
import com.telemedicine.patient.dto.NotificationResponseDto;
import com.telemedicine.patient.dto.PaymentRequest;
import com.telemedicine.patient.feignresponse.PaymentServiceFeignClient;

import lombok.AllArgsConstructor;
@RestController
@AllArgsConstructor
public class PaymentController {
   
    @Autowired
    PaymentServiceFeignClient paymentServiceFeignClient;
 
    @PostMapping("/dopayment/{billingId}")
    @PreAuthorize("hasAuthority('Patient')")
    public ResponseEntity<NotificationResponseDto> doPayment(@PathVariable int billingId,@RequestBody PaymentRequest paymentRequest){
    	System.out.println(billingId+" "+paymentRequest.toString());
        return paymentServiceFeignClient.doPayment(billingId, paymentRequest);
    }
//    @GetMapping("/getDummyStringforPatient")
//    public String getToString()
//    {
//    	return paymentServiceFeignClient.getString();
//    }
}