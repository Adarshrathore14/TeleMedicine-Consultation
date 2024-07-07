package com.telemedicine.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponseDto {

    private String meetingLink;
    
    private String appointmentId;
    
    private String message;
    
    private Integer doctorId;
    
    private String patientId;
}