package com.telemedicine.admin.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.telemedicine.admin.dto.DoctorEntity;
import com.telemedicine.admin.dto.DoctorResponse;
import com.telemedicine.admin.externalservice.DoctorFeignClients;

@RestController
@RequestMapping("/admin/doctor")
public class DoctorController {
	@Autowired
	private DoctorFeignClients doctorFeignClients;
    
    @PostMapping("/adddoctors")
    @PreAuthorize("hasAuthority('Admin')")
	public ResponseEntity<DoctorResponse> addDoctor(@RequestBody DoctorEntity doctorRequest) {
		System.out.println(doctorRequest.toString());
		DoctorResponse doctors = doctorFeignClients.createDoctor(doctorRequest).getBody();
		 return new ResponseEntity<>(doctors, HttpStatus.CREATED);
	}
	@DeleteMapping("/deletedoctor/{id}")
	@PreAuthorize("hasAuthority('Admin')")
	public ResponseEntity<Void> deleteDoctor(@PathVariable int id) {
		doctorFeignClients.deleteDoctorDetailsByDoctorId(id);
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
