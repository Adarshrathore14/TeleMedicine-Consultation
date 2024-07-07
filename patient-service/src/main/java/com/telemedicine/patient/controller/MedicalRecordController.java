package com.telemedicine.patient.controller;
import com.telemedicine.patient.apidefinitions.MedicalRecordApiDefinition;
import com.telemedicine.patient.dto.MedicalRecordResponse;
import com.telemedicine.patient.exceptions.InvalidPatientIdException;
import com.telemedicine.patient.service.MedicalRecordService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
@RestController
@AllArgsConstructor
public class MedicalRecordController implements MedicalRecordApiDefinition {
    private final MedicalRecordService medicalRecordService;
    @GetMapping("/doctorPrescriptions")
    @PreAuthorize("hasAuthority('Patient')")
    @Override
    public ResponseEntity<List<MedicalRecordResponse>> getDoctorPrescriptions()
            throws InvalidPatientIdException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String patientId = ((UserDetails)principal).getUsername();
        return ResponseEntity.ok(medicalRecordService.getDoctorPrescriptions(patientId));
    }
}
