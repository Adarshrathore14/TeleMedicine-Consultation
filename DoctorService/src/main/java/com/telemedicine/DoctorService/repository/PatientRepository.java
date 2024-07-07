package com.telemedicine.DoctorService.repository;

import com.telemedicine.DoctorService.model.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<PatientEntity,String> {
    PatientEntity findByPatientId(String patientId);
}
