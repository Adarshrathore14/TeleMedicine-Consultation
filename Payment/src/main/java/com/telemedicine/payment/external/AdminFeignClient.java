package com.telemedicine.payment.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.telemedicine.payment.dto.AppointmentFromAppointment;

@FeignClient(name = "ADMIN-SERVICE")
public interface AdminFeignClient {
	
	@PutMapping("/admin/schedules/removeslot")
    public void removeBookedSlot(@RequestBody AppointmentFromAppointment appointment);

}
