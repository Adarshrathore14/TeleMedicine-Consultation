package com.telemedicine.patient.controller;
import com.telemedicine.patient.dto.Patient;
import com.telemedicine.patient.dto.PatientDto;
import com.telemedicine.patient.dto.UpdateDetailsDto;
import com.telemedicine.patient.entity.PatientEntity;
import com.telemedicine.patient.exceptions.InvalidPatientIdException;
import com.telemedicine.patient.service.PatientService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@RestController
@AllArgsConstructor
public class PatientController {
    private final PatientService patientService;
    @GetMapping("/viewProfile")
    @PreAuthorize("hasAuthority('Patient')")
    public ResponseEntity<PatientDto> viewProfile() throws InvalidPatientIdException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String patientId = ((UserDetails)principal).getUsername();
        return ResponseEntity.ok(patientService.viewProfile(patientId));
    }
    @PatchMapping("/updateProfile")
    @PreAuthorize("hasAuthority('Patient')")
    public ResponseEntity<PatientDto> updateProfile(@Valid @RequestBody UpdateDetailsDto updateDetailsDto) throws InvalidPatientIdException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String patientId = ((UserDetails)principal).getUsername();
        return ResponseEntity.ok(patientService.updateProfile(patientId,updateDetailsDto));
    }
    
	/*
	 * @GetMapping("/getallpatients") public ResponseEntity<List<Patient>>
	 * getAllPatients() { return ResponseEntity.ok(patientService.getAllPatients());
	 * }
	 */
    
    
}
