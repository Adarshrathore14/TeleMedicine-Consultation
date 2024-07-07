package com.telemedicine.payment.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.telemedicine.payment.dto.NotificationResponseDto;

@FeignClient("Notification-service")
public interface NotificationFeignClient {

    @PostMapping("/notifications/sendNotification/{appointmentId}")
    public ResponseEntity<NotificationResponseDto> sendNotification(@PathVariable int appointmentId);
}
