package com.telemedicine.DoctorService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.telemedicine.DoctorService.model.DoctorEntity;

import java.util.Optional;

@Repository
public interface DoctorServiceRepository extends JpaRepository<DoctorEntity, Integer>{
    Optional<DoctorEntity> findByDoctorId(int doctorId);

}
