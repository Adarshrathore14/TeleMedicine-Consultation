package com.telemedicine.payment.external;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.telemedicine.payment.dto.AppointmentFromAppointment;





@FeignClient(name = "appointment-service")
public interface AppointmentFeignClient {

	@GetMapping("/appointmentService/appointment/{appointmentId}")
    public ResponseEntity<AppointmentFromAppointment> getAppointmentDetails(@PathVariable int appointmentId);
}
