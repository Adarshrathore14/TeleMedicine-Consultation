package com.telemedicine.Notification_Service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.telemedicine.Notification_Service.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    Optional<List<Notification>> findByDoctorId(Integer doctorId);
    
    Optional<List<Notification>> findByPatientId(String patientId);
}