package com.telemedicine.MedicalRecord.controller;
import com.telemedicine.MedicalRecord.dtos.MedicalRecordResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.telemedicine.MedicalRecord.dtos.MedicalDescription;
import com.telemedicine.MedicalRecord.dtos.MedicalRecordResponse;
import com.telemedicine.MedicalRecord.model.MedicalRecordEntity;
import com.telemedicine.MedicalRecord.service.MedicalRecord_Service;

@Controller
@RequestMapping("/medicalrecord")
public class MedicalRecordController {
	@Autowired
	private MedicalRecord_Service recordService; 
	
	@PostMapping("/createmedicalrecord")
	@PreAuthorize("hasAuthority('Doctor')")
	public ResponseEntity<MedicalRecordResponse> createMedicalRecord(@RequestBody MedicalRecordEntity medicalRecordEntity){
		MedicalRecordResponse record=recordService.createMedicalRecord(medicalRecordEntity);
		return new ResponseEntity<MedicalRecordResponse>(record,HttpStatus.CREATED);
	
	}
	
    @GetMapping("/getmedicalrecordbyid/{medicalId}")
	@PreAuthorize("hasAuthority('Doctor') or hasAuthority('Admin')")
	public ResponseEntity<MedicalRecordResponse> getMedicalRecordById(@PathVariable int medicalId) {
		MedicalRecordResponse recordResponse= recordService.getMedicalRecordByMedicalId(medicalId);
		return new ResponseEntity<MedicalRecordResponse>(recordResponse,HttpStatus.OK);
	}

    @GetMapping("/getallmedicalrecords/{doctorId}")
	@PreAuthorize("hasAuthority('Doctor') or hasAuthority('Admin')")
    public ResponseEntity<MedicalRecordResponses> getAllMedicalRecords(@PathVariable int doctorId){
    	MedicalRecordResponses medicalRecordResponses = recordService.getAllMedicalRecords(doctorId);
        return ResponseEntity.ok(medicalRecordResponses);
   }
    @PutMapping("/{medicalId}/updatemedicalRecord")
	@PreAuthorize("hasAuthority('Doctor')")
    public ResponseEntity<MedicalRecordResponse> updateMedicalDescription(
            @PathVariable int medicalId,
            @RequestBody MedicalDescription newMedicalDescription) {
        MedicalRecordResponse updatedMedicalRecord = recordService.updateMedicalRecord(medicalId, newMedicalDescription);
        return ResponseEntity.ok(updatedMedicalRecord);
    }
}
