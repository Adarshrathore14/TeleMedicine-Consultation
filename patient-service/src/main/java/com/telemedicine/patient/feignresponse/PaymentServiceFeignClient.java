package com.telemedicine.patient.feignresponse;
import com.telemedicine.patient.dto.NotificationResponseDto;
import com.telemedicine.patient.dto.PaymentRequest;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
 
 
@FeignClient(name = "Payment-service")
public interface PaymentServiceFeignClient {
   
    @PostMapping("/payments/dopayment/{billingId}")
    @CircuitBreaker(name = "Payment-Service",fallbackMethod = "defaultPaymentFallBack")
    public ResponseEntity<NotificationResponseDto> doPayment (@PathVariable("billingId") int billingId, @RequestBody PaymentRequest paymentRequest);
//	 @GetMapping("/payments/getDummyString")
//	public String getString();
    default ResponseEntity<NotificationResponseDto> defaultPaymentFallBack(CallNotPermittedException exception){
        return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(new NotificationResponseDto());
    }
}