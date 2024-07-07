package com.telemedicine.patient.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecordResponse {
	private int medicalId;
	private String medicalDescription;
	private int appointmentId;
	private String patientId;
	private int doctorId;
}