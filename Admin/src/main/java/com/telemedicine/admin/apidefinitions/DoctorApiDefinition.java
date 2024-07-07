package com.telemedicine.admin.apidefinitions;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.telemedicine.admin.dto.DoctorEntity;
import com.telemedicine.admin.dto.DoctorResponse;

@Tag(name = "doctor",description = "describing the some operations on doctor service")
public interface DoctorApiDefinition{
	
    @Operation(summary = "Adds an doctor by the admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "doctor created successfully")
    })
    public ResponseEntity<DoctorEntity> saveSchedule(@RequestBody DoctorResponse doctor);
    
   
    @Operation(summary = "Delete doctor by doctor id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",description = "Deleting the doctor by doctor id"),
            @ApiResponse(responseCode = "404",description = "There is no doctor with given doctor id")
    })
    public void deleteDoctor(@PathVariable int id);
}
