package com.telemedicine.payment.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("Doctor-service")
public interface DoctorFeignClients {
	
	@PostMapping("/doctor/addappointment/{aid}")
	public  void addAppointmentToDoctor(@PathVariable int aid);
}
