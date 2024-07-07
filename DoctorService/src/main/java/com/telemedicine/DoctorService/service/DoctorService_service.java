package com.telemedicine.DoctorService.service;
import com.telemedicine.DoctorService.dtos.*;
import com.telemedicine.DoctorService.model.DoctorEntity;
import org.springframework.http.ResponseEntity;
import java.util.List;
public interface DoctorService_service {
	DoctorResponse addDoctor(DoctorEntity doctor);
	DoctorResponse updateDoctorProfile(int doctorId,DoctorEntity doctor);
	DoctorResponse viewProfile(int doctorId);
	List<DoctorResponse> viewAllDoctorProfiles();
	void deleteDoctor(int doctorId);
	double getConsultationFees(int doctorId);
	ResponseEntity<MedicalRecordResponse> createMedicalRecord(MedicalRecord medicalRecord);
	ResponseEntity<MedicalRecordResponse> updateMedicalRecord(int medicalId, MedicalDescription medicalDescription);
	ResponseEntity<MedicalRecordResponses> getAllMedicalRecords(int doctorId);
	ResponseEntity<MedicalRecordResponse> getMedicalRecordByMedicalId(int medicalId);
	List<AppointmentResponse> viewAppointments(int doctorId);
}
