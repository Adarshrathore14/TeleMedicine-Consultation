package com.telemedicine.Notification_Service.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponseDto {

    private String meetingLink;
    
    private Integer appointmentId;
    
    private String message;
    
    private Integer doctorId;
    
    private String patientId;

	public NotificationResponseDto(String message) {
		super();
		this.message = message;
	}
    
    
}