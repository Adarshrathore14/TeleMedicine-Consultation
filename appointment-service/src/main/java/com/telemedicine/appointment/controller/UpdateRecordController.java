package com.telemedicine.appointment.controller;
import com.telemedicine.appointment.apidefinitions.UpdateRecordApiDefinition;
import com.telemedicine.appointment.exceptions.InvalidAppointmentIdException;
import com.telemedicine.appointment.exceptions.InvalidDoctorIdException;
import com.telemedicine.appointment.service.UpdateRecordService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@AllArgsConstructor
public class UpdateRecordController implements UpdateRecordApiDefinition {
    private final UpdateRecordService updateRecordService;
    @PutMapping("/update/{appointmentId}")
    @PreAuthorize("hasAuthority('Patient')")
    @Override
    public ResponseEntity<String> updateRecord(@PathVariable int appointmentId) throws InvalidAppointmentIdException,
            InvalidDoctorIdException {

        return ResponseEntity.ok(updateRecordService.update(appointmentId));
    }
}
