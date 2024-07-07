package com.telemedicine.admin.apidefinitions;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.telemedicine.admin.dto.DateRequest;
import com.telemedicine.admin.dto.ScheduleResponse;
import com.telemedicine.admin.entity.Schedule;

@Tag(name = "schedule",description = "describes available schedules and basic crud operations on schedule")
public interface ScheduleApiDefinition {
	
    @Operation(summary = "Adds an schedule by the admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "schedule created successfully")
    })
    public ResponseEntity<ScheduleResponse> saveSchedule(@RequestBody Schedule schedule);
    
    @Operation(summary = "Get schedule by schedule Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Returning the schedule object by schedule id"),
            @ApiResponse(responseCode = "404",description = "There is no schedule with given schedule id")
    })
    public ResponseEntity<ScheduleResponse> getScheduleById(@PathVariable int id);
    
    @Operation(summary = "Get List of schedules")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Returning all the schedules"),
            @ApiResponse(responseCode = "404",description = "No schedules")
    })
    public ResponseEntity<List<ScheduleResponse>> getAllSchedules();
    
    @Operation(summary = "Update schedule by schedule id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "updating the schedule by schedule id"),
            @ApiResponse(responseCode = "404",description = "There is no schedule with given schedule id")
    })
    public ResponseEntity<ScheduleResponse> updateSchedule(@PathVariable int id, @RequestBody Schedule updatedSchedule);
    
    @Operation(summary = "Delete schedule by schedule id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",description = "Deleting the schedule by schedule id"),
            @ApiResponse(responseCode = "404",description = "There is no schedule with given schedule id")
    })
    public ResponseEntity<Void> deleteSchedule(@PathVariable int id);
    
    @Operation(summary = "get List of schedules by Date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Returning all the schedules based on given date"),
            @ApiResponse(responseCode = "404",description = "There is no schedule with given date")
    })
    public ResponseEntity<List<ScheduleResponse>> getScheduleByDate(@RequestBody DateRequest dateRequest);
    @Operation(summary = "get List of schedules by doctor id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Returning all the schedules based on doctor id"),
            @ApiResponse(responseCode = "404",description = "There is no schedule with given doctor id")
    })
    public ResponseEntity<List<ScheduleResponse>> getSchedulesDoctorId(@PathVariable int id);
}
