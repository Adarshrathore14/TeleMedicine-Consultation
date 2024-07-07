package com.telemedicine.registration.repository;
import com.telemedicine.registration.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PatientRepository extends JpaRepository<PatientEntity,String> {
    Boolean existsByMobileNumber(String mobileNumber);
    Boolean existsByUserNameAndEmail(String userName,String email);
}
