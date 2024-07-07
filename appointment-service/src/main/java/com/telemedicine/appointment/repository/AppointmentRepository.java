package com.telemedicine.appointment.repository;
import com.telemedicine.appointment.entity.AppointmentDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
public interface AppointmentRepository extends JpaRepository<AppointmentDetailsEntity,Integer> {
    Optional<AppointmentDetailsEntity> findByAppointmentId(Integer appointmentId);
    void deleteAllByPatientIdAndCreatedDate(String patientId, LocalDateTime createdDate);
    boolean existsByPatientIdAndAppointmentDate(String patientId, LocalDate appointmentDate);
}