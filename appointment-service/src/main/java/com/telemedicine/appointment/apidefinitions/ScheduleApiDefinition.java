package com.telemedicine.appointment.apidefinitions;
import com.telemedicine.appointment.dto.ScheduleResponse;
import com.telemedicine.appointment.exceptions.InvalidDoctorIdException;
import com.telemedicine.appointment.exceptions.NoSchedulesAvailableException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate;
import java.util.List;
@Tag(name = "schedule",description = "provides the schedules of each doctors to the patient")
public interface ScheduleApiDefinition {
    @Operation(summary = "Gets the list of doctor schedules based on date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "gets the list of schedules based on date"),
            @ApiResponse(responseCode = "404",description = "no doctor schedules available on this date")
    })
    ResponseEntity<List<ScheduleResponse>> getByDate(@RequestParam LocalDate date)
            throws NoSchedulesAvailableException;

    @Operation(summary = "Gets the list of schedule for a specific doctor based on doctorId and date " +
            "or based on doctorId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "gets the list of schedules for a doctor based on doctorId"),
            @ApiResponse(responseCode = "200",description = "gets the list of schedules for a doctor based on doctorId " +
                    "and date"),
            @ApiResponse(responseCode = "404",description = "doctor id is not a valid one"),
            @ApiResponse(responseCode = "404",description = "no schedules available for the doctor on the date")
    })
    ResponseEntity<ScheduleResponse> getSchedulesByDoctorId(@PathVariable int doctorId, @RequestParam LocalDate date)
            throws InvalidDoctorIdException, NoSchedulesAvailableException;
}
