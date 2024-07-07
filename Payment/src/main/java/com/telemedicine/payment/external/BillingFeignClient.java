package com.telemedicine.payment.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("Billing-Service")
public interface BillingFeignClient {

	@GetMapping("/billing/getfee/{bid}")
    public Double getFeeByBid(@PathVariable int bid);
	
	@GetMapping("/billing/getappointmentid/{bid}")
	public ResponseEntity<Integer> getAppointmentIdByBid(@PathVariable int bid);
	
	@GetMapping("/billing/getstatus/{bid}")
	public ResponseEntity<String> getStatusByBid(@PathVariable int bid);
	
	@GetMapping("/billing/setstatus/{bid}")
	public void setStatusByBid(@PathVariable int bid);
}
