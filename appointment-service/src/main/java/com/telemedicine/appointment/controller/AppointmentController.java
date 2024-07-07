package com.telemedicine.appointment.controller;
import com.telemedicine.appointment.apidefinitions.AppointmentApiDefinition;
import com.telemedicine.appointment.dto.AppointmentResponse;
import com.telemedicine.appointment.dto.NotificationResponse;
import com.telemedicine.appointment.entity.AppointmentDetailsEntity;
import com.telemedicine.appointment.exceptions.*;
import com.telemedicine.appointment.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointment")
@AllArgsConstructor
public class AppointmentController implements AppointmentApiDefinition {
    private final AppointmentService appointmentService;
    @PostMapping("/create/{doctorId}")
    @PreAuthorize("hasAuthority('Patient')")
    @Override
    public ResponseEntity<AppointmentResponse> addAppointment(@PathVariable int doctorId, @Valid
    @RequestBody AppointmentDetailsEntity appointmentDetails) throws InvalidDoctorIdException, ServiceUnavailableException,
            AppointmentAlreadyExistsException, InvalidSlotIdException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String patientId = ((UserDetails)principal).getUsername();
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.createAppointment(doctorId, patientId,
                appointmentDetails));
    }

    @Override
    @GetMapping("/{appointmentId}")
    public ResponseEntity<NotificationResponse> getAppointmentDetails(@PathVariable int appointmentId)
            throws InvalidAppointmentIdException {
        return ResponseEntity.ok(appointmentService.getAppointmentDetails(appointmentId));

    }
//    @GetMapping("/getallappointments")
//    public ResponseEntity<List<AppointmentDetailsEntity>> getAllAppointments()
//    {
//    	return ResponseEntity.ok(appointmentService.getAllAppointments());
//    }
}
