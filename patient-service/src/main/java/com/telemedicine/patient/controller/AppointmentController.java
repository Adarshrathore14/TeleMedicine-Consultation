package com.telemedicine.patient.controller;

import com.telemedicine.patient.apidefinitions.AppointmentApiDefinition;
import com.telemedicine.patient.dto.AppointmentDetails;
import com.telemedicine.patient.dto.AppointmentResponse;
import com.telemedicine.patient.entity.AppointmentDetailsEntity;
import com.telemedicine.patient.exceptions.*;
import com.telemedicine.patient.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointment")
@AllArgsConstructor
public class AppointmentController implements AppointmentApiDefinition {
    private final AppointmentService appointmentService;
    @PostMapping("/create/{doctorId}")
    @PreAuthorize("hasAuthority('Patient')")
    @Override
    public ResponseEntity<AppointmentResponse> addAppointment(@PathVariable int doctorId, @Valid
    @RequestBody AppointmentDetailsEntity appointmentDetails) throws InvalidDoctorIdException, AppointmentAlreadyExistsException,
            InvalidSlotIdException, ServiceUnavailableException
    {
        return appointmentService.createAppointment(doctorId,appointmentDetails);
    }//(as we are throwing the serviceUnavailableException in the Feign Service (Appointment-Service) to handle that only we are throwing serviceUnavailable;
    @Override
    @GetMapping("/myAppointments")
    @PreAuthorize("hasAuthority('Patient')")
    public ResponseEntity<List<AppointmentDetails>> myAppointments() throws NoAppointmentsAvailableException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String patientId = ((UserDetails)principal).getUsername();
       return ResponseEntity.ok(appointmentService.myAppointments(patientId));
    }
}
