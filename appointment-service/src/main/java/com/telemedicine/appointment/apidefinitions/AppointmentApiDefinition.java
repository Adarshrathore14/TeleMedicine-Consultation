package com.telemedicine.appointment.apidefinitions;
import com.telemedicine.appointment.dto.AppointmentResponse;
import com.telemedicine.appointment.dto.NotificationResponse;
import com.telemedicine.appointment.entity.AppointmentDetailsEntity;
import com.telemedicine.appointment.exceptions.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "appointment",description = "describes how to book an appointment with the doctor")
public interface AppointmentApiDefinition {
    @Operation(summary = "Adds an appointment with the doctor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "appointment created successfully"),
            @ApiResponse(responseCode = "406",description = "validation errors"),
            @ApiResponse(responseCode = "409",description = "Appointment already booked on this Date")
    })
    ResponseEntity<AppointmentResponse> addAppointment(@PathVariable int doctorId, @Valid @RequestBody
    AppointmentDetailsEntity appointmentDetails) throws InvalidDoctorIdException, ServiceUnavailableException,
            AppointmentAlreadyExistsException, InvalidSlotIdException;

    @Operation(summary = "get the Appointment Details By AppointmentId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "data generated"),
            @ApiResponse(responseCode = "404",description = "invalid appointmentId")
    })
    ResponseEntity<NotificationResponse> getAppointmentDetails(int appointmentId) throws InvalidAppointmentIdException;
}
