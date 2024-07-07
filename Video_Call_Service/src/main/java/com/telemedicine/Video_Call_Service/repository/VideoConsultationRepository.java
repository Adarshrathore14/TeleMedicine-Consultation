package com.telemedicine.Video_Call_Service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.telemedicine.Video_Call_Service.model.VideoConsultation;

@Repository
public interface VideoConsultationRepository extends  JpaRepository<VideoConsultation, Integer> {
	
//	@Query("SELECT vc FROM VideoConsultation vc WHERE vc.appointment.appointId = :appointId")
    Optional<VideoConsultation> findByAppointmentId(Integer appointmentId);
	
    
}