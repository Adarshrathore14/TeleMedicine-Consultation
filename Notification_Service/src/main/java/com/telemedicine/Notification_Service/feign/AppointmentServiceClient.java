package com.telemedicine.Notification_Service.feign;

import com.telemedicine.Notification_Service.entity.dto.NotificationResponse;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name="appointment-service",path="/appointmentService" )
public interface AppointmentServiceClient {
	
	@PutMapping("/update/{appointmentId}")
	@CircuitBreaker(name = "Appointment-Service",fallbackMethod = "defaultAppointmentResponse")
	public ResponseEntity<String> updateRecord(@PathVariable int appointmentId);
	@GetMapping("/appointment/{appointmentId}")
	@CircuitBreaker(name = "Appointment-Service",fallbackMethod = "defaultAppointmentDetails")
	public ResponseEntity<NotificationResponse> getAppointmentDetails(@PathVariable int appointmentId);
	default ResponseEntity<String> defaultAppointmentResponse(CallNotPermittedException exception){
		return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body("");
	}
	default ResponseEntity<NotificationResponse> defaultAppointmentDetails(CallNotPermittedException exception){
		return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(new NotificationResponse());
	}

}
