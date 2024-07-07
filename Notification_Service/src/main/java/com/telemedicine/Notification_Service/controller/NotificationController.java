package com.telemedicine.Notification_Service.controller;

import java.util.List;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.telemedicine.Notification_Service.entity.dto.NotificationResponseDto;
import com.telemedicine.Notification_Service.service.NotificationService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/notifications")

public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @CircuitBreaker(name = "Video-Call", fallbackMethod = "fallbackSendNotification")
    @PostMapping("/sendNotification/{appointmentId}")
    public ResponseEntity<NotificationResponseDto> sendNotification(@PathVariable int appointmentId) {
        return ResponseEntity.ok(notificationService.sendNotification(appointmentId));
    }
    
    @CircuitBreaker(name = "Video-Call", fallbackMethod = "fallbackGetNotifications")
    @GetMapping("/getNotificationsDoctor/{doctorId}")
    public ResponseEntity<List<NotificationResponseDto>> getNotifications(@PathVariable Integer doctorId) {
        List<NotificationResponseDto> notifications = notificationService.getNotifications(doctorId);
        return ResponseEntity.ok(notifications);
    }
    
    @CircuitBreaker(name = "Video-Call", fallbackMethod = "fallbackGetNotifications")
    @GetMapping("/getNotificationsPatient/{patientId}")
    public ResponseEntity<List<NotificationResponseDto>> getNotifications(@PathVariable String patientId) {
        List<NotificationResponseDto> notifications = notificationService.getNotifications(patientId);
        return ResponseEntity.ok(notifications);
    }
    
    
    // Fallback method for sendNotification
    private ResponseEntity<NotificationResponseDto> fallbackSendNotification(CallNotPermittedException exception,Throwable throwable) {
        return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(new NotificationResponseDto("Fallback notification for sendNotification"));
    }

    // Fallback method for getNotifications
    private ResponseEntity<List<NotificationResponseDto>> fallbackGetNotifications(CallNotPermittedException exception) {
        return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(null);
    }
    
    
}