package com.telemedicine.appointment.feignresponse;
import com.telemedicine.appointment.dto.BillingResponseDto;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@FeignClient(name = "Billing-Service")
public interface BillingFeignService {
    @GetMapping("/billing/{appointmentId}")
    @CircuitBreaker(name="Billing-Service",fallbackMethod = "billFallBack")
    ResponseEntity<BillingResponseDto> getBillingByAppointmentId(@PathVariable int appointmentId);
    default ResponseEntity<BillingResponseDto> billFallBack(CallNotPermittedException exception) {
        BillingResponseDto billingResponse = new BillingResponseDto();
        billingResponse.setBillNumber(-1);
        billingResponse.setAppointmentId(0);
        billingResponse.setConsultationFees(0.00);
        return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(billingResponse);
    }
}