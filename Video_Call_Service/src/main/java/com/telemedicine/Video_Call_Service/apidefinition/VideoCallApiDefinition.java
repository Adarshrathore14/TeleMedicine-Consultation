package com.telemedicine.Video_Call_Service.apidefinition;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.telemedicine.Video_Call_Service.model.VideoConsultation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


@Tag(name = "Video Call API",description = "Endpoints for managing video calls")
public interface VideoCallApiDefinition {


	 @Operation(summary = "genereates the meeting link")
	 @ApiResponses(value = {
	 			@ApiResponse(responseCode = "201", description = "Meeting link generated successfully"),
	            @ApiResponse(responseCode = "500", description = "Circuit breaker triggered for generateMeetingLink")})
	 public ResponseEntity<String> generateMeetingLink(@PathVariable int appointmentId);
	 
	 @Operation(summary = "get video Consultation details from the appointment ID")
	 @ApiResponses(value = {
         @ApiResponse(responseCode = "200", description = "Video consultation details retrieved successfully"),
         @ApiResponse(responseCode = "500", description = "Circuit breaker triggered for getVideoConsultation")
	 	})
	 public ResponseEntity<VideoConsultation> getVideoConsultation(@PathVariable int appointmentId);
	 
}
