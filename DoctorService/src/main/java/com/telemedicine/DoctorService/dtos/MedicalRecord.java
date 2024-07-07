package com.telemedicine.DoctorService.dtos;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecord {
	private String medicalDescription;
	private int appointmentId;
	private String patientId;
	private int doctorId;
}
