package com.telemedicine.patient.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.telemedicine.patient.dto.DoctorResponse;
import com.telemedicine.patient.feignresponse.DoctorServiceFeignClient;

import lombok.AllArgsConstructor;
@RestController
@AllArgsConstructor
public class DoctorController {
   
    @Autowired
    DoctorServiceFeignClient doctorServiceFeignClient;
 
    @GetMapping("/getalldoctors")
    @PreAuthorize("hasAuthority('Patient')")
    public ResponseEntity<List<DoctorResponse>> getAllRecords(){
    	System.out.println("came to patient"+doctorServiceFeignClient.getAllDoctors().getBody());
        return doctorServiceFeignClient.getAllDoctors();
    }
    @GetMapping("/getdoctorbyid/{doctorId}")
    @PreAuthorize("hasAuthority('Patient')")
    public ResponseEntity<DoctorResponse> getDoctorById(@PathVariable int doctorId){
        return doctorServiceFeignClient.getDoctorById(doctorId);
    }
}
