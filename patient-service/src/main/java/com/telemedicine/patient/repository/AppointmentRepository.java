package com.telemedicine.patient.repository;
import com.telemedicine.patient.entity.AppointmentDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<AppointmentDetailsEntity,Integer> {
    Optional<List<AppointmentDetailsEntity>> findAllByPatientId(String patientId);
}