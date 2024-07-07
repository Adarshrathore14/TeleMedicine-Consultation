package com.telemedicine.appointment.apidefinitions;
import com.telemedicine.appointment.exceptions.InvalidAppointmentIdException;
import com.telemedicine.appointment.exceptions.InvalidDoctorIdException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "update record",description = "updates the payment and notification status along with slot adjustment")
public interface UpdateRecordApiDefinition {
    @Operation(summary = "updates the payment and notification status along with slot adjustment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "values are being updated and slot adjustment is done"),
            @ApiResponse(responseCode = "404",description = "appointment Id is not a valid one"),
            @ApiResponse(responseCode = "404",description = "doctorId is not a valid one")
    })
    ResponseEntity<String> updateRecord(@PathVariable int appointmentId) throws InvalidAppointmentIdException,
            InvalidDoctorIdException;
}
