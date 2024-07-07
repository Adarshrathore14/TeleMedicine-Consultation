package com.telemedicine.Notification_Service.apidefination;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.telemedicine.Notification_Service.entity.dto.NotificationResponseDto;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Notification",description = "Description to Crud of Notification Service")
public interface NotificationApiDefination {
	 
	 
	 @Operation(summary = "To send notification")
	 @ApiResponses(value = {
         @ApiResponse(responseCode = "200", description = "Notification sent successfully"),
         @ApiResponse(responseCode = "500", description = "Circuit breaker triggered for sendNotification")
	 	})
	 public ResponseEntity<NotificationResponseDto> sendNotification(@PathVariable int appointmentId);
	 
	 
	 @Operation(summary = "Get notifications for the given doctor ID")
	    @ApiResponses(value = {
	            @ApiResponse(responseCode = "200", description = "Doctor notifications retrieved successfully"),
	            @ApiResponse(responseCode = "500", description =  "Circuit breaker triggered for getNotifications")
	    })
	 public ResponseEntity<List<NotificationResponseDto>> getNotificationsDoctor(
	            @PathVariable Integer doctorId
	    );
	 
	 @Operation(summary =  "Get notifications for the given patient ID")
	    @ApiResponses(value = {
	            @ApiResponse(responseCode ="200", description = "Patient notifications retrieved successfully"),
	            @ApiResponse(responseCode ="500", description = "Circuit breaker triggered for getNotifications")
	    })
	    public ResponseEntity<List<NotificationResponseDto>> getNotificationsPatient(
	            @PathVariable String patientId
	    );
	
	
}
