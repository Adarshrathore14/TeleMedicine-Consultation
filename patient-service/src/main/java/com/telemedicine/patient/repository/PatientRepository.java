package com.telemedicine.patient.repository;
import com.telemedicine.patient.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface PatientRepository extends JpaRepository<PatientEntity,String> {
    Optional<PatientEntity> findByPatientId(String patientId);
}
