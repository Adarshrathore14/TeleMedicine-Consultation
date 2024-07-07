package com.telemedicine.MedicalRecord.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecordResponse {
	private int medicalId;
	private String medicalDescription;
	private String patientId;
	private int appointmentId;
	private int doctorId;
}
