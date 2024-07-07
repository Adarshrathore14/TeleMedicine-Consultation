package com.telemedicine.MedicalRecord.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.telemedicine.MedicalRecord.model.MedicalRecordEntity;

import java.util.List;
import java.util.Optional;
@Repository
public interface MedicalRecord_Repository extends JpaRepository<MedicalRecordEntity, Integer>{
    Optional<MedicalRecordEntity> findByMedicalId(int medicalId);
    Optional<List<MedicalRecordEntity>> findAllByDoctorId(int doctorId);
}
