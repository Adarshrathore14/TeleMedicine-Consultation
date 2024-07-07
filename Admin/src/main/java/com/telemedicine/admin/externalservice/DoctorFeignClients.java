package com.telemedicine.admin.externalservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.telemedicine.admin.dto.DoctorEntity;
import com.telemedicine.admin.dto.DoctorResponse;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@FeignClient("Doctor-service")
public interface DoctorFeignClients {
	
	@PostMapping("/doctor/adddoctor")
    public ResponseEntity<DoctorResponse> createDoctor(@RequestBody DoctorEntity doctor);
	
	@DeleteMapping("/doctor/deletedoctordetailsbydoctorid/{doctorId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteDoctorDetailsByDoctorId(@PathVariable int doctorId);
	
	@GetMapping("/doctor/getdoctorbyid/{doctorId}")
	public ResponseEntity<DoctorResponse> getDoctorById(@PathVariable int doctorId);
}
