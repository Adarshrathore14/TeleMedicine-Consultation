package com.telemedicine.DoctorService.controller;
import java.util.List;

import com.telemedicine.DoctorService.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.telemedicine.DoctorService.model.DoctorEntity;
import com.telemedicine.DoctorService.service.DoctorService_service;
@RestController
@RequestMapping("/doctor")
public class DoctorServiceController {

	@Autowired
	private DoctorService_service doctorService;
	
	@PostMapping("/adddoctor")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<DoctorResponse> createDoctor(@RequestBody DoctorEntity doctor) {
        DoctorResponse createdDoctor = doctorService.addDoctor(doctor);
        return new ResponseEntity<>(createdDoctor, HttpStatus.CREATED);
    }
	
	@GetMapping("/getdoctorbyid/{doctorId}")
	@PreAuthorize("hasAuthority('Patient') or hasAuthority('Doctor') or hasAuthority('Admin')")
	public ResponseEntity<DoctorResponse> getDoctorById(@PathVariable int doctorId){
		return new ResponseEntity<>(doctorService.viewProfile(doctorId),HttpStatus.OK);
	}

	
	@GetMapping("/getconsultantfeebydoctorid/{doctorId}")
	@PreAuthorize("hasAuthority('Patient')")
	public double getConsultantFeeByDoctorId(@PathVariable int doctorId) {
		return doctorService.getConsultationFees(doctorId);
	}

	@PostMapping("/addmedicalrecord")
	@PreAuthorize("hasAuthority('Doctor')")
    public ResponseEntity<MedicalRecordResponse> addMedicalRecords(@RequestBody MedicalRecord medicalrecord) {
		return doctorService.createMedicalRecord(medicalrecord);
    }
	@PutMapping("/updatemedicalrecord/{medicalId}")
	@PreAuthorize("hasAuthority('Doctor')")
	public ResponseEntity<MedicalRecordResponse> updateMedicalRecord(@PathVariable int medicalId, @RequestBody
	MedicalDescription medicalDescription){
		return doctorService.updateMedicalRecord(medicalId,medicalDescription);
	}
	@GetMapping("/getAllMedicalRecords/{doctorId}")
	@PreAuthorize("hasAuthority('Doctor') or hasAuthority('Admin')")
	public ResponseEntity<MedicalRecordResponses> getAllMedicalRecords(@PathVariable int doctorId){
		return doctorService.getAllMedicalRecords(doctorId);
	}
	@GetMapping("/getMedicalRecords/{medicalId}")
	@PreAuthorize("hasAuthority('Doctor') or hasAuthority('Admin')")
	ResponseEntity<MedicalRecordResponse> getMedicalRecordByMedicalId(@PathVariable int medicalId){
		return doctorService.getMedicalRecordByMedicalId(medicalId);
	}
	
	@GetMapping("/getappointments/{doctorId}")
	@PreAuthorize("hasAuthority('Doctor') or hasAuthority('Admin')")
	public ResponseEntity<List<AppointmentResponse>> getAppointments(@PathVariable int doctorId){
		return new ResponseEntity<List<AppointmentResponse>>(doctorService.viewAppointments(doctorId),HttpStatus.OK);
	}
	
	
	@GetMapping("/getalldoctors")
	@PreAuthorize("hasAuthority('Doctor') or hasAuthority('Admin') or hasAuthority('Patient')")
	public ResponseEntity<List<DoctorResponse>> getAllDoctors(){
		List<DoctorResponse> doctor= doctorService.viewAllDoctorProfiles();
		return new ResponseEntity<>(doctor,HttpStatus.OK);
	}
	
	@DeleteMapping("/deletedoctordetailsbydoctorid/{doctorId}")
	@PreAuthorize("hasAuthority('Admin')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteDoctorDetailsByDoctorId(@PathVariable int doctorId){
		 doctorService.deleteDoctor(doctorId);
	}
	@PutMapping("/updateDoctordetails/{doctorId}")
	@PreAuthorize("hasAuthority('Doctor') or hasAuthority('Admin')")
    public ResponseEntity<DoctorResponse> updateDoctordetails(@PathVariable int doctorId,@RequestBody DoctorEntity updatedDoctor) {
		DoctorResponse updatedoctor = doctorService.updateDoctorProfile(doctorId,updatedDoctor);
        return new ResponseEntity<>(updatedoctor, HttpStatus.OK);
    }
}
