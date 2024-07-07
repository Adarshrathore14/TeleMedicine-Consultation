package com.telemedicine.Video_Call_Service.model;

import lombok.Data;

@Data
public class NotificationRequest {

    private Integer userId;
    private String message;
}