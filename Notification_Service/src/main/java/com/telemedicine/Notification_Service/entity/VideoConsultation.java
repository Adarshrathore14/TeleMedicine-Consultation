package com.telemedicine.Notification_Service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class VideoConsultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer videoConsultantId;
    private String appointmentId;
    @Column(nullable = false)
    private String meetingLink;
}