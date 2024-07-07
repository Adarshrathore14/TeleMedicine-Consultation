package com.telemedicine.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecord {
	
	private int medicalId;
	private String medicalDescription;
	private int appointmentId;
	private int patientId;
	private int doctorId;
}
