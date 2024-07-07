package com.telemedicine.patient.apidefinitions;
import com.telemedicine.patient.dto.AppointmentDetails;
import com.telemedicine.patient.dto.AppointmentResponse;
import com.telemedicine.patient.entity.AppointmentDetailsEntity;
import com.telemedicine.patient.exceptions.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Tag(name = "appointment",description = "describes how to book an appointment with the doctor")
public interface AppointmentApiDefinition {
    @Operation(summary = "Adds an appointment with the doctor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "appointment created successfully"),
            @ApiResponse(responseCode = "406",description = "validation errors"),
            @ApiResponse(responseCode = "409",description = "Appointment already booked on this Date")
    })
    ResponseEntity<AppointmentResponse> addAppointment(@PathVariable int doctorId, @Valid @RequestBody
    AppointmentDetailsEntity appointmentDetails) throws InvalidDoctorIdException, AppointmentAlreadyExistsException,
    InvalidSlotIdException, ServiceUnavailableException;
    @Operation(summary = "gets all appointments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "all appointments shown successFully"),
            @ApiResponse(responseCode = "204",description = "no appointment bookings available")
    })
    ResponseEntity<List<AppointmentDetails>> myAppointments() throws NoAppointmentsAvailableException;
}
