package com.telemedicine.DoctorService.externalService;
import com.telemedicine.DoctorService.dtos.MedicalDescription;
import com.telemedicine.DoctorService.dtos.MedicalRecordResponse;
import com.telemedicine.DoctorService.dtos.MedicalRecordResponses;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.telemedicine.DoctorService.dtos.MedicalRecord;
@FeignClient(name = "medicalrecord-service",path = "/medicalrecord")
public interface MedicalRecordFeign {
	 @PostMapping("/createmedicalrecord")
	 @CircuitBreaker(name="medicalrecord-service",fallbackMethod = "defaultFallBack")
	 public ResponseEntity<MedicalRecordResponse> createMedicalRecord(@RequestBody MedicalRecord medicalRecord);
	@GetMapping("/getmedicalrecordbyid/{medicalId}")
	@CircuitBreaker(name="medicalrecord-service",fallbackMethod = "defaultFallBack")
	public ResponseEntity<MedicalRecordResponse> getMedicalRecordById(@PathVariable int medicalId);
	@GetMapping("/getallmedicalrecords/{doctorId}")
	@CircuitBreaker(name="medicalrecord-service",fallbackMethod = "defaultFallBackResponses")
	public ResponseEntity<MedicalRecordResponses> getAllMedicalRecords(@PathVariable int doctorId);
	@PutMapping("/{medicalId}/newmedicaldescription")
	@CircuitBreaker(name="medicalrecord-service",fallbackMethod = "defaultFallBack")
	public ResponseEntity<MedicalRecordResponse> updateMedicalDescription(
			@PathVariable int medicalId, @RequestBody MedicalDescription newMedicalDescription);
	default ResponseEntity<MedicalRecordResponse> defaultFallBack(CallNotPermittedException exception){
		return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(new MedicalRecordResponse());
	}
	default ResponseEntity<MedicalRecordResponses> defaultFallBackResponses(CallNotPermittedException exception){
		return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(new MedicalRecordResponses());
	}
}
