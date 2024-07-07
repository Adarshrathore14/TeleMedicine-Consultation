package com.telemedicine.Notification_Service.entity.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponse {
    private String patientId;
    private int doctorId;
}
